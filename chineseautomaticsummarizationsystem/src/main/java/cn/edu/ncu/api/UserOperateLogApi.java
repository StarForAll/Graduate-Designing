package cn.edu.ncu.api;

import cn.edu.ncu.common.annotation.OperateLog;
import cn.edu.ncu.common.constant.SwaggerTagConst;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.UserOperateLogDTO;
import cn.edu.ncu.pojo.dto.UserOperateLogQueryDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: XiongZhiCong
 * @Description: 用户操作日志API
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Api(tags = {SwaggerTagConst.Admin.MANAGER_USER_OPERATE_LOG})
@OperateLog
public interface UserOperateLogApi {

    @ApiOperation(value = "分页查询",notes = "@author yandanyang")
    @PostMapping("/userOperateLog/page/query")
     ResponseDTO<PageResultDTO<UserOperateLogDTO>> queryByPage(@RequestBody UserOperateLogQueryDTO queryDTO) ;

    @ApiOperation(value="删除",notes = "@author yandanyang")
    @GetMapping("/userOperateLog/delete/{id}")
    ResponseDTO<String> delete(@PathVariable("id") Long id);


    @ApiOperation(value="详情",notes = "@author yandanyang")
    @GetMapping("/userOperateLog/detail/{id}")
     ResponseDTO<UserOperateLogDTO> detail(@PathVariable("id") Long id);
}
