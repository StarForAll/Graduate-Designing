package cn.edu.ncu.controller;

import cn.edu.ncu.api.OrderOperateLogApi;
import cn.edu.ncu.common.util.basic.StringUtil;
import cn.edu.ncu.service.OrderOperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.vo.OrderOperateLogVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 单据操作记录Controller
 *
 * @author lidoudou
 * @date: 2018/1/31 16:56
 */

@RestController
public class OrderOperateLogController implements OrderOperateLogApi {

    @Autowired
    private OrderOperateLogService orderOperateLogService;
    @Override
    public ResponseDTO<List<OrderOperateLogVO>> list(@PathVariable Long orderId, String orderType) {
        List<Integer> orderTypeList = new ArrayList<>(StringUtil.splitConverToIntSet(orderType, ","));
        return orderOperateLogService.listOrderOperateLogsByOrderTypeAndOrderId(orderId, orderTypeList);
    }
}
