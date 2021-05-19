package cn.edu.ncu.dao.mapper;

import cn.edu.ncu.common.core.dao.CommonMapper;
import cn.edu.ncu.dao.entity.ReloadResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * t_reload_result 数据表dao
 *
 * @author listen
 * @date 2018/02/10 09:23
 */
@Mapper
public interface ReloadResultMapper extends CommonMapper<ReloadResult> {


    List<ReloadResult> query(@Param("tag") String tag);
}
