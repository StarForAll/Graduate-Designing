package cn.edu.ncu.dao.mapper;

import cn.edu.ncu.common.core.dao.CommonMapper;
import cn.edu.ncu.dao.entity.RoleEmployee;
import cn.edu.ncu.pojo.dto.EmployeeDTO;
import cn.edu.ncu.pojo.dto.RoleQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: 角色用户Mapper
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Mapper
public interface RoleEmployeeMapper extends CommonMapper<RoleEmployee> {

    /**
     * 根据员工id 查询所有的角色
     * @param employeeId
     * @return
     */
    List<Long> selectRoleIdByEmployeeId(@Param("employeeId") Long employeeId);

    Integer selectEmployeeCountByName(@Param("queryDTO") RoleQueryDTO queryDTO);
    /**
     *
     * @param queryDTO
     * @return
     */
    List<EmployeeDTO> selectEmployeeByNamePage( @Param("queryDTO") RoleQueryDTO queryDTO);

    /**
     *
     * @param roleId
     * @return
     */
    List<EmployeeDTO> selectEmployeeByRoleId(@Param("roleId") Long roleId);
    /**
     * 根据员工信息删除
     * @param employeeId
     */
    void deleteByEmployeeId(@Param("employeeId") Long employeeId);

    /**
     * 删除某个角色的所有关系
     * @param roleId
     */
    void deleteByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据员工和 角色删除关系
     * @param employeeId
     * @param roleId
     */
    void deleteByEmployeeIdRoleId(@Param("employeeId") Long employeeId, @Param("roleId") Long roleId);

    /**
     * 批量删除某个角色下的某批用户的关联关系
     * @param roleId
     * @param employeeIds
     */
    void batchDeleteEmployeeRole(@Param("roleId") Long roleId, @Param("employeeIds") List<Long> employeeIds);

    /**
     * 批量新增
     * @param roleRelationList
     */
    void batchInsert(List<RoleEmployee> roleRelationList);
}
