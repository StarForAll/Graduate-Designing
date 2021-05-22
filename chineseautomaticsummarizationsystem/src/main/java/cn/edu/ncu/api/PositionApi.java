package cn.edu.ncu.api;

import cn.edu.ncu.common.annotation.OperateLog;
import cn.edu.ncu.common.constant.SwaggerTagConst;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.PositionAddDTO;
import cn.edu.ncu.pojo.dto.PositionQueryDTO;
import cn.edu.ncu.pojo.dto.PositionUpdateDTO;
import cn.edu.ncu.pojo.vo.PositionResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: XiongZhiCong
 * @Description: 岗位API
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Api(tags = {SwaggerTagConst.Admin.MANAGER_JOB})
@OperateLog
public interface PositionApi {

    @ApiOperation(value = "分页查询所有岗位", notes = "分页查询所有岗位 @author zzr")
    @PostMapping("/position/getListPage")
     ResponseDTO<PageResultDTO<PositionResultVO>> getJobPage(@RequestBody @Valid PositionQueryDTO queryDTO);

    @ApiOperation(value = "添加岗位", notes = "添加岗位 @author zzr")
    @PostMapping("/position/add")
    ResponseDTO<String> addJob(@RequestBody @Valid PositionAddDTO addDTO) ;

    @ApiOperation(value = "更新岗位", notes = "更新岗位 @author zzr")
    @PostMapping("/position/update")
     ResponseDTO<String> updateJob(@RequestBody @Valid PositionUpdateDTO updateDTO) ;

    @ApiOperation(value = "根据ID查询岗位", notes = "根据ID查询岗位 @author zzr")
    @GetMapping("/position/queryById/{id}")
     ResponseDTO<PositionResultVO> queryJobById(@PathVariable Long id) ;

    @ApiOperation(value = "根据ID删除岗位", notes = "根据ID删除岗位 @author zzr")
    @GetMapping("/position/remove/{id}")
     ResponseDTO<String> removeJob(@PathVariable Long id) ;

}
