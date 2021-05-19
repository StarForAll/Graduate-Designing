package cn.edu.ncu.service;

import cn.edu.ncu.common.core.pojo.dto.PageParamDTO;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.bo.RequestTokenBO;
import cn.edu.ncu.pojo.dto.*;
import cn.edu.ncu.pojo.vo.NoticeDetailVO;
import cn.edu.ncu.pojo.vo.NoticeVO;
import org.springframework.transaction.annotation.Transactional;


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
public interface NoticeService {

    /**
     * @author yandanyang
     * @description 分页查询
     * @date 2019-07-11 16:19:48
     */
     ResponseDTO<PageResultDTO<NoticeVO>> queryByPage(NoticeQueryDTO queryDTO) ;

    /**
     * 获取当前登录人的消息列表
     *
     * @param queryDTO
     * @param requestToken
     * @return
     */
     ResponseDTO<PageResultDTO<NoticeReceiveDTO>> queryReceiveByPage(NoticeReceiveQueryDTO queryDTO, RequestTokenBO requestToken);

    /**
     * 获取我的未读消息
     *
     * @param queryDTO
     * @param requestToken
     * @return
     */
     ResponseDTO<PageResultDTO<NoticeVO>> queryUnreadByPage(PageParamDTO queryDTO, RequestTokenBO requestToken) ;

    /**
     * @author yandanyang
     * @description 添加
     * @date 2019-07-11 16:19:48
     */
     ResponseDTO<String> add(NoticeAddDTO addDTO, RequestTokenBO requestToken);

    /**
     * @author yandanyang
     * @description 编辑
     * @date 2019-07-11 16:19:48
     */
    @Transactional(rollbackFor = Exception.class)
     ResponseDTO<String> update(NoticeUpdateDTO updateDTO) ;

    /**
     * @author yandanyang
     * @description 删除
     * @date 2019-07-11 16:19:48
     */
     ResponseDTO<String> delete(Long id);

    /**
     * @author yandanyang
     * @description 根据ID查询
     * @date 2019-07-11 16:19:48
     */
     ResponseDTO<NoticeDetailVO> detail(Long id) ;


    /**
     * 发送给所有在线用户未读消息数
     *
     * @param id
     * @param requestToken
     * @return
     */
     ResponseDTO<NoticeDetailVO> send(Long id, RequestTokenBO requestToken) ;


    /**
     * 读取消息
     *
     * @param id
     * @param requestToken
     * @return
     */
     ResponseDTO<NoticeDetailVO> read(Long id, RequestTokenBO requestToken) ;
}
