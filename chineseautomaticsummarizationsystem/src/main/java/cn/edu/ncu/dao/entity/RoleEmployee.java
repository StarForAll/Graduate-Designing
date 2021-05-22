package cn.edu.ncu.dao.entity;

import cn.edu.ncu.common.core.pojo.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Table;

/**
 * @Author: XiongZhiCong
 * @Description: 角色 员工关系
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
@Table(name="role_employee")
public class RoleEmployee extends BaseEntity {

    private Long roleId;

    private Long employeeId;
}
