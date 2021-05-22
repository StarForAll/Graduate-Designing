package cn.edu.ncu.api;

import cn.edu.ncu.common.annotation.OperateLog;
import cn.edu.ncu.common.constant.SwaggerTagConst;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.EmployeeQueryDTO;
import cn.edu.ncu.pojo.dto.UserLoginLogDTO;
import cn.edu.ncu.pojo.dto.UserLoginLogQueryDTO;
import cn.edu.ncu.pojo.vo.EmployeeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: XiongZhiCong
 * @Description: 用户登录日志API
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Api(tags = {SwaggerTagConst.Admin.MANAGER_USER_LOGIN_LOG})
@OperateLog
public interface UserLoginLogApi {


    @ApiOperation(value = "分页查询用户登录日志", notes = "@author yandanyang")
    @PostMapping("/userLoginLog/page/query")
     ResponseDTO<PageResultDTO<UserLoginLogDTO>> queryByPage(@RequestBody UserLoginLogQueryDTO queryDTO);

    @ApiOperation(value = "删除用户登录日志", notes = "@author yandanyang")
    @GetMapping("/userLoginLog/delete/{id}")
    ResponseDTO<String> delete(@PathVariable("id") Long id) ;
    @ApiOperation(value = "查询员工在线状态", notes = "@author zzr")
    @PostMapping("/userOnLine/query")
     ResponseDTO<PageResultDTO<EmployeeVO>> queryUserOnLine(@RequestBody @Valid EmployeeQueryDTO queryDTO);

}
