package cn.edu.ncu.dao.mapper;

import cn.edu.ncu.common.core.dao.CommonMapper;
import cn.edu.ncu.dao.entity.QuartzTaskLog;
import cn.edu.ncu.pojo.dto.QuartzLogQueryDTO;
import cn.edu.ncu.pojo.vo.QuartzTaskLogVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: 定时任务日志Mapper
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Mapper
public interface QuartzTaskLogMapper extends CommonMapper<QuartzTaskLog> {
    Integer queryListCount(@Param("queryDTO") QuartzLogQueryDTO queryDTO);

    /**
     * 查询列表
     * @param queryDTO
     * @return
     */
    List<QuartzTaskLogVO> queryList(@Param("queryDTO") QuartzLogQueryDTO queryDTO);
}
