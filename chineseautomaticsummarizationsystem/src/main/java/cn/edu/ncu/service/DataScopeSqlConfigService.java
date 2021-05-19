package cn.edu.ncu.service;

import cn.edu.ncu.pojo.dto.DataScopeSqlConfigDTO;

/**
 * [  ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2018 1024lab.netInc. All rights reserved.
 * @date 2019/4/29 0029 上午 10:12
 * @since JDK1.8
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
