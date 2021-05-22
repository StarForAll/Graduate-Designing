package cn.edu.ncu.pojo.dto;

import lombok.Data;

/**
 * @Author: XiongZhiCong
 * @Description: websocket心跳服务DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class WebSocketHeartBeatDTO {

    /**
     * 当前登录人id
     */
    private Long employeeId;

}
