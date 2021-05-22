package cn.edu.ncu.service;

import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.bo.EmployeeBO;
import cn.edu.ncu.pojo.bo.RequestTokenBO;
import cn.edu.ncu.pojo.dto.*;
import cn.edu.ncu.pojo.vo.EmployeeVO;
import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description:  员工管理
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public interface EmployeeService {



     List<EmployeeVO> getAllEmployee();

     EmployeeBO getById(Long id) ;

    /**
     * 查询员工列表
     *
     * @param queryDTO
     * @return
     */
     ResponseDTO<PageResultDTO<EmployeeVO>> selectEmployeeList(EmployeeQueryDTO queryDTO) ;

    /**
     * 新增员工
     *
     * @param employeeAddDto
     * @param requestToken
     * @return
     */
     ResponseDTO<String> addEmployee(EmployeeAddDTO employeeAddDto, RequestTokenBO requestToken);

    /**
     * 更新禁用状态
     *
     * @param employeeId
     * @param status
     * @return
     */
     ResponseDTO<String> updateStatus(Long employeeId, Integer status);

    /**
     * 批量更新员工状态
     *
     * @param batchUpdateStatusDTO
     * @return
     */
     ResponseDTO<String> batchUpdateStatus(EmployeeBatchUpdateStatusDTO batchUpdateStatusDTO);

    /**
     * 更新员工
     *
     * @param updateDTO
     * @return
     */
     ResponseDTO<String> updateEmployee(EmployeeUpdateDTO updateDTO);

    /**
     * 删除员工
     *
     * @param employeeId 员工ID
     * @return
     */
     ResponseDTO<String> deleteEmployeeById(Long employeeId);

    /**
     * 更新用户角色
     *
     * @param updateRolesDTO
     * @return
     */
     ResponseDTO<String> updateRoles(EmployeeUpdateRolesDTO updateRolesDTO);

    /**
     * 更新密码
     *
     * @param updatePwdDTO
     * @param requestToken
     * @return
     */
     ResponseDTO<String> updatePwd(EmployeeUpdatePwdDTO updatePwdDTO, RequestTokenBO requestToken);

     ResponseDTO<List<EmployeeVO>> getEmployeeByDeptId(Long departmentId);

    /**
     * 重置密码
     *
     * @param employeeId
     * @return
     */
     ResponseDTO resetPasswd(Integer employeeId) ;

}
