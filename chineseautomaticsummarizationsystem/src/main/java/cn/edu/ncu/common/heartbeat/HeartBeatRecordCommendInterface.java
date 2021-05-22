package cn.edu.ncu.common.heartbeat;


import cn.edu.ncu.common.core.pojo.dto.HeartBeatRecordDTO;

/**
 * @Author: XiongZhiCong
 * @Description:
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public interface HeartBeatRecordCommendInterface {
    /**
     * 处理
     * @param heartBeatRecord
     */
    void handler(HeartBeatRecordDTO heartBeatRecord);

}
