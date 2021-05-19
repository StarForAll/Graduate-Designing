package cn.edu.ncu.api;

import cn.edu.ncu.common.annotation.OperateLog;
import cn.edu.ncu.common.constant.SwaggerTagConst;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.SystemConfigAddDTO;
import cn.edu.ncu.pojo.dto.SystemConfigQueryDTO;
import cn.edu.ncu.pojo.dto.SystemConfigUpdateDTO;
import cn.edu.ncu.pojo.vo.SystemConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * [  ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2019 1024lab.netInc. All rights reserved.
 * @date
 * @since JDK1.8
 */
@Api(tags = {SwaggerTagConst.Admin.MANAGER_SYSTEM_CONFIG})
@OperateLog
public interface SystemConfigApi {


    @ApiOperation(value = "分页查询所有系统配置", notes = "分页查询所有系统配置")
    @PostMapping("systemConfig/getListPage")
     ResponseDTO<PageResultDTO<SystemConfigVO>> getSystemConfigPage(@RequestBody @Valid SystemConfigQueryDTO queryDTO) ;

    @ApiOperation(value = "添加配置参数", notes = "添加配置参数")
    @PostMapping("systemConfig/add")
    ResponseDTO<String> addSystemConfig(@RequestBody @Valid SystemConfigAddDTO configAddDTO) ;

    @ApiOperation(value = "修改配置参数", notes = "修改配置参数")
    @PostMapping("systemConfig/update")
     ResponseDTO<String> updateSystemConfig(@RequestBody @Valid SystemConfigUpdateDTO updateDTO) ;

    @ApiOperation(value = "根据分组查询所有系统配置", notes = "根据分组查询所有系统配置")
    @GetMapping("systemConfig/getListByGroup")
     ResponseDTO<List<SystemConfigVO>> getListByGroup(String group) ;

    @ApiOperation(value = "通过key获取对应的信息", notes = "通过key获取对应的信息")
    @GetMapping("systemConfig/selectByKey")
     ResponseDTO<SystemConfigVO> selectByKey(String configKey);

}
