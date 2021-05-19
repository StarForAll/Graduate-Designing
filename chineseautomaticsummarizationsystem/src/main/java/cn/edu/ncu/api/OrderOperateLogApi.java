package cn.edu.ncu.api;

import cn.edu.ncu.common.annotation.OperateLog;
import cn.edu.ncu.common.constant.OrderOperateLogOrderTypeEnum;
import cn.edu.ncu.common.constant.SwaggerTagConst;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.vo.OrderOperateLogVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 单据操作记录Controller
 *
 * @author lidoudou
 * @date: 2018/1/31 16:56
 */

@Api(tags = {SwaggerTagConst.Admin.MANAGER_ORDER_OPERATE_LOG})
@OperateLog
public interface OrderOperateLogApi {

    @ApiOperation(value = "查询单据操作日志", notes = "查询单据操作日志")
    @GetMapping("/orderOperateLog/list/{orderId}")
    @ApiImplicitParams({@ApiImplicitParam(name = "orderId", value = "业务id", paramType = "path"), @ApiImplicitParam(name = "orderType", value = "业务类型" + OrderOperateLogOrderTypeEnum.INFO, paramType
        = "query")})
    ResponseDTO<List<OrderOperateLogVO>> list(@PathVariable Long orderId, String orderType) ;
}
