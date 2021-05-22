package cn.edu.ncu.dao.mapper;

import cn.edu.ncu.common.core.dao.CommonMapper;
import cn.edu.ncu.dao.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: XiongZhiCong
 * @Description: 角色Mapper
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Mapper
public interface RoleMapper extends CommonMapper<Role> {

    void update(Role role);
    Role getByRoleName(@Param("roleName") String name);

}
