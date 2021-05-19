package cn.edu.ncu.service;

import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.OrderOperateLogSaveDTO;
import cn.edu.ncu.pojo.vo.OrderOperateLogVO;
import java.util.List;

/**
 * <p>
 * 各种单据操作记录
 * 服务实现类
 * </p>
 *
 * @author anders
 * @since 2018-01-09
 */
public interface OrderOperateLogService {


     void batchSaveOrderOperateLog(List<OrderOperateLogSaveDTO> orderOperateLogSaveDTOList) ;

     ResponseDTO<List<OrderOperateLogVO>> listOrderOperateLogsByOrderTypeAndOrderId(Long orderId, List<Integer> orderTypeList) ;

     ResponseDTO<List<OrderOperateLogVO>> listOrderOperateLogsByOrderTypeAndOrderIds(List<Long> orderIds, List<Integer> orderTypeList);
}
