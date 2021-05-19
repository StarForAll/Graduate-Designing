package cn.edu.ncu.api;

import cn.edu.ncu.common.annotation.NoValidPrivilege;
import cn.edu.ncu.common.constant.SwaggerTagConst;
import cn.edu.ncu.common.core.pojo.dto.PageParamDTO;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.*;
import cn.edu.ncu.pojo.vo.NoticeDetailVO;
import cn.edu.ncu.pojo.vo.NoticeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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

@Api(tags = {SwaggerTagConst.Admin.MANAGER_NOTICE})
public interface NoticeApi {
    @ApiOperation(value = "分页查询全部消息", notes = "@author yandanyang")
    @PostMapping("/notice/page/query")
    @NoValidPrivilege
    ResponseDTO<PageResultDTO<NoticeVO>> queryByPage(@RequestBody NoticeQueryDTO queryDTO) ;
    @ApiOperation(value = "获取已收取的所有消息", notes = "@author yandanyang")
    @PostMapping("/notice/receive/page/query")
    @NoValidPrivilege
     ResponseDTO<PageResultDTO<NoticeReceiveDTO>> queryReceiveByPage(@RequestBody NoticeReceiveQueryDTO queryDTO) ;

    @ApiOperation(value = "分页查询未读消息", notes = "@author yandanyang")
    @PostMapping("/notice/unread/page/query")
    @NoValidPrivilege
     ResponseDTO<PageResultDTO<NoticeVO>> queryUnreadByPage(@RequestBody PageParamDTO queryDTO) ;

    @ApiOperation(value = "添加", notes = "@author yandanyang")
    @PostMapping("/notice/add")
    @NoValidPrivilege
     ResponseDTO<String> add(@RequestBody @Valid NoticeAddDTO addTO) ;

    @ApiOperation(value = "修改", notes = "@author yandanyang")
    @PostMapping("/notice/update")
    @NoValidPrivilege
     ResponseDTO<String> update(@RequestBody @Valid NoticeUpdateDTO updateDTO) ;

    @ApiOperation(value = "删除", notes = "@author yandanyang")
    @GetMapping("/notice/delete/{id}")
    @NoValidPrivilege
     ResponseDTO<String> delete(@PathVariable("id") Long id) ;

    @ApiOperation(value = "详情", notes = "@author yandanyang")
    @GetMapping("/notice/detail/{id}")
    @NoValidPrivilege
     ResponseDTO<NoticeDetailVO> detail(@PathVariable("id") Long id) ;

    @ApiOperation(value = "发送", notes = "@author yandanyang")
    @GetMapping("/notice/send/{id}")
    @NoValidPrivilege
     ResponseDTO<NoticeDetailVO> send(@PathVariable("id") Long id) ;

    @ApiOperation(value = "读取消息", notes = "@author yandanyang")
    @GetMapping("/notice/read/{id}")
    @NoValidPrivilege
     ResponseDTO<NoticeDetailVO> read(@PathVariable("id") Long id) ;
}
