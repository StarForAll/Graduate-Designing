package cn.edu.ncu.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: XiongZhiCong
 * @Description: 通知接收记录DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class NoticeReceiveQueryDTO extends NoticeQueryDTO{

    @ApiModelProperty(value = "当前登录人",hidden = true)
    private Long employeeId;

    @ApiModelProperty(value = "发送状态",hidden = true)
    private Integer sendStatus;

}
