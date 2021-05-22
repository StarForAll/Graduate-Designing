package cn.edu.ncu.dao.mapper;

import cn.edu.ncu.common.core.dao.CommonMapper;
import cn.edu.ncu.dao.entity.DataScopeRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: 数据范围Mapper
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Mapper
public interface DataScopeRoleMapper extends CommonMapper<DataScopeRole> {

    /**
     * 获取某个角色的设置信息
     * @param roleId
     * @return
     */
    List<DataScopeRole> listByRoleId(@Param("roleId") Long roleId);

    /**
     * 获取某批角色的所有数据范围配置信息
     * @param roleIdList
     * @return
     */
    List<DataScopeRole> listByRoleIdList(@Param("roleIdList") List<Long> roleIdList);

    /**
     * 删除某个角色的设置信息
     * @param roleId
     * @return
     */
    void deleteByRoleId(@Param("roleId") Long roleId);


    /**
     * 批量添加设置信息
     * @param list
     */
    void batchInsert(@Param("list") List<DataScopeRole> list);
}
