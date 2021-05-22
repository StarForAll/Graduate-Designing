package cn.edu.ncu.service.impl;

import cn.edu.ncu.common.constant.PositionResponseCodeConst;
import cn.edu.ncu.common.constant.ResponseCodeConst;
import cn.edu.ncu.common.util.bean.BeanUtil;
import cn.edu.ncu.common.util.dao.IdGeneratorUtil;
import cn.edu.ncu.dao.entity.Position;
import cn.edu.ncu.dao.entity.PositionRelation;
import cn.edu.ncu.dao.mapper.PositionMapper;
import cn.edu.ncu.service.PositionService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.*;
import cn.edu.ncu.pojo.vo.PositionResultVO;
/**
 * @Author: XiongZhiCong
 * @Description: 岗位业务
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Service
public class PositionServiceImpl implements PositionService {

    @Resource
    private PositionMapper positionMapper;
    @Autowired
    private IdGeneratorUtil idGeneratorUtil;
    /**
     * 查询岗位
     *
     * @param queryDTO
     * @return
     */
    @Override
    public ResponseDTO<PageResultDTO<PositionResultVO>> queryPositionByPage(PositionQueryDTO queryDTO) {
        PageResultDTO<PositionResultVO> pageResultDTO = new PageResultDTO<>();
        int total=0;
        Boolean searchCount = queryDTO.getSearchCount();
        if(searchCount ==null||searchCount){
            total=positionMapper.selectCountByPage(queryDTO);
        }
        long pages=total/queryDTO.getPageSize()+1;
        pageResultDTO.setPageNum(Long.valueOf(queryDTO.getPageNum()));
        pageResultDTO.setPages(pages);
        pageResultDTO.setTotal((long) total);
        pageResultDTO.setPageSize(Long.valueOf(queryDTO.getPageSize()));

        List<Position> entityList = positionMapper.selectByPage( queryDTO);
        pageResultDTO.setList(entityList.stream().map
                (e -> BeanUtil.copy(e, PositionResultVO.class)).collect(Collectors.toList()));
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * 新增岗位
     *
     * @param addDTO
     * @return
     */
    @Override
    public ResponseDTO<String> addPosition(PositionAddDTO addDTO) {
        Position positionEntity = BeanUtil.copy(addDTO, Position.class);
        if(positionEntity.getId()==null){
            positionEntity.setId(idGeneratorUtil.snowflakeId());
        }
        Date date=new Date(System.currentTimeMillis());
        if(positionEntity.getUpdateTime()==null){
            positionEntity.setUpdateTime(date);
        }
        if(positionEntity.getCreateTime()==null){
            positionEntity.setCreateTime(date);
        }
        positionMapper.insert(positionEntity);
        return ResponseDTO.succ();
    }

    /**
     * 修改岗位
     *
     * @param updateDTO
     * @return
     */
    @Override
    public ResponseDTO<String> updatePosition(PositionUpdateDTO updateDTO) {
        Position positionEntity = BeanUtil.copy(updateDTO, Position.class);
        positionMapper.update(positionEntity);
        return ResponseDTO.succ();
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @Override
    public ResponseDTO<PositionResultVO> queryPositionById(Long id) {
        return ResponseDTO.succData(BeanUtil.copy(positionMapper.selectByPrimaryKey(id), PositionResultVO.class));
    }

    /**
     * 删除岗位
     */
    @Override
    public ResponseDTO<String> removePosition(Long id) {
        //查询是否还有人关联该岗位
        PositionRelationQueryDTO positionRelationQueryDTO = new PositionRelationQueryDTO();
        positionRelationQueryDTO.setPositionId(id);
        List<PositionRelationResultDTO> dtoList = positionMapper.selectRelation(positionRelationQueryDTO);
        if (CollectionUtils.isNotEmpty(dtoList)) {
            return ResponseDTO.wrap(PositionResponseCodeConst.REMOVE_DEFINE);
        }
        positionMapper.deleteByPrimaryKey(id);
        return ResponseDTO.succ();
    }

    /**
     * 添加岗位关联关系
     *
     * @param positionRelAddDTO
     * @return
     */
    @Override
    public ResponseDTO<String> addPositionRelation(PositionRelationAddDTO positionRelAddDTO) {
        List<Long> positionIdList = positionRelAddDTO.getPositionIdList();
        if(positionIdList.isEmpty()){
            return ResponseDTO.wrap(ResponseCodeConst.POSITION_ID_NOT_FOUND_ERROR);
        }
        List<PositionRelation> positionRelations=new ArrayList<>();
        PositionRelation positionRelation;
        for(Long positionId:positionIdList){
            positionRelation=new PositionRelation();
            positionRelation.setId(idGeneratorUtil.snowflakeId());
            positionRelation.setEmployeeId(positionRelAddDTO.getEmployeeId());
            positionRelation.setPositionId(positionId);
            positionRelation.setCreateTime(new Date(System.currentTimeMillis()));
            positionRelation.setUpdateTime(new Date(System.currentTimeMillis()));
            positionRelations.add(positionRelation);
        }
        positionMapper.insertBatchRelation(positionRelations);
        return ResponseDTO.succ();
    }

    /**
     * 删除指定用户的岗位关联关系
     *
     * @param employeeId
     * @return
     */
    @Override
    public ResponseDTO<String> removePositionRelation(Long employeeId) {
        positionMapper.deleteRelationByEmployeeId(employeeId);
        return ResponseDTO.succ();
    }

    /**
     * 根据员工ID查询 所关联的岗位信息
     *
     * @param employeeId
     * @return
     */
    @Override
    public List<PositionRelationResultDTO> queryPositionByEmployeeId(Long employeeId) {
        PositionRelationQueryDTO positionRelationQueryDTO = new PositionRelationQueryDTO();
        positionRelationQueryDTO.setEmployeeId(employeeId);
        List<PositionRelationResultDTO> positionRelationList = positionMapper.selectRelation(positionRelationQueryDTO);
        return positionRelationList;
    }

}
