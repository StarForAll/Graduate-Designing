package cn.edu.ncu.api;

import cn.edu.ncu.common.annotation.NoValidPrivilege;
import cn.edu.ncu.common.annotation.OperateLog;
import cn.edu.ncu.common.constant.SwaggerTagConst;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.ReloadItemUpdateDTO;
import cn.edu.ncu.pojo.vo.ReloadItemVO;
import cn.edu.ncu.pojo.vo.ReloadResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Smart Reload 路由
 *
 * @author listen
 * @date 2018/02/10 09:18
 */
@Api(tags = {SwaggerTagConst.Admin.MANAGER_RELOAD})
@OperateLog
public interface ReloadApi {


    @ApiOperation(value = "获取全部Smart-reload项", notes = "获取全部Smart-reload项")
    @GetMapping("/smartReload/all")
    @NoValidPrivilege
    ResponseDTO<List<ReloadItemVO>> listAllReloadItem() ;

    @ApiOperation(value = "获取reload result", notes = "获取reload result")
    @GetMapping("/smartReload/result/{tag}")
    @NoValidPrivilege
     ResponseDTO<List<ReloadResultVO>> queryReloadResult(@PathVariable("tag") String tag);

    @ApiOperation("通过tag更新标识")
    @PostMapping("/smartReload/update")
    @NoValidPrivilege
     ResponseDTO<String> updateByTag(@RequestBody @Valid ReloadItemUpdateDTO updateDTO) ;
}
