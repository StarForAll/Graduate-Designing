package cn.edu.ncu.dao.mapper;

import cn.edu.ncu.common.core.dao.CommonMapper;
import cn.edu.ncu.dao.entity.ReloadResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: t_reload_result 数据表dao
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Mapper
public interface ReloadResultMapper extends CommonMapper<ReloadResult> {


    List<ReloadResult> query(@Param("tag") String tag);
}
