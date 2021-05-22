package cn.edu.ncu.controller;

import cn.edu.ncu.api.HeartBeatApi;
import cn.edu.ncu.service.HeartBeatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import cn.edu.ncu.common.core.pojo.dto.PageParamDTO;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.vo.HeartBeatRecordVO;
import javax.validation.Valid;

/**
 * @Author: XiongZhiCong
 * @Description: 心跳服务
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@RestController
public class HeartBeatController implements HeartBeatApi {

    @Autowired
    private HeartBeatService heartBeatService;

    @Override
    public ResponseDTO<PageResultDTO<HeartBeatRecordVO>> query(@RequestBody @Valid PageParamDTO pageParamDTO){
        return heartBeatService.pageQuery(pageParamDTO);
    }

}
