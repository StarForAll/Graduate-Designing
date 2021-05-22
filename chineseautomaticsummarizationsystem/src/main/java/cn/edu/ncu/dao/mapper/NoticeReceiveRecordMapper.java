package cn.edu.ncu.dao.mapper;

import cn.edu.ncu.common.core.dao.CommonMapper;
import cn.edu.ncu.dao.entity.NoticeReceiveRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: 通知接收记录Mapper
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Mapper
public interface NoticeReceiveRecordMapper extends CommonMapper<NoticeReceiveRecord> {

    /**
     * 批量删除
     *
     * @param noticeId
     * @return
     */
    void deleteByNoticeId(@Param("noticeId") Long noticeId);

    /**
     * 批量插入
     *
     * @param rolePrivilegeList
     */
    void batchInsert(List<NoticeReceiveRecord> rolePrivilegeList);

    /**
     * 根据员工和系统通知获取读取记录
     *
     * @param employeeId
     * @param noticeId
     * @return
     */
    NoticeReceiveRecord selectByEmployeeAndNotice(@Param("employeeId") Long employeeId, @Param("noticeId") Long noticeId);
}
