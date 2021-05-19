package cn.edu.ncu.api;

import cn.edu.ncu.common.annotation.NoValidPrivilege;
import cn.edu.ncu.common.annotation.OperateLog;
import cn.edu.ncu.common.constant.SwaggerTagConst;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.EmailDTO;
import cn.edu.ncu.pojo.dto.EmailQueryDTO;
import cn.edu.ncu.pojo.vo.EmailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * [  ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2019 1024lab.netInc. All rights reserved.
 * @date 2019-05-13 17:10:16
 * @since JDK1.8
 */
@OperateLog
@Api(tags = {SwaggerTagConst.Admin.MANAGER_EMAIL})
public interface EmailApi {


    @ApiOperation(value = "分页查询",notes = "@author yandanyang")
    @PostMapping("/email/page/query")
    @NoValidPrivilege
     ResponseDTO<PageResultDTO<EmailVO>> queryByPage(@RequestBody @Validated EmailQueryDTO queryDTO) ;

    @ApiOperation(value = "添加",notes = "@author yandanyang")
    @PostMapping("/email/add")
    @NoValidPrivilege
     ResponseDTO<Long> add(@RequestBody @Valid EmailDTO addTO);

    @ApiOperation(value="修改",notes = "@author yandanyang")
    @PostMapping("/email/update")
    @NoValidPrivilege
     ResponseDTO<Long> update(@RequestBody @Valid EmailDTO updateDTO);


    @ApiOperation(value="删除",notes = "@author yandanyang")
    @GetMapping("/email/delete/{id}")
    @NoValidPrivilege
     ResponseDTO<String> delete(@PathVariable("id") Long id);


    @ApiOperation(value="详情",notes = "@author yandanyang")
    @GetMapping("/email/detail/{id}")
    @NoValidPrivilege
     ResponseDTO<EmailVO> detail(@PathVariable("id") Long id);


    @ApiOperation(value="发送",notes = "@author yandanyang")
    @GetMapping("/email/send/{id}")
    @NoValidPrivilege
    ResponseDTO<String> send(@PathVariable("id") Long id);
}
