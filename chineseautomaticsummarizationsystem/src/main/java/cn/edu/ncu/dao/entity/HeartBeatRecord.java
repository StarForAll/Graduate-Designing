package cn.edu.ncu.dao.entity;

import cn.edu.ncu.common.core.pojo.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 心跳记录日志
 * User: simajinqiang
 * Date: 2018/7/9
 * Time: 11:11
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
