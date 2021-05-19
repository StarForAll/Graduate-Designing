package cn.edu.ncu.service;

import cn.edu.ncu.common.constant.*;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.SystemConfigAddDTO;
import cn.edu.ncu.pojo.dto.SystemConfigDTO;
import cn.edu.ncu.pojo.dto.SystemConfigQueryDTO;
import cn.edu.ncu.pojo.dto.SystemConfigUpdateDTO;
import cn.edu.ncu.pojo.vo.SystemConfigVO;
import java.util.List;

/**
 * 系统配置业务类
 *
 * @author GHQ
 * @date 2017-12-23 15:09
 */

public interface SystemConfigService {



    /**
     * 分页获取系统配置
     *
     * @param queryDTO
     * @return
     */
     ResponseDTO<PageResultDTO<SystemConfigVO>> getSystemConfigPage(SystemConfigQueryDTO queryDTO) ;

    /**
     * 根据参数key获得一条数据（数据库）
     *
     * @param configKey
     * @return
     */
     ResponseDTO<SystemConfigVO> selectByKey(String configKey);
    /**
     * 根据参数key获得一条数据 并转换为 对象
     *
     * @param configKey
     * @param clazz
     * @param <T>
     * @return
     */
     <T> T selectByKey2Obj(String configKey, Class<T> clazz);

     SystemConfigDTO getCacheByKey(SystemConfigEnum.Key key) ;
    /**
     * 添加系统配置
     *
     * @param configAddDTO
     * @return
     */
     ResponseDTO<String> addSystemConfig(SystemConfigAddDTO configAddDTO) ;

    /**
     * 更新系统配置
     *
     * @param updateDTO
     * @return
     */
     ResponseDTO<String> updateSystemConfig(SystemConfigUpdateDTO updateDTO) ;



    /**
     * 根据分组名称 获取获取系统设置
     *
     * @param group
     * @return
     */
     ResponseDTO<List<SystemConfigVO>> getListByGroup(String group) ;
    /**
     * 根据分组名称 获取获取系统设置
     *
     * @param group
     * @return
     */
     List<SystemConfigDTO> getListByGroup(SystemConfigEnum.Group group) ;

}
