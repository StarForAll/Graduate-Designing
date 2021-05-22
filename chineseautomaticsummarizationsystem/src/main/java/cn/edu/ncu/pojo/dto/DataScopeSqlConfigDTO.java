package cn.edu.ncu.pojo.dto;

import cn.edu.ncu.common.constant.DataScopeTypeEnum;
import cn.edu.ncu.common.constant.DataScopeWhereInTypeEnum;
import lombok.Data;

/**
 * @Author: XiongZhiCong
 * @Description: 数据范围设置DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class DataScopeSqlConfigDTO {

    /**
     * 数据范围类型
     * {@link DataScopeTypeEnum}
     */
    private DataScopeTypeEnum dataScopeType;

    /**
     * join sql 具体实现类
     */
    private Class joinSqlImplClazz;

    private String joinSql;

    private Integer whereIndex;

    /**
     * whereIn类型
     * {@link DataScopeWhereInTypeEnum}
     */
    private DataScopeWhereInTypeEnum dataScopeWhereInType;
}
