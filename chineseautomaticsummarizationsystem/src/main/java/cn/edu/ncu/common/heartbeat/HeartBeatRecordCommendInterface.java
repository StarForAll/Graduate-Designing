package cn.edu.ncu.common.heartbeat;


import cn.edu.ncu.common.core.pojo.dto.HeartBeatRecordDTO;

/**
* @Description:
* @Author: simajinqiang
* @Date: 2018/7/9 14:06
*/
public interface HeartBeatRecordCommendInterface {
    /**
     * 处理
     * @param heartBeatRecord
     */
    void handler(HeartBeatRecordDTO heartBeatRecord);

}
