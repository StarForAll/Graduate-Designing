package cn.edu.ncu.service;

import cn.edu.ncu.pojo.dto.DataScopeSqlConfigDTO;

/**
 * @Author: XiongZhiCong
 * @Description: 数据范围设置
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public interface DataScopeSqlConfigService {



    /**
     * 根据调用的方法获取，此方法的配置信息
     *
     * @param method
     * @return
     */
     DataScopeSqlConfigDTO getSqlConfig(String method);

    /**
     * 组装需要拼接的sql
     *
     * @param sqlConfigDTO
     * @return
     */
     String getJoinSql(DataScopeSqlConfigDTO sqlConfigDTO) ;
}
