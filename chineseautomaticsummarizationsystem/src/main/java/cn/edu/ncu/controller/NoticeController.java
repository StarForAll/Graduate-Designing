package cn.edu.ncu.controller;

import cn.edu.ncu.api.NoticeApi;
import cn.edu.ncu.common.annotation.NoValidPrivilege;
import cn.edu.ncu.common.util.web.RequestTokenUtil;
import cn.edu.ncu.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cn.edu.ncu.common.core.pojo.dto.PageParamDTO;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.*;
import cn.edu.ncu.pojo.vo.NoticeDetailVO;
import cn.edu.ncu.pojo.vo.NoticeVO;
import javax.validation.Valid;

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
@RestController
public class NoticeController implements NoticeApi {

    @Autowired
    private NoticeService noticeService;
    @Override
    @NoValidPrivilege
    public ResponseDTO<PageResultDTO<NoticeVO>> queryByPage(@RequestBody NoticeQueryDTO queryDTO) {
        return noticeService.queryByPage(queryDTO);
    }
    @Override
    @NoValidPrivilege
    public ResponseDTO<PageResultDTO<NoticeReceiveDTO>> queryReceiveByPage(@RequestBody NoticeReceiveQueryDTO queryDTO) {
        return noticeService.queryReceiveByPage(queryDTO, RequestTokenUtil.getRequestUser());
    }
    @Override
    @NoValidPrivilege
    public ResponseDTO<PageResultDTO<NoticeVO>> queryUnreadByPage(@RequestBody PageParamDTO queryDTO) {
        return noticeService.queryUnreadByPage(queryDTO, RequestTokenUtil.getRequestUser());
    }
    @Override
    @NoValidPrivilege
    public ResponseDTO<String> add(@RequestBody @Valid NoticeAddDTO addTO) {
        return noticeService.add(addTO, RequestTokenUtil.getRequestUser());
    }
    @Override
    @NoValidPrivilege
    public ResponseDTO<String> update(@RequestBody @Valid NoticeUpdateDTO updateDTO) {
        return noticeService.update(updateDTO);
    }
    @Override
    @NoValidPrivilege
    public ResponseDTO<String> delete(@PathVariable("id") Long id) {
        return noticeService.delete(id);
    }
    @Override
    public ResponseDTO<NoticeDetailVO> detail(@PathVariable("id") Long id) {
        return noticeService.detail(id);
    }
    @Override
    @NoValidPrivilege
    public ResponseDTO<NoticeDetailVO> send(@PathVariable("id") Long id) {
        return noticeService.send(id, RequestTokenUtil.getRequestUser());
    }

    @Override
    @NoValidPrivilege
    public ResponseDTO<NoticeDetailVO> read(@PathVariable("id") Long id) {
        return noticeService.read(id, RequestTokenUtil.getRequestUser());
    }
}
