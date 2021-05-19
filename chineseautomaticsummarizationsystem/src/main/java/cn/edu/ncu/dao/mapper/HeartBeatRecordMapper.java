package cn.edu.ncu.dao.mapper;

import cn.edu.ncu.common.core.dao.CommonMapper;
import cn.edu.ncu.common.core.pojo.dto.PageParamDTO;
import cn.edu.ncu.dao.entity.HeartBeatRecord;
import cn.edu.ncu.pojo.vo.HeartBeatRecordVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 心跳日志数据库操作
 *
 * @author : simajinqiang
 * Date: 2018/7/9
 * Time: 17:37
 */
@Mapper
public interface HeartBeatRecordMapper extends CommonMapper<HeartBeatRecord> {

    /**
     * 新增心跳日志
     *
     * @param HeartBeatRecord
     */
    void insertHeartBeat(HeartBeatRecord HeartBeatRecord);

    /**
     * 更新心跳日志
     *
     * @param id
     * @param heartBeatTime
     */
    void updateHeartBeatTimeById(@Param("id") Long id, @Param("heartBeatTime") Date heartBeatTime);

    /**
     * 查询心跳日志
     *
     * @param HeartBeatRecord
     * @return
     */
    HeartBeatRecord query(HeartBeatRecord HeartBeatRecord);

    Integer pageQueryCount(PageParamDTO pageParamDTO);
    /**
     * 分页查询心跳记录
     * @return
     */
    List<HeartBeatRecordVO> pageQuery(@Param("queryDTO")PageParamDTO queryDTO);
}
