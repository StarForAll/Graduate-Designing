package cn.edu.ncu.dao.mapper;

import cn.edu.ncu.common.core.dao.CommonMapper;
import cn.edu.ncu.dao.entity.Privilege;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @Author: XiongZhiCong
 * @Description: 岗位Mapper
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Mapper
public interface PrivilegeMapper extends CommonMapper<Privilege> {
    void update(Privilege privilege);
    /**
     * 根据权限key删除
     * @param keyList
     */
    void delByKeyList(@Param("keyList") List<String> keyList);
    /**
     * 根据权限parentkey删除
     * @param keyList
     */
    void delByParentKeyList(@Param("keyList") List<String> keyList);

    /**
     * 批量保存
     * @param privilegeList
     */
    void batchInsert(List<Privilege> privilegeList);

    /**
     * 批量更新
     * @param privilegeList
     */
    void batchUpdate(@Param("updateList") List<Privilege> privilegeList);

    /**
     * 根据父节点key查询
     * @param parentKey
     * @return
     */
    List<Privilege> selectByParentKey(@Param("parentKey") String parentKey);

    /**
     * 根据父节点key查询
     * @param keyList
     * @return
     */
    List<Privilege> selectByKeyList(@Param("keyList") List<String> keyList);
    List<Privilege> selectAllPrivilege();
    /**
     * 根据权限key查询
     * @param key
     * @return
     */
    Privilege selectByKey(@Param("key") String key);

    /**
     * 根据类型查询
     * @param type
     * @return
     */
    List<Privilege> selectByExcludeType(@Param("type") Integer type);

    /**
     * 根据类型查询
     * @param type
     * @return
     */
    List<Privilege> selectByType(@Param("type") Integer type);

    void deleteBatchIds(List<Long> deleteIdList);
}
