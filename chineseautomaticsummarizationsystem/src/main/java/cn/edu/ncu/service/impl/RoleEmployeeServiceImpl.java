package cn.edu.ncu.service.impl;

import cn.edu.ncu.common.constant.RoleResponseCodeConst;
import cn.edu.ncu.common.util.bean.BeanUtil;
import cn.edu.ncu.common.util.dao.IdGeneratorUtil;
import cn.edu.ncu.dao.entity.Department;
import cn.edu.ncu.dao.entity.Role;
import cn.edu.ncu.dao.entity.RoleEmployee;
import cn.edu.ncu.dao.mapper.DepartmentMapper;
import cn.edu.ncu.dao.mapper.RoleEmployeeMapper;
import cn.edu.ncu.dao.mapper.RoleMapper;
import cn.edu.ncu.pojo.dto.EmployeeDTO;
import cn.edu.ncu.service.RoleEmployeeService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.RoleBatchDTO;
import cn.edu.ncu.pojo.dto.RoleQueryDTO;
import cn.edu.ncu.pojo.vo.EmployeeVO;
import cn.edu.ncu.pojo.vo.RoleSelectedVO;
import javax.annotation.Resource;
import java.util.List;

/**
 * 角色管理业务
 *
 * @author zzr
 * @date 2019/4/3
 */
@Service
public class RoleEmployeeServiceImpl implements RoleEmployeeService {
    @Autowired
    private IdGeneratorUtil idGeneratorUtil;
    @Resource
    private RoleEmployeeMapper roleEmployeeMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private DepartmentMapper departmentMapper;

    /**
     * 通过角色id，分页获取成员员工列表
     *
     * @param queryDTO
     * @return
     */
    @Override
    public ResponseDTO<PageResultDTO<EmployeeVO>> listEmployeeByName(RoleQueryDTO queryDTO) {
        PageResultDTO<EmployeeVO> pageResultDTO=new PageResultDTO<>();
        int total=0;
        Boolean searchCount = queryDTO.getSearchCount();
        if(searchCount ==null||searchCount){
            total=roleEmployeeMapper.selectEmployeeCountByName(queryDTO);
        }
        long pages=total/queryDTO.getPageSize()+1;
        pageResultDTO.setPageNum(Long.valueOf(queryDTO.getPageNum()));
        pageResultDTO.setPages(pages);
        pageResultDTO.setTotal((long) total);
        pageResultDTO.setPageSize(Long.valueOf(queryDTO.getPageSize()));

        List<EmployeeDTO> employeeDTOS = roleEmployeeMapper.selectEmployeeByNamePage( queryDTO);
        employeeDTOS.stream().filter(e -> e.getDepartmentId() != null).forEach(employeeDTO -> {
            Department departmentEntity = departmentMapper.selectByPrimaryKey(employeeDTO.getDepartmentId());
            employeeDTO.setDepartmentName(departmentEntity.getName());
        });
        pageResultDTO.setList(BeanUtil.copyList(employeeDTOS,EmployeeVO.class));
        return ResponseDTO.succData(pageResultDTO);
    }

    @Override
    public ResponseDTO<List<EmployeeVO>> getAllEmployeeByRoleId(Long roleId) {
        List<EmployeeDTO> employeeDTOS = roleEmployeeMapper.selectEmployeeByRoleId(roleId);
        List<EmployeeVO> list = BeanUtil.copyList(employeeDTOS, EmployeeVO.class);
        return ResponseDTO.succData(list);
    }

    /**
     * 移除员工角色
     *
     * @param employeeId
     * @param roleId
     * @return ResponseDTO<String>
     */
    @Override
    public ResponseDTO<String> removeEmployeeRole(Long employeeId, Long roleId) {
        if (null == employeeId || null == roleId) {
            return ResponseDTO.wrap(RoleResponseCodeConst.ERROR_PARAM);
        }
        roleEmployeeMapper.deleteByEmployeeIdRoleId(employeeId, roleId);
        return ResponseDTO.succ();
    }

    /**
     * 批量删除角色的成员员工
     *
     * @param removeDTO
     * @return ResponseDTO<String>
     */
    @Override
    public ResponseDTO<String> batchRemoveEmployeeRole(RoleBatchDTO removeDTO) {
        List<Long> employeeIdList = removeDTO.getEmployeeIds();
        roleEmployeeMapper.batchDeleteEmployeeRole(removeDTO.getRoleId(), employeeIdList);
        return ResponseDTO.succ();
    }

    /**
     * 批量添加角色的成员员工
     *
     * @param addDTO
     * @return ResponseDTO<String>
     */
    @Override
    public ResponseDTO<String> batchAddEmployeeRole(RoleBatchDTO addDTO) {
        Long roleId = addDTO.getRoleId();
        List<Long> employeeIdList = addDTO.getEmployeeIds();
        roleEmployeeMapper.deleteByRoleId(roleId);
        List<RoleEmployee> roleRelationEntities = Lists.newArrayList();
        RoleEmployee employeeRoleRelationEntity;
        for (Long employeeId : employeeIdList) {
            employeeRoleRelationEntity = new RoleEmployee();
            employeeRoleRelationEntity.setId(idGeneratorUtil.snowflakeId());
            employeeRoleRelationEntity.setRoleId(roleId);
            employeeRoleRelationEntity.setEmployeeId(employeeId);
            roleRelationEntities.add(employeeRoleRelationEntity);
        }
        roleEmployeeMapper.batchInsert(roleRelationEntities);
        return ResponseDTO.succ();
    }

    /**
     * 通过员工id获取员工角色
     *
     * @param employeeId
     * @return
     */
    @Override
    public ResponseDTO<List<RoleSelectedVO>> getRolesByEmployeeId(Long employeeId) {
        List<Long> roleIds = roleEmployeeMapper.selectRoleIdByEmployeeId(employeeId);
        List<Role> roleList = roleMapper.selectAll();
        List<RoleSelectedVO> result = BeanUtil.copyList(roleList, RoleSelectedVO.class);
        result.stream().forEach(item -> item.setSelected(roleIds.contains(item.getId())));
        return ResponseDTO.succData(result);
    }
}
