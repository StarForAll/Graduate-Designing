package cn.edu.ncu.pojo.dto;

import lombok.Data;

/**
 * @Author: XiongZhiCong
 * @Description: 通知阅读DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class NoticeReadCountDTO {
    /**
     * 员工id
     */
    private Long employeeId;
    /**
     * 已读消息数
     */
    private Integer readCount;

}
