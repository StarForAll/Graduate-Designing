package cn.edu.ncu.common.core.pojo.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
/**
 * @Author: XiongZhiCong
 * @Description: 单个字段的排序选择
 * @Date: Created in 14:16 2021/4/8
 * @Modified By:
 */
@Slf4j
@Data
public class OrderItemDTO {
    private String column;
    private boolean asc = true;
}