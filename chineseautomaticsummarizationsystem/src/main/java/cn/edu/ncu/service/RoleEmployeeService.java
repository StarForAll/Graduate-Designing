package cn.edu.ncu.service;

import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.RoleBatchDTO;
import cn.edu.ncu.pojo.dto.RoleQueryDTO;
import cn.edu.ncu.pojo.vo.EmployeeVO;
import cn.edu.ncu.pojo.vo.RoleSelectedVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色管理业务
 *
 * @author zzr
 * @date 2019/4/3
 */
public interface RoleEmployeeService {
    /**
     * 通过角色id，分页获取成员员工列表
     *
     * @param queryDTO
     * @return
     */
     ResponseDTO<PageResultDTO<EmployeeVO>> listEmployeeByName(RoleQueryDTO queryDTO) ;

     ResponseDTO<List<EmployeeVO>> getAllEmployeeByRoleId(Long roleId) ;

    /**
     * 移除员工角色
     *
     * @param employeeId
     * @param roleId
     * @return ResponseDTO<String>
     */
    @Transactional(rollbackFor = Exception.class)
     ResponseDTO<String> removeEmployeeRole(Long employeeId, Long roleId);

    /**
     * 批量删除角色的成员员工
     *
     * @param removeDTO
     * @return ResponseDTO<String>
     */
    @Transactional(rollbackFor = Exception.class)
     ResponseDTO<String> batchRemoveEmployeeRole(RoleBatchDTO removeDTO) ;

    /**
     * 批量添加角色的成员员工
     *
     * @param addDTO
     * @return ResponseDTO<String>
     */
    @Transactional(rollbackFor = Exception.class)
    ResponseDTO<String> batchAddEmployeeRole(RoleBatchDTO addDTO) ;

    /**
     * 通过员工id获取员工角色
     *
     * @param employeeId
     * @return
     */
     ResponseDTO<List<RoleSelectedVO>> getRolesByEmployeeId(Long employeeId);
}
