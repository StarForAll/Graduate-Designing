package cn.edu.ncu.dao.mapper;

import cn.edu.ncu.common.core.dao.CommonMapper;
import cn.edu.ncu.dao.entity.Position;
import cn.edu.ncu.dao.entity.PositionRelation;
import cn.edu.ncu.pojo.dto.PositionQueryDTO;
import cn.edu.ncu.pojo.dto.PositionRelationAddDTO;
import cn.edu.ncu.pojo.dto.PositionRelationQueryDTO;
import cn.edu.ncu.pojo.dto.PositionRelationResultDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zzr
 */
@Mapper
public interface PositionMapper extends CommonMapper<Position> {
    void update(Position position);
    Integer selectCountByPage( @Param("queryDTO") PositionQueryDTO queryDTO);
    /**
     * 查询岗位列表
     *
     * @param queryDTO
     * @return
     */
    List<Position> selectByPage( @Param("queryDTO") PositionQueryDTO queryDTO);

    /**
     * 查询岗位与人员关系
     *
     * @param positionRelationQueryDTO
     * @return
     */
    List<PositionRelationResultDTO> selectRelation(PositionRelationQueryDTO positionRelationQueryDTO);

    /**
     * 批量查询员工岗位信息
     * @param employeeIdList
     * @return
     */
    List<PositionRelationResultDTO> selectEmployeesRelation(@Param("employeeIdList") List<Long> employeeIdList);

    /**
     * 批量添加岗位 人员 关联关系
     *
     * @param positionRelations
     * @return
     */
    Integer insertBatchRelation(@Param("positionRelations")List<PositionRelation> positionRelations);

    /**
     * 删除指定人员的 岗位关联关系
     *
     * @param employeeId
     * @return
     */
    Integer deleteRelationByEmployeeId(@Param("employeeId") Long employeeId);

}
