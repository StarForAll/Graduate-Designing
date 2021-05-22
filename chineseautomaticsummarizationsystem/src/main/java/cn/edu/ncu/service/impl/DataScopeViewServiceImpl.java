package cn.edu.ncu.service.impl;
import cn.edu.ncu.common.constant.DataScopeTypeEnum;
import cn.edu.ncu.common.constant.DataScopeViewTypeEnum;
import cn.edu.ncu.common.util.basic.BaseEnumUtil;
import cn.edu.ncu.dao.entity.DataScopeRole;
import cn.edu.ncu.dao.entity.Employee;
import cn.edu.ncu.dao.mapper.DataScopeRoleMapper;
import cn.edu.ncu.dao.mapper.EmployeeMapper;
import cn.edu.ncu.dao.mapper.RoleEmployeeMapper;
import cn.edu.ncu.pojo.dto.EmployeeDTO;
import cn.edu.ncu.pojo.vo.EmployeeVO;
import cn.edu.ncu.service.DataScopeViewService;
import cn.edu.ncu.service.DepartmentTreeService;
import cn.edu.ncu.service.PrivilegeEmployeeService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: XiongZhiCong
 * @Description: 数据范围视图业务
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Service
public class DataScopeViewServiceImpl implements DataScopeViewService {

    @Resource
    private RoleEmployeeMapper roleEmployeeMapper;

    @Resource
    private DataScopeRoleMapper dataScopeRoleMapper;

    @Autowired
    private DepartmentTreeService departmentTreeService;

    @Resource
    private EmployeeMapper employeeMapper;

    @Autowired
    private PrivilegeEmployeeService privilegeEmployeeService;

    /**
     * 获取某人可以查看的所有人员信息
     *
     * @param dataScopeTypeEnum
     * @param employeeId
     * @return
     */
    @Override
    public List<Long> getCanViewEmployeeId(DataScopeTypeEnum dataScopeTypeEnum, Long employeeId) {
        DataScopeViewTypeEnum viewType = this.getEmployeeDataScopeViewType(dataScopeTypeEnum, employeeId);
        if (DataScopeViewTypeEnum.ME == viewType) {
            return this.getMeEmployeeIdList(employeeId);
        }
        if (DataScopeViewTypeEnum.DEPARTMENT == viewType) {
            return this.getDepartmentEmployeeIdList(employeeId);
        }
        if (DataScopeViewTypeEnum.DEPARTMENT_AND_SUB == viewType) {
            return this.getDepartmentAndSubEmployeeIdList(employeeId);
        }
        return Lists.newArrayList();
    }

    /**
     * 获取某人可以查看的所有部门信息
     *
     * @param dataScopeTypeEnum
     * @param employeeId
     * @return
     */
    @Override
    public List<Long> getCanViewDepartmentId(DataScopeTypeEnum dataScopeTypeEnum, Long employeeId) {
        DataScopeViewTypeEnum viewType = this.getEmployeeDataScopeViewType(dataScopeTypeEnum, employeeId);
        if (DataScopeViewTypeEnum.ME == viewType) {
            return this.getMeDepartmentIdList(employeeId);
        }
        if (DataScopeViewTypeEnum.DEPARTMENT == viewType) {
            return this.getMeDepartmentIdList(employeeId);
        }
        if (DataScopeViewTypeEnum.DEPARTMENT_AND_SUB == viewType) {
            return this.getDepartmentAndSubIdList(employeeId);
        }
        return Lists.newArrayList();
    }

    private List<Long> getMeDepartmentIdList(Long employeeId) {
        Employee employeeEntity = employeeMapper.selectByPrimaryKey(employeeId);
        return Lists.newArrayList(employeeEntity.getDepartmentId());
    }

    private List<Long> getDepartmentAndSubIdList(Long employeeId) {
        Employee employeeEntity = employeeMapper.selectByPrimaryKey(employeeId);
        List<Long> allDepartmentIds = Lists.newArrayList();
        departmentTreeService.buildIdList(employeeEntity.getDepartmentId(), allDepartmentIds);
        return allDepartmentIds;
    }

    /**
     * 根据员工id 获取各数据范围最大的可见范围 map<dataScopeType,viewType></>
     *
     * @param employeeId
     * @return
     */
    @Override
    public DataScopeViewTypeEnum getEmployeeDataScopeViewType(DataScopeTypeEnum dataScopeTypeEnum, Long employeeId) {
        if (employeeId == null) {
            return DataScopeViewTypeEnum.ME;
        }

        if (privilegeEmployeeService.isSuperman(employeeId)) {
            return DataScopeViewTypeEnum.ALL;
        }
        List<Long> roleIdList = roleEmployeeMapper.selectRoleIdByEmployeeId(employeeId);
        //未设置角色 默认本人
        if (CollectionUtils.isEmpty(roleIdList)) {
            return DataScopeViewTypeEnum.ME;
        }
        //未设置角色数据范围 默认本人
        List<DataScopeRole> dataScopeRoleList = dataScopeRoleMapper.listByRoleIdList(roleIdList);
        if (CollectionUtils.isEmpty(dataScopeRoleList)) {
            return DataScopeViewTypeEnum.ME;
        }
        Map<Integer, List<DataScopeRole>> listMap = dataScopeRoleList.stream().collect(Collectors.groupingBy(DataScopeRole::getDataScopeType));
        List<DataScopeRole> viewLevelList = listMap.get(dataScopeTypeEnum.getValue());
        DataScopeRole maxLevel = viewLevelList.stream().max(Comparator.comparing(e -> BaseEnumUtil.getEnumByValue(e.getViewType(), DataScopeViewTypeEnum.class).getLevel())).get();
        return BaseEnumUtil.getEnumByValue(maxLevel.getViewType(), DataScopeViewTypeEnum.class);
    }

    /**
     * 获取本人相关 可查看员工id
     *
     * @param employeeId
     * @return
     */
    private List<Long> getMeEmployeeIdList(Long employeeId) {
        return Lists.newArrayList(employeeId);
    }

    /**
     * 获取本部门相关 可查看员工id
     *
     * @param employeeId
     * @return
     */
    private List<Long> getDepartmentEmployeeIdList(Long employeeId) {
        Employee employeeEntity = employeeMapper.selectByPrimaryKey(employeeId);
        List<EmployeeVO> employeeList = employeeMapper.getEmployeeIdByDeptId(employeeEntity.getDepartmentId());
        return employeeList.stream().map(EmployeeVO::getId).collect(Collectors.toList());
    }

    /**
     * 获取本部门及下属子部门相关 可查看员工id
     *
     * @param employeeId
     * @return
     */
    private List<Long> getDepartmentAndSubEmployeeIdList(Long employeeId) {
        Employee employeeEntity = employeeMapper.selectByPrimaryKey(employeeId);
        List<Long> allDepartmentIds = Lists.newArrayList();
        departmentTreeService.buildIdList(employeeEntity.getDepartmentId(), allDepartmentIds);
        List<EmployeeDTO> employeeList = employeeMapper.getEmployeeIdByDeptIds(allDepartmentIds);
        return employeeList.stream().map(EmployeeDTO::getId).collect(Collectors.toList());
    }

}
