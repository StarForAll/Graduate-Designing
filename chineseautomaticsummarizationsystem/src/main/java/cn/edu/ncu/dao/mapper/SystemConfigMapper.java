package cn.edu.ncu.dao.mapper;

import cn.edu.ncu.common.core.dao.CommonMapper;
import cn.edu.ncu.dao.entity.SystemConfig;
import cn.edu.ncu.pojo.dto.SystemConfigQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统参数配置 t_system_config Dao层
 *
 * @author GHQ
 * @date 2017-12-23 14:25
 */
@Mapper
public interface SystemConfigMapper extends CommonMapper<SystemConfig> {
    void update(SystemConfig systemConfig);
    Integer queryCount( @Param("queryDTO") SystemConfigQueryDTO queryDTO);
    /**
     * 查询所有系统配置（分页）
     *
     * @return
     */
    List<SystemConfig> selectSystemSettingList( @Param("queryDTO") SystemConfigQueryDTO queryDTO);

    /**
     * 根据key查询获取数据
     *
     * @param key
     * @return
     */
    SystemConfig getByKey(@Param("key") String key);

    /**
     * 根据key查询获取数据  排除掉某個id的数据
     * @param key
     * @param excludeId
     * @return
     */
    SystemConfig getByKeyExcludeId(@Param("key") String key, @Param("excludeId") Long excludeId);
    /**
     * 查询所有系统配置
     *
     * @return
     */
//    List<SystemConfig> selectAll();

    /**
     * 根据分组查询所有系统配置
     * @param group
     * @return
     */
    List<SystemConfig> getListByGroup(String group);


    SystemConfig  selectByKeyAndGroup(@Param("configKey") String configKey, @Param("group") String group);
}
