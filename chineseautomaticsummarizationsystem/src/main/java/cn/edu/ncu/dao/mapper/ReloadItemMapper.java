package cn.edu.ncu.dao.mapper;
import cn.edu.ncu.common.core.dao.CommonMapper;
import cn.edu.ncu.dao.entity.ReloadItemEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * t_reload_item 数据表dao
 *
 * @author listen
 * @date 2018/02/10 09:23
 */
@Mapper
public interface ReloadItemMapper extends CommonMapper<ReloadItemEntity> {
    void update(ReloadItemEntity reloadItemEntity);
}
