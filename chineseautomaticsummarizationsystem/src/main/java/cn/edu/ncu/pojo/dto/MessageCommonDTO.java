package cn.edu.ncu.pojo.dto;

import cn.edu.ncu.common.constant.MessageTypeEnum;
import lombok.Data;

/**
 * @Author: XiongZhiCong
 * @Description: 通知DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class MessageCommonDTO {
    /**
     * 消息类型 {@link MessageTypeEnum}
     */
    private Integer messageType;

    /**
     * 具体消息内容
     */
    private String jsonStr;


}
