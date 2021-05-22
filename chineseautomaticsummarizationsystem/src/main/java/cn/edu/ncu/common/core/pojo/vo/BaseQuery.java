package cn.edu.ncu.common.core.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: XiongZhiCong
 * @Description: BaseQuery
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseQuery extends AbstractQuery{
    /**
     *  当前页索引
     */
    private Integer pageNum = 1;
    /**
     * 每页的数据
     */
    private Integer pageSize = 10;
}
