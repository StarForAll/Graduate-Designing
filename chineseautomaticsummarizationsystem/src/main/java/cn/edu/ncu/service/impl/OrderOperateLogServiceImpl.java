package cn.edu.ncu.service.impl;

import cn.edu.ncu.common.util.basic.StringUtil;
import cn.edu.ncu.common.util.bean.BeanUtil;
import cn.edu.ncu.common.util.dao.IdGeneratorUtil;
import cn.edu.ncu.dao.entity.OrderOperateLog;
import cn.edu.ncu.dao.mapper.OrderOperateLogMapper;
import cn.edu.ncu.service.OrderOperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.OrderOperateLogSaveDTO;
import cn.edu.ncu.pojo.vo.OrderOperateLogVO;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 各种单据操作记录
 * 服务实现类
 * </p>
 *
 * @author anders
 * @since 2018-01-09
 */
@Service
public class OrderOperateLogServiceImpl implements OrderOperateLogService {
    @Autowired
    private IdGeneratorUtil idGeneratorUtil;
    @Resource
    private OrderOperateLogMapper orderOperateLogMapper;

    @Override
    public void batchSaveOrderOperateLog(List<OrderOperateLogSaveDTO> orderOperateLogSaveDTOList) {
        List<OrderOperateLog> entityList = new ArrayList<>();
        orderOperateLogSaveDTOList.forEach(e -> {
            OrderOperateLog orderOperateLogEntity = BeanUtil.copy(e, OrderOperateLog.class);
            if(orderOperateLogEntity.getId()==null){
                orderOperateLogEntity.setId(idGeneratorUtil.snowflakeId());
            }
            orderOperateLogEntity.setOperateType(e.getOperateType().getCode());
            if (StringUtil.isNotBlank(e.getOperateContent())) {
                orderOperateLogEntity.setOperateContent(e.getOperateContent());
            } else {
                orderOperateLogEntity.setOperateContent(e.getOperateType().getMsg());
            }
            orderOperateLogEntity.setOperateRemark(e.getOperateRemark());
            orderOperateLogEntity.setExtData(e.getExtData());
            Date date=new Date(System.currentTimeMillis());
            orderOperateLogEntity.setCreateTime(date);
            orderOperateLogEntity.setUpdateTime(date);
            orderOperateLogEntity.setOrderType(e.getOrderType().getType());
            entityList.add(orderOperateLogEntity);
        });
        //批量添加
        orderOperateLogMapper.batchInsert(entityList);
    }

    @Override
    public ResponseDTO<List<OrderOperateLogVO>> listOrderOperateLogsByOrderTypeAndOrderId(Long orderId, List<Integer> orderTypeList) {
        List<OrderOperateLog> orderOperateLogEntities = orderOperateLogMapper.listOrderOperateLogsByOrderTypeAndOrderId(orderId, orderTypeList);
        List<OrderOperateLogVO> dtoList = orderOperateLogEntities.stream().map(e -> BeanUtil.copy(e, OrderOperateLogVO.class)).collect(Collectors.toList());
        return ResponseDTO.succData(dtoList);
    }

    @Override
    public ResponseDTO<List<OrderOperateLogVO>> listOrderOperateLogsByOrderTypeAndOrderIds(List<Long> orderIds, List<Integer> orderTypeList) {
        List<OrderOperateLog> orderOperateLogEntities = orderOperateLogMapper.listOrderOperateLogsByOrderTypeAndOrderIds(orderIds, orderTypeList);
        List<OrderOperateLogVO> dtoList = orderOperateLogEntities.stream().map(e -> BeanUtil.copy(e, OrderOperateLogVO.class)).collect(Collectors.toList());
        return ResponseDTO.succData(dtoList);
    }
}
