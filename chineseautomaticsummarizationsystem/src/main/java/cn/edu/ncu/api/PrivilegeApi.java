package cn.edu.ncu.api;

import cn.edu.ncu.common.annotation.OperateLog;
import cn.edu.ncu.common.constant.SwaggerTagConst;
import cn.edu.ncu.common.core.pojo.ValidateList;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.PrivilegeFunctionDTO;
import cn.edu.ncu.pojo.dto.PrivilegeMenuDTO;
import cn.edu.ncu.pojo.vo.PrivilegeFunctionVO;
import cn.edu.ncu.pojo.vo.PrivilegeMenuVO;
import cn.edu.ncu.pojo.vo.PrivilegeRequestUrlVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: 权限API
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@OperateLog
@Api(tags = {SwaggerTagConst.Admin.MANAGER_PRIVILEGE})
public interface PrivilegeApi {

    @GetMapping("/privilege/getAllUrl")
    @ApiOperation(value = "获取所有请求路径", notes = "获取所有请求路径")
     ResponseDTO<List<PrivilegeRequestUrlVO>> getAllUrl() ;

    @ApiOperation(value = "菜单批量保存")
    @PostMapping("/privilege/menu/batchSaveMenu")
     ResponseDTO<String> menuBatchSave(@Valid @RequestBody ValidateList<PrivilegeMenuDTO> menuList) ;


    @ApiOperation(value = "查询所有菜单项")
    @PostMapping("/privilege/menu/queryAll")
     ResponseDTO<List<PrivilegeMenuVO>> queryAll() ;


    @ApiOperation(value = "保存更新功能点")
    @PostMapping("/privilege/function/saveOrUpdate")
     ResponseDTO<String> functionSaveOrUpdate(@Valid @RequestBody PrivilegeFunctionDTO privilegeFunctionDTO) ;

    @ApiOperation(value = "批量保存功能点")
    @PostMapping("/privilege/function/batchSave")
     ResponseDTO<String>  batchSaveFunctionList(@Valid @RequestBody ValidateList<PrivilegeFunctionDTO> functionList) ;


    @ApiOperation(value = "查询菜单功能点", notes = "更新")
    @PostMapping("/privilege/function/query/{menuKey}")
    ResponseDTO<List<PrivilegeFunctionVO>> functionQuery(@PathVariable String menuKey) ;


}
