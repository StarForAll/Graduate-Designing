package cn.edu.ncu.service.impl;

import cn.edu.ncu.common.constant.JudgeEnum;
import cn.edu.ncu.common.util.dao.IdGeneratorUtil;
import cn.edu.ncu.dao.entity.Notice;
import cn.edu.ncu.dao.entity.NoticeReceiveRecord;
import cn.edu.ncu.dao.mapper.NoticeMapper;
import cn.edu.ncu.dao.mapper.NoticeReceiveRecordMapper;
import cn.edu.ncu.pojo.bo.RequestTokenBO;
import cn.edu.ncu.pojo.dto.NoticeUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * [  ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2018 1024lab.netInc. All rights reserved.
 * @date 2019/7/13 0013 下午 17:33
 * @since JDK1.8
 */
@Service
public class NoticeManage {

    @Resource
    private NoticeMapper noticeMapper;
    @Resource
    private NoticeReceiveRecordMapper noticeReceiveRecordMapper;
    @Autowired
    private IdGeneratorUtil idGeneratorUtil;
    /**
     * 发送消息
     * @param entity
     * @param requestToken
     */
    public void send(Notice entity, RequestTokenBO requestToken){

        entity.setSendStatus(JudgeEnum.YES.getValue());
        noticeMapper.update(entity);
        //默认发件人 已读此消息
        NoticeReceiveRecord recordEntity = new NoticeReceiveRecord();
        recordEntity.setId(idGeneratorUtil.snowflakeId());
        recordEntity.setEmployeeId(requestToken.getRequestUserId());
        recordEntity.setNoticeId(entity.getId());
        Date date=new Date(System.currentTimeMillis());
        recordEntity.setCreateTime(date);
        recordEntity.setUpdateTime(date);
        noticeReceiveRecordMapper.insert(recordEntity);
    }


    /**
     * 保存读取记录
     * @param noticeId
     * @param requestToken
     */
    public void saveReadRecord(Long noticeId, RequestTokenBO requestToken){
        NoticeReceiveRecord recordEntity = new NoticeReceiveRecord();
        recordEntity.setId(idGeneratorUtil.snowflakeId());
        recordEntity.setEmployeeId(requestToken.getRequestUserId());
        recordEntity.setNoticeId(noticeId);
        Date date=new Date(System.currentTimeMillis());
        recordEntity.setCreateTime(date);
        recordEntity.setUpdateTime(date);
        noticeReceiveRecordMapper.insert(recordEntity);
    }


    /**
     * 消息删除
     * @param entity
     */
    public void delete(Notice entity) {
        if(JudgeEnum.YES.getValue().equals(entity.getSendStatus())){
            //消息已发送 执行逻辑删除
            noticeMapper.logicDeleteById(entity.getId(),JudgeEnum.YES.getValue());
        }else{
            //消息未发送 执行真实删除
            noticeMapper.deleteByPrimaryKey(entity.getId());
        }
    }

    /**
     * 更新消息
     * @param entity
     * @param updateDTO
     */
    public void update(Notice entity, NoticeUpdateDTO updateDTO) {
        entity.setTitle(updateDTO.getTitle());
        entity.setContent(updateDTO.getContent());
        entity.setSendStatus(JudgeEnum.NO.getValue());
        entity.setDeleted(JudgeEnum.NO.getValue());
        noticeMapper.update(entity);
    }
}
