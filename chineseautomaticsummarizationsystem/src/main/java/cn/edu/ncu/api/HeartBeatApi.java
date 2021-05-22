package cn.edu.ncu.api;

import cn.edu.ncu.common.annotation.OperateLog;
import cn.edu.ncu.common.constant.SwaggerTagConst;
import cn.edu.ncu.common.core.pojo.dto.PageParamDTO;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.vo.HeartBeatRecordVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @Author: XiongZhiCong
 * @Description: 心跳服务API
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Api(tags = {SwaggerTagConst.Admin.MANAGER_HEART_BEAT})
@OperateLog
public interface HeartBeatApi {

    @PostMapping("/heartBeat/query")
    @ApiOperation("查询心跳记录 @author zhuoda")
    ResponseDTO<PageResultDTO<HeartBeatRecordVO>> query(@RequestBody @Valid PageParamDTO pageParamDTO);

}
