package cn.edu.ncu.service.impl;

import cn.edu.ncu.common.constant.CommonConst;
import cn.edu.ncu.common.constant.EmployeeResponseCodeConst;
import cn.edu.ncu.common.constant.EmployeeStatusEnum;
import cn.edu.ncu.common.constant.JudgeEnum;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.common.util.basic.DESUtil;
import cn.edu.ncu.common.util.basic.VerificationUtil;
import cn.edu.ncu.common.util.bean.BeanUtil;
import cn.edu.ncu.common.util.dao.IdGeneratorUtil;
import cn.edu.ncu.dao.entity.Department;
import cn.edu.ncu.dao.entity.Employee;
import cn.edu.ncu.dao.entity.RoleEmployee;
import cn.edu.ncu.dao.mapper.DepartmentMapper;
import cn.edu.ncu.dao.mapper.EmployeeMapper;
import cn.edu.ncu.dao.mapper.PositionMapper;
import cn.edu.ncu.dao.mapper.RoleEmployeeMapper;
import cn.edu.ncu.pojo.bo.EmployeeBO;
import cn.edu.ncu.pojo.bo.RequestTokenBO;
import cn.edu.ncu.pojo.dto.*;
import cn.edu.ncu.pojo.vo.EmployeeVO;
import cn.edu.ncu.service.EmployeeService;
import cn.edu.ncu.service.PositionService;
import cn.edu.ncu.service.PrivilegeEmployeeService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author: XiongZhiCong
 * @Description: 员工管理业务
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final String RESET_PASSWORD = "123456";

    @Resource
    private EmployeeMapper employeeMapper;

    @Resource
    private DepartmentMapper departmentMapper;

    @Resource
    private RoleEmployeeMapper roleEmployeeMapper;

    @Autowired
    private PositionService positionService;

    @Resource
    private PositionMapper positionMapper;

    @Autowired
    private PrivilegeEmployeeService privilegeEmployeeService;
    @Autowired
    private IdGeneratorUtil idGeneratorUtil;
    /**
     * 员工基本信息的缓存
     */
    private static final ConcurrentHashMap<Long, EmployeeBO> employeeCache = new ConcurrentHashMap<>();

    @Override
    public List<EmployeeVO> getAllEmployee() {
        List<Employee> employees = employeeMapper.selectAll();
        return BeanUtil.copyList(employees,EmployeeVO.class);
    }

    @Override
    public EmployeeBO getById(Long id) {
        EmployeeBO employeeBO = employeeCache.get(id);
        if (employeeBO == null) {
            Employee employeeEntity = employeeMapper.selectByPrimaryKey(id);
            if (employeeEntity != null) {
                Boolean superman = privilegeEmployeeService.isSuperman(id);
                employeeBO = new EmployeeBO(employeeEntity, superman);
                employeeCache.put(employeeEntity.getId(), employeeBO);
            }
        }
        return employeeBO;
    }

    /**
     * 查询员工列表
     *
     * @param queryDTO
     * @return
     */
    @Override
    public ResponseDTO<PageResultDTO<EmployeeVO>> selectEmployeeList(EmployeeQueryDTO queryDTO) {
        PageResultDTO<EmployeeVO> pageResultDTO=new PageResultDTO<>();
        int total=0;
        Boolean searchCount = queryDTO.getSearchCount();
        if(searchCount ==null||searchCount){
            total=employeeMapper.selectEmployeeCount(queryDTO);
        }
        long pages=total/queryDTO.getPageSize()+1;
        pageResultDTO.setPageNum(Long.valueOf(queryDTO.getPageNum()));
        pageResultDTO.setPages(pages);
        pageResultDTO.setTotal((long) total);
        pageResultDTO.setPageSize(Long.valueOf(queryDTO.getPageSize()));

        queryDTO.setIsDelete(JudgeEnum.NO.getValue());
        List<EmployeeDTO> employeeList = employeeMapper.selectEmployeeList(queryDTO);
        List<Long> employeeIdList = employeeList.stream().map(EmployeeDTO::getId).collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(employeeIdList)) {
            List<PositionRelationResultDTO> positionRelationResultDTOList = positionMapper.selectEmployeesRelation(employeeIdList);
            Map<Long, List<PositionRelationResultDTO>> employeePositionMap = new HashedMap();

            for (PositionRelationResultDTO positionRelationResultDTO : positionRelationResultDTOList) {
                List<PositionRelationResultDTO> relationResultDTOList = employeePositionMap.computeIfAbsent(positionRelationResultDTO.getEmployeeId(), k -> new ArrayList<>());
                //匹配对应的岗位
                relationResultDTOList.add(positionRelationResultDTO);
            }

            for (EmployeeDTO employeeDTO : employeeList) {
                List<PositionRelationResultDTO> relationResultDTOList = employeePositionMap.get(employeeDTO.getId());
                if (relationResultDTOList != null) {
                    employeeDTO.setPositionRelationList(relationResultDTOList);
                    employeeDTO.setPositionName(relationResultDTOList.stream().map(PositionRelationResultDTO::getPositionName).collect(Collectors.joining(",")));
                }
            }
        }
        List<EmployeeVO> employeeVOs = BeanUtil.copyList(employeeList, EmployeeVO.class);
        pageResultDTO.setList(employeeVOs);
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * 新增员工
     *
     * @param employeeAddDto
     * @param requestToken
     * @return
     */
    @Override
    public ResponseDTO<String> addEmployee(EmployeeAddDTO employeeAddDto, RequestTokenBO requestToken) {
        Employee entity = BeanUtil.copy(employeeAddDto, Employee.class);
        if (StringUtils.isNotEmpty(employeeAddDto.getIdCard())) {
            boolean checkResult = Pattern.matches(VerificationUtil.ID_CARD, employeeAddDto.getIdCard());
            if (!checkResult) {
                return ResponseDTO.wrap(EmployeeResponseCodeConst.ID_CARD_ERROR);
            }
        }
        if (StringUtils.isNotEmpty(employeeAddDto.getBirthday())) {
            boolean checkResult = Pattern.matches(VerificationUtil.DATE, employeeAddDto.getBirthday());
            if (!checkResult) {
                return ResponseDTO.wrap(EmployeeResponseCodeConst.BIRTHDAY_ERROR);
            }
        }
        //同名员工
        EmployeeDTO sameNameEmployee = employeeMapper.getByLoginName(entity.getLoginName(), EmployeeStatusEnum.NORMAL.getValue());
        if (null != sameNameEmployee) {
            return ResponseDTO.wrap(EmployeeResponseCodeConst.LOGIN_NAME_EXISTS);
        }
        //同电话员工
        EmployeeDTO samePhoneEmployee = employeeMapper.getByPhone(entity.getPhone(), EmployeeStatusEnum.NORMAL.getValue());
        if (null != samePhoneEmployee) {
            return ResponseDTO.wrap(EmployeeResponseCodeConst.PHONE_EXISTS);
        }
        Long departmentId = entity.getDepartmentId();
        Department department = departmentMapper.selectByPrimaryKey(departmentId);
        if (department == null) {
            return ResponseDTO.wrap(EmployeeResponseCodeConst.DEPT_NOT_EXIST);
        }

        //如果没有密码  默认设置为123456
        String pwd = entity.getLoginPwd();
        if (StringUtils.isBlank(pwd)) {
            entity.setLoginPwd(DESUtil.encrypt(CommonConst.Password.SALT_FORMAT, RESET_PASSWORD));
        } else {
            entity.setLoginPwd(DESUtil.encrypt(CommonConst.Password.SALT_FORMAT, entity.getLoginPwd()));
        }

        entity.setCreateUser(requestToken.getRequestUserId());
        if (StringUtils.isEmpty(entity.getBirthday())) {
            entity.setBirthday(null);
        }
        if(entity.getId()==null){
            entity.setId(idGeneratorUtil.snowflakeId());
        }
        Date date=new Date(System.currentTimeMillis());
        if(entity.getUpdateTime()==null){
            entity.setUpdateTime(date);
        }
        if(entity.getCreateTime()==null){
            entity.setCreateTime(date);
        }
        employeeMapper.insert(entity);

        PositionRelationAddDTO positionRelAddDTO = new PositionRelationAddDTO(employeeAddDto.getPositionIdList(), entity.getId());
        //存储所选岗位信息
        positionService.addPositionRelation(positionRelAddDTO);

        return ResponseDTO.succ();
    }

    /**
     * 更新禁用状态
     *
     * @param employeeId
     * @param status
     * @return
     */
    @Override
    public ResponseDTO<String> updateStatus(Long employeeId, Integer status) {
        if (null == employeeId) {
            return ResponseDTO.wrap(EmployeeResponseCodeConst.EMP_NOT_EXISTS);
        }
        EmployeeBO entity = getById(employeeId);
        if (null == entity) {
            return ResponseDTO.wrap(EmployeeResponseCodeConst.EMP_NOT_EXISTS);
        }
        List<Long> empIds = Lists.newArrayList();
        empIds.add(employeeId);
        employeeMapper.batchUpdateStatus(empIds, status);
        employeeCache.remove(employeeId);
        return ResponseDTO.succ();
    }

    /**
     * 批量更新员工状态
     *
     * @param batchUpdateStatusDTO
     * @return
     */
    @Override
    public ResponseDTO<String> batchUpdateStatus(EmployeeBatchUpdateStatusDTO batchUpdateStatusDTO) {
        employeeMapper.batchUpdateStatus(batchUpdateStatusDTO.getEmployeeIds(), batchUpdateStatusDTO.getStatus());
        if (batchUpdateStatusDTO.getEmployeeIds() != null) {
            batchUpdateStatusDTO.getEmployeeIds().forEach(employeeCache::remove);
        }
        return ResponseDTO.succ();
    }

    /**
     * 更新员工
     *
     * @param updateDTO
     * @return
     */
    @Override
    public ResponseDTO<String> updateEmployee(EmployeeUpdateDTO updateDTO) {
        Long employeeId = updateDTO.getId();
        Employee employeeEntity = employeeMapper.selectByPrimaryKey(employeeId);
        if (null == employeeEntity) {
            return ResponseDTO.wrap(EmployeeResponseCodeConst.EMP_NOT_EXISTS);
        }
        if (StringUtils.isNotBlank(updateDTO.getIdCard())) {
            boolean checkResult = Pattern.matches(VerificationUtil.ID_CARD, updateDTO.getIdCard());
            if (!checkResult) {
                return ResponseDTO.wrap(EmployeeResponseCodeConst.ID_CARD_ERROR);
            }
        }
        if (StringUtils.isNotEmpty(updateDTO.getBirthday())) {
            boolean checkResult = Pattern.matches(VerificationUtil.DATE, updateDTO.getBirthday());
            if (!checkResult) {
                return ResponseDTO.wrap(EmployeeResponseCodeConst.BIRTHDAY_ERROR);
            }
        }
        Long departmentId = updateDTO.getDepartmentId();
        Department departmentEntity = departmentMapper.selectByPrimaryKey(departmentId);
        if (departmentEntity == null) {
            return ResponseDTO.wrap(EmployeeResponseCodeConst.DEPT_NOT_EXIST);
        }
        EmployeeDTO sameNameEmployee = employeeMapper.getByLoginName(updateDTO.getLoginName(), EmployeeStatusEnum.NORMAL.getValue());
        if (null != sameNameEmployee && !sameNameEmployee.getId().equals(employeeId)) {
            return ResponseDTO.wrap(EmployeeResponseCodeConst.LOGIN_NAME_EXISTS);
        }
        EmployeeDTO samePhoneEmployee = employeeMapper.getByPhone(updateDTO.getLoginName(), EmployeeStatusEnum.NORMAL.getValue());
        if (null != samePhoneEmployee && !samePhoneEmployee.getId().equals(employeeId)) {
            return ResponseDTO.wrap(EmployeeResponseCodeConst.PHONE_EXISTS);
        }
        String newPwd = updateDTO.getLoginPwd();
        if (!StringUtils.isBlank(newPwd)) {
            updateDTO.setLoginPwd(DESUtil.encrypt(CommonConst.Password.SALT_FORMAT, updateDTO.getLoginPwd()));
        } else {
            updateDTO.setLoginPwd(employeeEntity.getLoginPwd());
        }
        Employee entity = BeanUtil.copy(updateDTO, Employee.class);
        entity.setUpdateTime(new Date());
        if (StringUtils.isEmpty(entity.getBirthday())) {
            entity.setBirthday(null);
        }
        if (CollectionUtils.isNotEmpty(updateDTO.getPositionIdList())) {
            //删除旧的关联关系 添加新的关联关系
            positionService.removePositionRelation(entity.getId());
            PositionRelationAddDTO positionRelAddDTO = new PositionRelationAddDTO(updateDTO.getPositionIdList(), entity.getId());
            positionService.addPositionRelation(positionRelAddDTO);
        }
        entity.setIsDisabled(employeeEntity.getIsDisabled());
        entity.setIsLeave(employeeEntity.getIsLeave());
        entity.setCreateUser(employeeEntity.getCreateUser());
        entity.setCreateTime(employeeEntity.getCreateTime());
        entity.setUpdateTime(new Date());
        employeeMapper.update(entity);
        employeeCache.remove(employeeId);
        return ResponseDTO.succ();
    }

    /**
     * 删除员工
     *
     * @param employeeId 员工ID
     * @return
     */
    @Override
    public ResponseDTO<String> deleteEmployeeById(Long employeeId) {
        Employee employeeEntity = employeeMapper.selectByPrimaryKey(employeeId);
        if (null == employeeEntity) {
            return ResponseDTO.wrap(EmployeeResponseCodeConst.EMP_NOT_EXISTS);
        }
        //假删
        employeeEntity.setIsDelete(JudgeEnum.YES.getValue().longValue());
        employeeMapper.update(employeeEntity);
        employeeCache.remove(employeeId);
        return ResponseDTO.succ();
    }

    /**
     * 更新用户角色
     *
     * @param updateRolesDTO
     * @return
     */
    @Override
    public ResponseDTO<String> updateRoles(EmployeeUpdateRolesDTO updateRolesDTO) {
        roleEmployeeMapper.deleteByEmployeeId(updateRolesDTO.getEmployeeId());
        if (CollectionUtils.isNotEmpty(updateRolesDTO.getRoleIds())) {
            List<RoleEmployee> roleEmployeeEntities = Lists.newArrayList();
            RoleEmployee roleEmployeeEntity;
            for (Long roleId : updateRolesDTO.getRoleIds()) {
                roleEmployeeEntity = new RoleEmployee();
                roleEmployeeEntity.setId(idGeneratorUtil.snowflakeId());
                roleEmployeeEntity.setEmployeeId(updateRolesDTO.getEmployeeId());
                roleEmployeeEntity.setRoleId(roleId);
                roleEmployeeEntities.add(roleEmployeeEntity);
            }
            roleEmployeeMapper.batchInsert(roleEmployeeEntities);
        }
        return ResponseDTO.succ();
    }

    /**
     * 更新密码
     *
     * @param updatePwdDTO
     * @param requestToken
     * @return
     */
    @Override
    public ResponseDTO<String> updatePwd(EmployeeUpdatePwdDTO updatePwdDTO, RequestTokenBO requestToken) {
        Long employeeId = requestToken.getRequestUserId();
        Employee employee = employeeMapper.selectByPrimaryKey(employeeId);
        if (employee == null) {
            return ResponseDTO.wrap(EmployeeResponseCodeConst.EMP_NOT_EXISTS);
        }
        if (!employee.getLoginPwd().equals(DESUtil.encrypt(CommonConst.Password.SALT_FORMAT, updatePwdDTO.getOldPwd()))) {
            return ResponseDTO.wrap(EmployeeResponseCodeConst.PASSWORD_ERROR);
        }
        employee.setLoginPwd(DESUtil.encrypt(CommonConst.Password.SALT_FORMAT, updatePwdDTO.getPwd()));
        employeeMapper.update(employee);
        employeeCache.remove(employeeId);
        return ResponseDTO.succ();
    }

    @Override
    public ResponseDTO<List<EmployeeVO>> getEmployeeByDeptId(Long departmentId) {
        List<EmployeeVO> list = employeeMapper.getEmployeeIdByDeptId(departmentId);
        return ResponseDTO.succData(list);
    }

    /**
     * 重置密码
     *
     * @param employeeId
     * @return
     */
    @Override
    public ResponseDTO resetPasswd(Integer employeeId) {
        String md5Password = DESUtil.encrypt(CommonConst.Password.SALT_FORMAT, RESET_PASSWORD);
        employeeMapper.updatePassword(employeeId, md5Password);
        employeeCache.remove(employeeId);
        return ResponseDTO.succ();
    }

}
