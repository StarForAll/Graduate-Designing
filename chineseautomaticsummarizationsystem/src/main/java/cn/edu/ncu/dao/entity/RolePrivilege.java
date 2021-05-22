package cn.edu.ncu.dao.entity;

import cn.edu.ncu.common.core.pojo.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Table;

/**
 * @Author: XiongZhiCong
 * @Description: 角色 权限关系
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
@Table(name="role_privilege")
public class RolePrivilege extends BaseEntity {

    /**
     * 角色 id
     */
    private Long roleId;

    /**
     * 功能权限 id
     */
    private String privilegeKey;


}
