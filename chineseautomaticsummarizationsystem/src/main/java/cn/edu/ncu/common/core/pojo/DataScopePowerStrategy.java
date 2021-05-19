package cn.edu.ncu.common.core.pojo;

import cn.edu.ncu.common.constant.DataScopeViewTypeEnum;
import cn.edu.ncu.pojo.dto.DataScopeSqlConfigDTO;

public abstract class DataScopePowerStrategy {

    /**
     * 获取joinsql 字符串
     * @param viewTypeEnum 查看的类型
     * @param sqlConfigDTO
     * @return
     */
    public abstract String getCondition(DataScopeViewTypeEnum viewTypeEnum, DataScopeSqlConfigDTO sqlConfigDTO);
}