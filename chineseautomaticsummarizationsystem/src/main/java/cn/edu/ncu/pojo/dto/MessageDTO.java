package cn.edu.ncu.pojo.dto;

import cn.edu.ncu.common.constant.MessageTypeEnum;
import lombok.Builder;
import lombok.Data;

/**
 * @Author: XiongZhiCong
 * @Description: 通知DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
@Builder
public class MessageDTO {

    /**
     * 消息类型 {@link MessageTypeEnum}
     */
    private Integer messageType;

    /**
     * 消息体
     */
    private String message;

    /**
     * 发送者
     */
    private Long fromUserId;

    /**
     * 接收者，系统通知可为null
     */
    private Long toUserId;

}
