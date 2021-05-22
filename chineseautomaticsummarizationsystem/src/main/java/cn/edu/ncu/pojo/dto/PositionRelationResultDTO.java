package cn.edu.ncu.pojo.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Author: XiongZhiCong
 * @Description: 岗位关联关系DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class PositionRelationResultDTO {

    /**
     * 岗位ID
     */
    private Long positionId;

    /**
     * 员工ID
     */
    private Long employeeId;

    /**
     * 岗位名称
     */
    private String positionName;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

}
