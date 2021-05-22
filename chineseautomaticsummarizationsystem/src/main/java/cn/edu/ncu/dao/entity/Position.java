package cn.edu.ncu.dao.entity;

import cn.edu.ncu.common.core.pojo.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Table;

/**
 * @Author: XiongZhiCong
 * @Description: 岗位
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
@Table(name="position")
public class Position extends BaseEntity {

    /**
     * 岗位名称
     */
    private String positionName;

    /**
     * 岗位描述
     */
    private String remark;

}
