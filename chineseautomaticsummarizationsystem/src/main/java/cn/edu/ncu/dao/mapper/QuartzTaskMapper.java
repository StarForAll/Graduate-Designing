package cn.edu.ncu.dao.mapper;

import cn.edu.ncu.common.core.dao.CommonMapper;
import cn.edu.ncu.dao.entity.QuartzTaskEntity;
import cn.edu.ncu.pojo.dto.QuartzQueryDTO;
import cn.edu.ncu.pojo.vo.QuartzTaskVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: 定时任务Mapper
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Mapper
public interface QuartzTaskMapper extends CommonMapper<QuartzTaskEntity> {
    void update(QuartzTaskEntity quartzTaskEntity);
    /**
     * 更新任务状态
     * @param taskId
     * @param taskStatus
     */
    void updateStatus(@Param("taskId") Integer taskId, @Param("taskStatus") Integer taskStatus);

    Integer queryListCount(@Param("queryDTO") QuartzQueryDTO queryDTO);
    /**
     * 查询列表
     * @param queryDTO
     * @return
     */
    List<QuartzTaskVO> queryList(@Param("queryDTO") QuartzQueryDTO queryDTO);

}
