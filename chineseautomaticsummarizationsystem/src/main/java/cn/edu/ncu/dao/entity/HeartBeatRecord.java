package cn.edu.ncu.dao.entity;

import cn.edu.ncu.common.core.pojo.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: XiongZhiCong
 * @Description:心跳记录日志
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
@Table(name= "heart_beat_record")
public class HeartBeatRecord extends BaseEntity implements Serializable {

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
