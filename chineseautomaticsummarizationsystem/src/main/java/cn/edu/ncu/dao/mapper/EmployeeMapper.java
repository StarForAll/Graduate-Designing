package cn.edu.ncu.dao.mapper;

import cn.edu.ncu.common.core.dao.CommonMapper;
import cn.edu.ncu.dao.entity.Employee;
import cn.edu.ncu.pojo.dto.EmployeeDTO;
import cn.edu.ncu.pojo.dto.EmployeeQueryDTO;
import cn.edu.ncu.pojo.dto.EmployeeQueryExportDTO;
import cn.edu.ncu.pojo.vo.EmployeeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: 员工dao接口
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Mapper
public interface EmployeeMapper extends CommonMapper<Employee> {
    void update(Employee employee);
    Integer selectEmployeeCount(@Param("queryDTO") EmployeeQueryDTO queryDTO);
    /**
     * 查询员工列表
     *
     * @param queryDTO
     * @return
     */
    List<EmployeeDTO> selectEmployeeList( @Param("queryDTO") EmployeeQueryDTO queryDTO);

    /**
     * 不带分页查询员工列表
     *
     * @param queryDTO
     * @return
     */
    List<EmployeeDTO> selectEmployeeList(@Param("queryDTO") EmployeeQueryExportDTO queryDTO);

    /**
     * 批量更新禁用状态
     *
     * @param employeeIds
     * @param isDisabled
     */
    void batchUpdateStatus(@Param("employeeIds") List<Long> employeeIds, @Param("isDisabled") Integer isDisabled);

    /**
     * 登录
     *
     * @param loginName
     * @param loginPwd
     * @return
     */
    EmployeeDTO login(@Param("loginName") String loginName, @Param("loginPwd") String loginPwd);

    /**
     * 通过登录名查询
     *
     * @param loginName
     * @param isDisabled
     * @return
     */
    EmployeeDTO getByLoginName(@Param("loginName") String loginName, @Param("isDisabled") Integer isDisabled);

    /**
     * 通过手机号查询
     *
     * @param phone
     * @param isDisabled
     * @return
     */
    EmployeeDTO getByPhone(@Param("phone") String phone, @Param("isDisabled") Integer isDisabled);

    /**
     * 获取所有员工
     *
     * @return
     */
    List<EmployeeDTO> listAll();

    /**
     * 获取某个部门员工数
     *
     * @param depId
     * @param deleteFlag 可以null
     * @return
     */
    Integer countByDepartmentId(@Param("depId") Long depId, @Param("deleteFlag") Boolean deleteFlag);

    /**
     * 获取一批员工
     *
     * @param employeeIds
     * @return
     */
    List<EmployeeDTO> getEmployeeByIds(@Param("ids") Collection<Long> employeeIds);


    EmployeeDTO getEmployeeById(@Param("id") Long employeeId);

    /**
     * 获取某个部门的员工
     *
     * @param departmentId
     * @return
     */
    List<EmployeeVO> getEmployeeIdByDeptId(@Param("departmentId") Long departmentId);

    /**
     * 获取某批部门的员工
     *
     * @param departmentIds
     * @return
     */
    List<EmployeeDTO> getEmployeeIdByDeptIds(@Param("departmentIds") List<Long> departmentIds);


    /**
     * 员工重置密码
     *
     * @param employeeId
     * @param password
     * @return
     */
    Integer updatePassword(@Param("employeeId") Integer employeeId, @Param("password") String password);


}