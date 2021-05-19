package cn.edu.ncu.service.impl;

import cn.edu.ncu.common.annotation.DataScope;
import cn.edu.ncu.common.constant.DataScopeTypeEnum;
import cn.edu.ncu.common.constant.DataScopeViewTypeEnum;
import cn.edu.ncu.common.constant.DataScopeWhereInTypeEnum;
import cn.edu.ncu.common.core.pojo.DataScopePowerStrategy;
import cn.edu.ncu.common.util.web.RequestTokenUtil;
import cn.edu.ncu.pojo.bo.RequestTokenBO;
import cn.edu.ncu.service.ApplicationContextHolder;
import cn.edu.ncu.service.DataScopeSqlConfigService;
import cn.edu.ncu.service.DataScopeViewService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import cn.edu.ncu.pojo.dto.DataScopeSqlConfigDTO;
import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * [  ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2018 1024lab.netInc. All rights reserved.
 * @date 2019/4/29 0029 上午 10:12
 * @since JDK1.8
 */
@Slf4j
@Service
public class DataScopeSqlConfigServiceImpl implements DataScopeSqlConfigService {

    private ConcurrentHashMap<String, DataScopeSqlConfigDTO> dataScopeMethodMap = new ConcurrentHashMap<>();

    @Autowired
    private DataScopeViewService dataScopeViewService;

    @Value("${swagger.packAge}")
    private String scanPackage;

    /**
     * 注解joinsql 参数
     */
    private static final String EMPLOYEE_PARAM = "#employeeIds";

    private static final String DEPARTMENT_PARAM = "#departmentIds";

    @PostConstruct
    private void initDataScopeMethodMap() {
        this.refreshDataScopeMethodMap();
    }

    /**
     * 刷新 所有添加数据范围注解的接口方法配置<class.method,DataScopeSqlConfigDTO></>
     *
     * @return
     */
    private Map<String, DataScopeSqlConfigDTO> refreshDataScopeMethodMap() {
        Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage(scanPackage)).setScanners(new MethodAnnotationsScanner()));
        Set<Method> methods = reflections.getMethodsAnnotatedWith(DataScope.class);
        for (Method method : methods) {
            DataScope dataScopeAnnotation = method.getAnnotation(DataScope.class);
            if (dataScopeAnnotation != null) {
                DataScopeSqlConfigDTO configDTO = new DataScopeSqlConfigDTO();
                configDTO.setDataScopeType(dataScopeAnnotation.dataScopeType());
                configDTO.setJoinSql(dataScopeAnnotation.joinSql());
                configDTO.setWhereIndex(dataScopeAnnotation.whereIndex());
                configDTO.setDataScopeWhereInType(dataScopeAnnotation.whereInType());
                dataScopeMethodMap.put(method.getDeclaringClass().getSimpleName() + "." + method.getName(), configDTO);
            }
        }
        return dataScopeMethodMap;
    }

    /**
     * 根据调用的方法获取，此方法的配置信息
     *
     * @param method
     * @return
     */
    @Override
    public DataScopeSqlConfigDTO getSqlConfig(String method) {
        DataScopeSqlConfigDTO sqlConfigDTO = this.dataScopeMethodMap.get(method);
        return sqlConfigDTO;
    }

    /**
     * 组装需要拼接的sql
     *
     * @param sqlConfigDTO
     * @return
     */
    @Override
    public String getJoinSql(DataScopeSqlConfigDTO sqlConfigDTO) {
        DataScopeTypeEnum dataScopeTypeEnum = sqlConfigDTO.getDataScopeType();
        String joinSql = sqlConfigDTO.getJoinSql();
        RequestTokenBO requestToken = RequestTokenUtil.getThreadLocalUser();
        Long employeeId = requestToken.getRequestUserId();
        if (DataScopeWhereInTypeEnum.CUSTOM_STRATEGY == sqlConfigDTO.getDataScopeWhereInType()) {
            Class strategyClass = sqlConfigDTO.getJoinSqlImplClazz();
            if(strategyClass == null){
                log.warn("data scope custom strategy class is null");
                return "";
            }
            DataScopePowerStrategy powerStrategy = (DataScopePowerStrategy) ApplicationContextHolder.getBean(sqlConfigDTO.getJoinSqlImplClazz());
            if (powerStrategy == null) {
                log.warn("data scope custom strategy class：{} ,bean is null",sqlConfigDTO.getJoinSqlImplClazz());
                return "";
            }
            DataScopeViewTypeEnum viewTypeEnum = dataScopeViewService.getEmployeeDataScopeViewType(dataScopeTypeEnum, employeeId);
            return powerStrategy.getCondition(viewTypeEnum,sqlConfigDTO);
        }
        if (DataScopeWhereInTypeEnum.EMPLOYEE == sqlConfigDTO.getDataScopeWhereInType()) {
            List<Long> canViewEmployeeIds = dataScopeViewService.getCanViewEmployeeId(dataScopeTypeEnum, employeeId);
            if (CollectionUtils.isEmpty(canViewEmployeeIds)) {
                return "";
            }
            String employeeIds = StringUtils.join(canViewEmployeeIds, ",");
            String sql = joinSql.replaceAll(EMPLOYEE_PARAM, employeeIds);
            return sql;
        }
        if (DataScopeWhereInTypeEnum.DEPARTMENT == sqlConfigDTO.getDataScopeWhereInType()) {
            List<Long> canViewDepartmentIds = dataScopeViewService.getCanViewDepartmentId(dataScopeTypeEnum, employeeId);
            if (CollectionUtils.isEmpty(canViewDepartmentIds)) {
                return "";
            }
            String departmentIds = StringUtils.join(canViewDepartmentIds, ",");
            String sql = joinSql.replaceAll(DEPARTMENT_PARAM, departmentIds);
            return sql;
        }
        return "";
    }
}
