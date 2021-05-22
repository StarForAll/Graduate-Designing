package cn.edu.ncu.dao.mapper;
import cn.edu.ncu.common.core.dao.CommonMapper;
import cn.edu.ncu.dao.entity.ReloadItemEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @Author: XiongZhiCong
 * @Description: t_reload_item 数据表dao
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Mapper
public interface ReloadItemMapper extends CommonMapper<ReloadItemEntity> {
    void update(ReloadItemEntity reloadItemEntity);
}
