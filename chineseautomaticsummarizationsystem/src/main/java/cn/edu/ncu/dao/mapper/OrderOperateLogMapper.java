package cn.edu.ncu.dao.mapper;

import cn.edu.ncu.common.core.dao.CommonMapper;
import cn.edu.ncu.dao.entity.OrderOperateLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 各种单据操作记录
 * Mapper 接口
 * </p>
 *
 * @author anders
 * @since 2018-01-09
 */
@Mapper
public interface OrderOperateLogMapper extends CommonMapper<OrderOperateLog> {

    List<OrderOperateLog> listOrderOperateLogsByOrderTypeAndOrderId(@Param("orderId") Long orderId, @Param("orderTypeList") List<Integer> orderTypeList);

    List<OrderOperateLog> listOrderOperateLogsByOrderTypeAndOrderIds(@Param("orderIds") List<Long> orderIds, @Param("orderTypeList") List<Integer> orderTypeList);

    void batchInsert(List<OrderOperateLog> list);

}
