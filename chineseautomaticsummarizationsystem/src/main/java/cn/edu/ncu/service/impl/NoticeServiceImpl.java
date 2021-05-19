package cn.edu.ncu.service.impl;

import cn.edu.ncu.common.constant.JudgeEnum;
import cn.edu.ncu.common.constant.ResponseCodeConst;
import cn.edu.ncu.common.core.pojo.dto.PageParamDTO;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.common.util.bean.BeanUtil;
import cn.edu.ncu.common.util.dao.IdGeneratorUtil;
import cn.edu.ncu.common.util.web.RequestTokenUtil;
import cn.edu.ncu.dao.entity.Notice;
import cn.edu.ncu.dao.entity.NoticeReceiveRecord;
import cn.edu.ncu.dao.mapper.NoticeMapper;
import cn.edu.ncu.dao.mapper.NoticeReceiveRecordMapper;
import cn.edu.ncu.pojo.bo.RequestTokenBO;
import cn.edu.ncu.pojo.dto.*;
import cn.edu.ncu.pojo.vo.NoticeDetailVO;
import cn.edu.ncu.pojo.vo.NoticeVO;
import cn.edu.ncu.service.NoticeService;
import cn.edu.ncu.service.WebSocketServer;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * [  ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2019 1024lab.netInc. All rights reserved.
 * @date 2019-07-11 16:19:48
 * @since JDK1.8
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    @Resource
    private NoticeMapper noticeMapper;

    @Resource
    private NoticeReceiveRecordMapper noticeReceiveRecordMapper;

    @Autowired
    private NoticeManage noticeManage;
    @Autowired
    private IdGeneratorUtil idGeneratorUtil;
    /**
     * @author yandanyang
     * @description 分页查询
     * @date 2019-07-11 16:19:48
     */
    @Override
    public ResponseDTO<PageResultDTO<NoticeVO>> queryByPage(NoticeQueryDTO queryDTO) {
        PageResultDTO<NoticeVO> pageResultDTO =new PageResultDTO<>();
        queryDTO.setDeleted(JudgeEnum.NO.getValue());
        queryDTO.setUserId(RequestTokenUtil.getRequestUser().getRequestUserId());
        int total=0;
        Boolean searchCount = queryDTO.getSearchCount();
        if(searchCount ==null||searchCount){
            total=noticeMapper.queryCount(queryDTO);
        }
        long pages=total/queryDTO.getPageSize()+1;
        pageResultDTO.setPageNum(Long.valueOf(queryDTO.getPageNum()));
        pageResultDTO.setPages(pages);
        pageResultDTO.setTotal((long) total);
        pageResultDTO.setPageSize(Long.valueOf(queryDTO.getPageSize()));
        List<NoticeVO> dtoList = noticeMapper.queryByPage(queryDTO);
        pageResultDTO.setList(dtoList);
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * 获取当前登录人的消息列表
     *
     * @param queryDTO
     * @param requestToken
     * @return
     */
    @Override
    public ResponseDTO<PageResultDTO<NoticeReceiveDTO>> queryReceiveByPage(NoticeReceiveQueryDTO queryDTO, RequestTokenBO requestToken) {
        PageResultDTO<NoticeReceiveDTO> pageResultDTO = new PageResultDTO<>();
        queryDTO.setEmployeeId(requestToken.getRequestUserId());
        queryDTO.setSendStatus(JudgeEnum.YES.getValue());
        int total=0;
        Boolean searchCount = queryDTO.getSearchCount();
        if(searchCount ==null||searchCount){
            total=noticeMapper.queryReceiveCount(queryDTO);
        }
        long pages=total/queryDTO.getPageSize()+1;
        pageResultDTO.setPageNum(Long.valueOf(queryDTO.getPageNum()));
        pageResultDTO.setPages(pages);
        pageResultDTO.setTotal((long) total);
        pageResultDTO.setPageSize(Long.valueOf(queryDTO.getPageSize()));

        List<NoticeReceiveDTO> dtoList = noticeMapper.queryReceiveByPage( queryDTO);
        dtoList.forEach(e -> {
            if (e.getReceiveTime() == null) {
                e.setReadStatus(JudgeEnum.NO.getValue());
            } else {
                e.setReadStatus(JudgeEnum.YES.getValue());
            }
        });
        pageResultDTO.setList(dtoList);
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * 获取我的未读消息
     *
     * @param queryDTO
     * @param requestToken
     * @return
     */
    @Override
    public ResponseDTO<PageResultDTO<NoticeVO>> queryUnreadByPage(PageParamDTO queryDTO, RequestTokenBO requestToken) {
        PageResultDTO<NoticeVO> pageResultDTO = new PageResultDTO<>();
        int total=0;
        Boolean searchCount = queryDTO.getSearchCount();
        if(searchCount ==null||searchCount){
            total=noticeMapper.queryUnreadCount(requestToken.getRequestUserId(), JudgeEnum.YES.getValue());
        }
        long pages=total/queryDTO.getPageSize()+1;
        pageResultDTO.setPageNum(Long.valueOf(queryDTO.getPageNum()));
        pageResultDTO.setPages(pages);
        pageResultDTO.setTotal((long) total);
        pageResultDTO.setPageSize(Long.valueOf(queryDTO.getPageSize()));

        List<NoticeVO> dtoList = noticeMapper.queryUnreadByPage(requestToken.getRequestUserId(), JudgeEnum.YES.getValue());
        pageResultDTO.setList(dtoList);
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * @author yandanyang
     * @description 添加
     * @date 2019-07-11 16:19:48
     */
    @Override
    public ResponseDTO<String> add(NoticeAddDTO addDTO, RequestTokenBO requestToken) {
        Notice entity = BeanUtil.copy(addDTO, Notice.class);
        if(entity.getId()==null){
            entity.setId(idGeneratorUtil.snowflakeId());
        }
        entity.setCreateTime(new Date(System.currentTimeMillis()));
        entity.setUpdateTime(new Date(System.currentTimeMillis()));
        entity.setCreateUser(requestToken.getRequestUserId());
        entity.setSendStatus(JudgeEnum.NO.getValue());
        entity.setDeleted(JudgeEnum.NO.getValue());
        noticeMapper.insert(entity);
        return ResponseDTO.succ();
    }

    /**
     * @author yandanyang
     * @description 编辑
     * @date 2019-07-11 16:19:48
     */
    @Override
    public ResponseDTO<String> update(NoticeUpdateDTO updateDTO) {
        Notice entity = noticeMapper.selectByPrimaryKey(updateDTO.getId());
        if (entity == null) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM, "此系统通知不存在");
        }
        if (JudgeEnum.YES.getValue().equals(entity.getSendStatus())) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM, "此系统通知已发送无法修改");
        }
        noticeManage.update(entity, updateDTO);
        return ResponseDTO.succ();
    }

    /**
     * @author yandanyang
     * @description 删除
     * @date 2019-07-11 16:19:48
     */
    @Override
    public ResponseDTO<String> delete(Long id) {
        Notice entity = noticeMapper.selectByPrimaryKey(id);
        if (entity == null) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM, "此系统通知不存在");
        }
        noticeManage.delete(entity);
        return ResponseDTO.succ();
    }

    /**
     * @author yandanyang
     * @description 根据ID查询
     * @date 2019-07-11 16:19:48
     */
    @Override
    public ResponseDTO<NoticeDetailVO> detail(Long id) {
        NoticeDetailVO noticeDTO = noticeMapper.detail(id);
        return ResponseDTO.succData(noticeDTO);
    }

    /**
     * 获取某人的未读消息数
     *
     * @param employeeId
     * @return
     */
    private Integer getUnreadCount(Long employeeId) {
        return noticeMapper.noticeUnreadCount(employeeId, JudgeEnum.YES.getValue());
    }

    /**
     * 发送给所有在线用户未读消息数
     *
     * @param id
     * @param requestToken
     * @return
     */
    @Override
    public ResponseDTO<NoticeDetailVO> send(Long id, RequestTokenBO requestToken) {
        Notice entity = noticeMapper.selectByPrimaryKey(id);
        if (entity == null) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM, "此系统通知不存在");
        }
        noticeManage.send(entity, requestToken);
        this.sendMessage(requestToken);
        return ResponseDTO.succ();
    }

    /**
     * 发送系统通知 ，发送人不进行接收,需再事务外调用 以防止数据隔离级别不同造成未读消息数异常
     *
     * @param requestToken
     */
    private void sendMessage(RequestTokenBO requestToken) {
        List<Long> onLineEmployeeIds = WebSocketServer.getOnLineUserList();
        if (CollectionUtils.isEmpty(onLineEmployeeIds)) {
            return;
        }
        //在线用户已读消息数
        Map<Long, Integer> readCountMap = new HashMap<>();
        List<NoticeReadCountDTO> readCountList = noticeMapper.readCount(onLineEmployeeIds);
        if (CollectionUtils.isNotEmpty(readCountList)) {
            readCountMap = readCountList.stream().collect(Collectors.toMap(NoticeReadCountDTO :: getEmployeeId, NoticeReadCountDTO :: getReadCount));
        }
        //已发送消息数
        Integer noticeCount = noticeMapper.noticeCount(JudgeEnum.YES.getValue());
        for (Long employeeId : onLineEmployeeIds) {
            Integer readCount = readCountMap.get(employeeId) == null ? 0 : readCountMap.get(employeeId);
            Integer unReadCount = noticeCount - readCount;
            if (! requestToken.getRequestUserId().equals(employeeId)) {
                WebSocketServer.sendOneOnLineUser(unReadCount.toString(), employeeId);
            }
        }
    }

    /**
     * 读取消息
     *
     * @param id
     * @param requestToken
     * @return
     */
    @Override
    public ResponseDTO<NoticeDetailVO> read(Long id, RequestTokenBO requestToken) {
        NoticeDetailVO noticeDTO = noticeMapper.detail(id);
        NoticeReceiveRecord recordEntity = noticeReceiveRecordMapper.selectByEmployeeAndNotice(requestToken.getRequestUserId(), id);
        if (recordEntity != null) {
            return ResponseDTO.succData(noticeDTO);
        }
        noticeManage.saveReadRecord(id, requestToken);
        this.sendMessage(requestToken);
        return ResponseDTO.succData(noticeDTO);
    }
}
