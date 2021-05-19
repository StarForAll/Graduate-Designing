package cn.edu.ncu.dao.entity;

import cn.edu.ncu.common.core.pojo.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Table;

/**
 * [ 角色 员工关系]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2018 1024lab.netInc. All rights reserved.
 * @date 2019/3/27 0027 下午 13:01
 * @since JDK1.8
 */
@Data
@Table(name="role_employee")
public class RoleEmployee extends BaseEntity {

    private Long roleId;

    private Long employeeId;
}
