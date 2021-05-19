package cn.edu.ncu.dao.entity;

import cn.edu.ncu.common.core.pojo.entity.BaseEntity;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Table;

/**
 * <p>
 * 各种单据操作记录
 *
 * </p>
 *
 * @author anders
 * @since 2018-01-09
 */

@Data
@Builder
@Table(name="order_operate_log")
public class OrderOperateLog extends BaseEntity {

    /**
     * 各种单据的id
     */
    private Long orderId;
    /**
     * 单据类型
     */
    private Integer orderType;
    /**
     * 操作类型
     */
    private Integer operateType;
    /**
     * 操作类型 对应的中文
     */
    private String operateContent;
    /**
     * 操作备注
     */
    private String operateRemark;
    /**
     * 员工id
     */
    private Long employeeId;
    /**
     * 员工名称
     */
    private String employeeName;
    /**
     * 额外信息
     */
    private String extData;



}
