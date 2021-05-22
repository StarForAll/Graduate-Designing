package cn.edu.ncu.controller;

import cn.edu.ncu.api.UserOperateLogApi;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.UserOperateLogDTO;
import cn.edu.ncu.pojo.dto.UserOperateLogQueryDTO;
import cn.edu.ncu.service.UserOperateLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: XiongZhiCong
 * @Description: 用户操作日志
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@RestController
public class UserOperateLogController implements UserOperateLogApi {

    @Autowired
    private UserOperateLogService userOperateLogService;

    @Override
    public ResponseDTO<PageResultDTO<UserOperateLogDTO>> queryByPage(@RequestBody UserOperateLogQueryDTO queryDTO) {
        return userOperateLogService.queryByPage(queryDTO);
    }
    @Override
    public ResponseDTO<String> delete(@PathVariable("id") Long id){
        return userOperateLogService.delete(id);
    }

    @Override
    public ResponseDTO<UserOperateLogDTO> detail(@PathVariable("id") Long id){
        return userOperateLogService.detail(id);
    }
}
