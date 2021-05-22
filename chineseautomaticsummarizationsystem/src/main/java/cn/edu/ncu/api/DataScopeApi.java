package cn.edu.ncu.api;

import cn.edu.ncu.common.annotation.NoValidPrivilege;
import cn.edu.ncu.common.annotation.OperateLog;
import cn.edu.ncu.common.constant.SwaggerTagConst;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.DataScopeBatchSetRoleDTO;
import cn.edu.ncu.pojo.vo.DataScopeAndViewTypeVO;
import cn.edu.ncu.pojo.vo.DataScopeSelectVO;
import cn.edu.ncu.service.DataScopeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: 数据范围API
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Api(tags = {SwaggerTagConst.Admin.MANAGER_DATA_SCOPE})
@OperateLog
public interface DataScopeApi {

    @ApiOperation(value = "获取当前系统所配置的所有数据范围")
    @GetMapping("/dataScope/list")
    @NoValidPrivilege
     ResponseDTO<List<DataScopeAndViewTypeVO>> dataScopeList();

    @ApiOperation(value = "获取某角色所设置的数据范围")
    @GetMapping("/dataScope/listByRole/{roleId}")
    @NoValidPrivilege
     ResponseDTO<List<DataScopeSelectVO>> dataScopeListByRole(@PathVariable Long roleId) ;

    @ApiOperation(value = "批量设置某角色数据范围")
    @PostMapping("/dataScope/batchSet")
    @NoValidPrivilege
     ResponseDTO<String> dataScopeBatchSet(@RequestBody @Valid DataScopeBatchSetRoleDTO batchSetRoleDTO) ;


}
