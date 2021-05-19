package cn.edu.ncu.common.core.pojo.dto;

import lombok.Data;

import java.util.Date;


/**
 * @Author: XiongZhiCong
 * @Description: 心跳记录日志
 * @Date: Created in 14:16 2021/4/8
 * @Modified By:
 */
@Data
public class HeartBeatRecordDTO {

    /**
     * 项目名字
     */
    private String projectPath;
    /**
     * 服务器ip
     */
    private String serverIp;
    /**
     * 进程号
     */
    private Integer processNo;
    /**
     * 进程开启时间
     */
    private Date processStartTime;
    /**
     * 心跳当前时间
     */
    private Date heartBeatTime;


}
