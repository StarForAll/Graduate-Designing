package cn.edu.ncu.controller;

import cn.edu.ncu.api.UserLoginLogApi;
import cn.edu.ncu.service.UserLoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.EmployeeQueryDTO;
import cn.edu.ncu.pojo.dto.UserLoginLogDTO;
import cn.edu.ncu.pojo.dto.UserLoginLogQueryDTO;
import cn.edu.ncu.pojo.vo.EmployeeVO;
import javax.validation.Valid;

/**
 * @Author: XiongZhiCong
 * @Description: 用户登录日志
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@RestController
public class UserLoginLogController implements UserLoginLogApi {

    @Autowired
    private UserLoginLogService userLoginLogService;

    @Override
    public ResponseDTO<PageResultDTO<UserLoginLogDTO>> queryByPage(@RequestBody UserLoginLogQueryDTO queryDTO) {
        return userLoginLogService.queryByPage(queryDTO);
    }
    @Override
    public ResponseDTO<String> delete(@PathVariable("id") Long id) {
        return userLoginLogService.delete(id);
    }
    @Override
    public ResponseDTO<PageResultDTO<EmployeeVO>> queryUserOnLine(@RequestBody @Valid EmployeeQueryDTO queryDTO) {
        return userLoginLogService.queryUserOnLine(queryDTO);
    }

}
