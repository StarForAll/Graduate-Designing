package cn.edu.ncu.dao.entity;

import cn.edu.ncu.common.core.pojo.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Table;

/**
 * @Author: XiongZhiCong
 * @Description: 角色
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
@Table(name="role")
public class Role extends BaseEntity {


    private String roleName;

    private String remark;
}
