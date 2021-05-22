package cn.edu.ncu.dao.entity;

import cn.edu.ncu.common.core.pojo.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Table;

/**
 * @Author: XiongZhiCong
 * @Description: 数据范围与角色关系
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
@Table(name="role_data_scope")
public class DataScopeRole extends BaseEntity {

    /**
     * 数据范围id
     */
    private Integer dataScopeType;
    /**
     * 数据范围类型
     */
    private Integer viewType;
    /**
     * 角色id
     */
    private Long roleId;
}
