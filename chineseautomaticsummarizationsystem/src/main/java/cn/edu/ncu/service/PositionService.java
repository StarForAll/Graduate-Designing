package cn.edu.ncu.service;

import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.*;
import cn.edu.ncu.pojo.vo.PositionResultVO;

import java.util.List;
/**
 * @Author: XiongZhiCong
 * @Description: 岗位
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public interface PositionService {


    /**
     * 查询岗位
     *
     * @param queryDTO
     * @return
     */
     ResponseDTO<PageResultDTO<PositionResultVO>> queryPositionByPage(PositionQueryDTO queryDTO) ;

    /**
     * 新增岗位
     *
     * @param addDTO
     * @return
     */
     ResponseDTO<String> addPosition(PositionAddDTO addDTO) ;

    /**
     * 修改岗位
     *
     * @param updateDTO
     * @return
     */
     ResponseDTO<String> updatePosition(PositionUpdateDTO updateDTO) ;

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
     ResponseDTO<PositionResultVO> queryPositionById(Long id) ;

    /**
     * 删除岗位
     */
     ResponseDTO<String> removePosition(Long id) ;

    /**
     * 添加岗位关联关系
     *
     * @param positionRelAddDTO
     * @return
     */
     ResponseDTO<String> addPositionRelation(PositionRelationAddDTO positionRelAddDTO) ;

    /**
     * 删除指定用户的岗位关联关系
     *
     * @param employeeId
     * @return
     */
     ResponseDTO<String> removePositionRelation(Long employeeId) ;

    /**
     * 根据员工ID查询 所关联的岗位信息
     *
     * @param employeeId
     * @return
     */
     List<PositionRelationResultDTO> queryPositionByEmployeeId(Long employeeId) ;

}
