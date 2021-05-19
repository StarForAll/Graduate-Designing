package cn.edu.ncu.service.impl;

import cn.edu.ncu.common.constant.JudgeEnum;
import cn.edu.ncu.common.constant.PrivilegeTypeEnum;
import cn.edu.ncu.common.constant.SystemConfigEnum;
import cn.edu.ncu.common.core.exception.BusinessException;
import cn.edu.ncu.common.util.basic.StringUtil;
import cn.edu.ncu.dao.entity.Privilege;
import cn.edu.ncu.dao.mapper.PrivilegeMapper;
import cn.edu.ncu.dao.mapper.RoleEmployeeMapper;
import cn.edu.ncu.dao.mapper.RolePrivilegeMapper;
import cn.edu.ncu.pojo.bo.RequestTokenBO;
import cn.edu.ncu.pojo.dto.EmployeeDTO;
import cn.edu.ncu.pojo.dto.SystemConfigDTO;
import cn.edu.ncu.service.PrivilegeEmployeeService;
import cn.edu.ncu.service.SystemConfigService;
import cn.edu.ncu.service.exception.BaseDataExceptionCode;
import com.google.common.collect.Lists;
import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * [ 后台员工权限缓存方法 ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2018 1024lab.netInc. All rights reserved.
 * @date 2019/3/28 0028 下午 14:07
 * @since JDK1.8
 */
@Service
public class PrivilegeEmployeeServiceImpl implements PrivilegeEmployeeService {

    /**
     * 后台用户权限缓存 <id,<controllerName,methodName></>></>
     */
    private ConcurrentMap<Long, Map<String, List<String>>> employeePrivileges = new ConcurrentLinkedHashMap.Builder<Long, Map<String, List<String>>>().maximumWeightedCapacity(1000).build();
    private ConcurrentMap<Long, List<Privilege>> employeePrivilegeListMap = new ConcurrentLinkedHashMap.Builder<Long, List<Privilege>>().maximumWeightedCapacity(1000).build();

    @Autowired
    private SystemConfigService systemConfigService;

    @Resource
    private RoleEmployeeMapper roleEmployeeMapper;

    @Resource
    private RolePrivilegeMapper rolePrivilegeMapper;

    @Resource
    private PrivilegeMapper privilegeMapper;

    /**
     * 移除某人缓存中的权限
     *
     * @param employeeId
     */
    @Override
    public void removeCache(Long employeeId) {
        this.employeePrivileges.remove(employeeId);
    }

    /**
     * 检查某人是否有访问某个方法的权限
     *
     * @param requestTokenBO
     * @param controllerName
     * @param methodName
     * @return
     */
    @Override
    public Boolean checkEmployeeHavePrivilege(RequestTokenBO requestTokenBO, String controllerName, String methodName) {
        if (StringUtils.isEmpty(controllerName) || StringUtils.isEmpty(methodName)) {
            return false;
        }
        Boolean isSuperman = requestTokenBO.getEmployeeBO().getIsSuperman();
        if (isSuperman) {
            return true;
        }
        Map<String, List<String>> privileges = this.getPrivileges(requestTokenBO.getRequestUserId());
        List<String> urlList = privileges.get(controllerName.toLowerCase());
        if (CollectionUtils.isEmpty(urlList)) {
            return false;
        }
        return urlList.contains(methodName);
    }

    @Override
    public List<Privilege> getEmployeeAllPrivilege(Long employeeId) {
        return employeePrivilegeListMap.get(employeeId);
    }

    /**
     * 判断是否为超级管理员
     *
     * @param employeeId
     * @return
     */
    @Override
    public Boolean isSuperman(Long employeeId) {
        SystemConfigDTO systemConfig = systemConfigService.getCacheByKey(SystemConfigEnum.Key.EMPLOYEE_SUPERMAN);
        if (systemConfig == null) {
            throw new BusinessException(BaseDataExceptionCode.SYSTEM_CONFIG_LACK,
                    new Exception("缺少系统配置项[" + SystemConfigEnum.Key.EMPLOYEE_SUPERMAN.name() + "]"));
        }

        List<Long> superManIdsList = StringUtil.splitConverToLongList(systemConfig.getConfigValue(), ",");
        return superManIdsList.contains(employeeId);
    }

    /**
     * 根据员工ID 获取 权限信息
     *
     * @param employeeId
     * @return
     */
    @Override
    public List<Privilege> getPrivilegesByEmployeeId(Long employeeId) {
        List<Privilege> privilegeEntities;
        // 如果是超管的话
        Boolean isSuperman = this.isSuperman(employeeId);
        if (isSuperman) {
            privilegeEntities = privilegeMapper.selectAllPrivilege();
        } else {
            privilegeEntities = loadPrivilegeFromDb(employeeId);
        }

        if (privilegeEntities == null) {
            privilegeEntities = Lists.newArrayList();
        }

        this.updateCachePrivilege(employeeId, privilegeEntities);
        return privilegeEntities;
    }

    /**
     * 获取某人所能访问的方法
     *
     * @param employeeId
     * @return
     */
    private Map<String, List<String>> getPrivileges(Long employeeId) {
        Map<String, List<String>> privileges = employeePrivileges.get(employeeId);
        if (privileges != null) {
            return privileges;
        }
        List<Privilege> privilegeEntities = this.loadPrivilegeFromDb(employeeId);
        return updateCachePrivilege(employeeId, privilegeEntities);
    }

    private List<Privilege> loadPrivilegeFromDb(Long employeeId) {
        List<Long> roleIdList = roleEmployeeMapper.selectRoleIdByEmployeeId(employeeId);
        if (CollectionUtils.isEmpty(roleIdList)) {
            return Lists.newArrayList();
        }
        List<Privilege> privilegeEntities = rolePrivilegeMapper.listByRoleIds(roleIdList, JudgeEnum.YES.getValue());
        if (privilegeEntities != null) {
            return privilegeEntities;
        }
        return Collections.emptyList();
    }

    @Override
    public Map<String, List<String>> updateCachePrivilege(Long employeeId, List<Privilege> privilegeEntities) {
        employeePrivilegeListMap.put(employeeId, privilegeEntities);
        List<String> privilegeList = new ArrayList<>();
        Map<String, List<String>> privilegeMap = new HashMap<>(16);
        if (CollectionUtils.isNotEmpty(privilegeEntities)) {
            List<List<String>> setList =
                    privilegeEntities.stream().filter(e -> e.getType().equals(PrivilegeTypeEnum.POINTS.getValue())).map(Privilege::getUrl).collect(Collectors.toList()).stream().map(e -> StringUtil.splitConvertToList(e, ",")).collect(Collectors.toList());
            setList.forEach(privilegeList::addAll);
        }
        privilegeList.forEach(item -> {
            List<String> path = StringUtil.splitConvertToList(item, "\\.");
            String controllerName = path.get(0).toLowerCase();
            String methodName = path.get(1);
            List<String> methodNameList = privilegeMap.get(controllerName);
            if (null == methodNameList) {
                methodNameList = new ArrayList();
            }
            if (!methodNameList.contains(methodName)) {
                methodNameList.add(methodName);
            }
            privilegeMap.put(controllerName, methodNameList);
        });

        employeePrivileges.put(employeeId, privilegeMap);
        return privilegeMap;
    }

    @Override
    public void updateOnlineEmployeePrivilegeByRoleId(Long roleId) {
        List<EmployeeDTO> roleEmployeeList = roleEmployeeMapper.selectEmployeeByRoleId(roleId);
        List<Long> employeeIdList = roleEmployeeList.stream().map(EmployeeDTO::getId).collect(Collectors.toList());

        for (Long empId : employeePrivileges.keySet()) {
            if (employeeIdList.contains(empId)) {
                getPrivilegesByEmployeeId(empId);
            }
        }
    }
}
