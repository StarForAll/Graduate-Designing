package cn.edu.ncu.dao.mapper;

import cn.edu.ncu.common.core.dao.CommonMapper;
import cn.edu.ncu.dao.entity.Privilege;
import cn.edu.ncu.dao.entity.RolePrivilege;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: 角色权限Mapper
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Mapper
public interface RolePrivilegeMapper extends CommonMapper<RolePrivilege> {

    /**
     * 根据角色id删除
     * @param roleId
     */
    void deleteByRoleId(@Param("roleId") Long roleId);

    /**
     * 删除权限所关联的角色信息
     * @param privilegeKeyList
     */
    void deleteByPrivilegeKey(@Param("privilegeKeyList") List<String> privilegeKeyList);


    /**
     * 批量添加
     * @param rolePrivilegeList
     */
    void batchInsert(List<RolePrivilege> rolePrivilegeList);

    /**
     * 查询某批角色的权限
     * @param roleIds
     * @return
     */
    List<Privilege> listByRoleIds(@Param("roleIds") List<Long> roleIds, @Param("normalStatus") Integer normalStatus);

    /**
     * 查询某个角色的权限
     * @param roleId
     * @return
     */
    List<Privilege> listByRoleId(@Param("roleId") Long roleId);
}
