package cn.edu.ncu.service.impl;

import cn.edu.ncu.common.constant.DataScopeTypeEnum;
import cn.edu.ncu.common.constant.DataScopeViewTypeEnum;
import cn.edu.ncu.common.constant.ResponseCodeConst;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.common.util.bean.BeanUtil;
import cn.edu.ncu.common.util.dao.IdGeneratorUtil;
import cn.edu.ncu.dao.entity.DataScopeRole;
import cn.edu.ncu.pojo.dto.DataScopeBatchSetDTO;
import cn.edu.ncu.pojo.dto.DataScopeBatchSetRoleDTO;
import cn.edu.ncu.pojo.dto.DataScopeDTO;
import cn.edu.ncu.pojo.vo.DataScopeAndViewTypeVO;
import cn.edu.ncu.pojo.vo.DataScopeSelectVO;
import cn.edu.ncu.pojo.vo.DataScopeViewTypeVO;
import cn.edu.ncu.dao.mapper.DataScopeRoleMapper;
import cn.edu.ncu.service.DataScopeService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;

/**
 * [  ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2018 1024lab.netInc. All rights reserved.
 * @date 2019/4/27 0027 下午 14:52
 * @since JDK1.8
 */
@Service
public class DataScopeServiceImpl implements DataScopeService {

    @Resource
    private DataScopeRoleMapper dataScopeRoleMapper;
    @Autowired
    private IdGeneratorUtil idGeneratorUtil;
    /**
     * 获取所有可以进行数据范围配置的信息
     *
     * @return
     */
    @Override
    public ResponseDTO<List<DataScopeAndViewTypeVO>> dataScopeList() {
        List<DataScopeDTO> dataScopeList = this.getDataScopeType();
        List<DataScopeAndViewTypeVO> dataScopeAndTypeList = BeanUtil.copyList(dataScopeList, DataScopeAndViewTypeVO.class);
        List<DataScopeViewTypeVO> typeList = this.getViewType();
        dataScopeAndTypeList.forEach(e -> {
            e.setViewTypeList(typeList);
        });
        return ResponseDTO.succData(dataScopeAndTypeList);
    }

    /**
     * 获取当前系统存在的数据可见范围
     *
     * @return
     */
    @Override
    public List<DataScopeViewTypeVO> getViewType() {
        List<DataScopeViewTypeVO> viewTypeList = Lists.newArrayList();
        DataScopeViewTypeEnum[] enums = DataScopeViewTypeEnum.class.getEnumConstants();
        DataScopeViewTypeVO dataScopeViewTypeDTO;
        for (DataScopeViewTypeEnum viewTypeEnum : enums) {
            dataScopeViewTypeDTO = DataScopeViewTypeVO.builder().viewType(viewTypeEnum.getValue()).viewTypeLevel(viewTypeEnum.getLevel()).viewTypeName(viewTypeEnum.getDesc()).build();
            viewTypeList.add(dataScopeViewTypeDTO);
        }
        Comparator<DataScopeViewTypeVO> comparator = Comparator.comparing(DataScopeViewTypeVO::getViewTypeLevel);
        viewTypeList.sort(comparator);
        return viewTypeList;
    }

    @Override
    public List<DataScopeDTO> getDataScopeType() {
        List<DataScopeDTO> dataScopeTypeList = Lists.newArrayList();
        DataScopeTypeEnum[] enums = DataScopeTypeEnum.class.getEnumConstants();
        DataScopeDTO dataScopeDTO;
        for (DataScopeTypeEnum typeEnum : enums) {
            dataScopeDTO =
                DataScopeDTO.builder().dataScopeType(typeEnum.getValue()).dataScopeTypeDesc(typeEnum.getDesc()).dataScopeTypeName(typeEnum.getName()).dataScopeTypeSort(typeEnum.getSort()).build();
            dataScopeTypeList.add(dataScopeDTO);
        }
        Comparator<DataScopeDTO> comparator = Comparator.comparing(DataScopeDTO::getDataScopeTypeSort);
        dataScopeTypeList.sort(comparator);
        return dataScopeTypeList;
    }

    /**
     * 获取某个角色的数据范围设置信息
     *
     * @param roleId
     * @return
     */
    @Override
    public ResponseDTO<List<DataScopeSelectVO>> dataScopeListByRole(Long roleId) {

        List<DataScopeRole> dataScopeRoleList = dataScopeRoleMapper.listByRoleId(roleId);
        if (CollectionUtils.isEmpty(dataScopeRoleList)) {
            return ResponseDTO.succData(Lists.newArrayList());
        }
        List<DataScopeSelectVO> dataScopeSelects = BeanUtil.copyList(dataScopeRoleList, DataScopeSelectVO.class);
        return ResponseDTO.succData(dataScopeSelects);
    }

    /**
     * 批量设置某个角色的数据范围设置信息
     *
     * @param batchSetRoleDTO
     * @return
     */
    @Override
    public ResponseDTO<String> dataScopeBatchSet(DataScopeBatchSetRoleDTO batchSetRoleDTO) {
        List<DataScopeBatchSetDTO> batchSetList = batchSetRoleDTO.getBatchSetList();
        if (CollectionUtils.isEmpty(batchSetList)) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM, "缺少配置信息");
        }
        List<DataScopeRole> dataScopeRoleList = BeanUtil.copyList(batchSetList, DataScopeRole.class);
        for(DataScopeRole dataScopeRole:dataScopeRoleList){
            dataScopeRole.setId(idGeneratorUtil.snowflakeId());
            dataScopeRole.setRoleId(batchSetRoleDTO.getRoleId());
        }
        dataScopeRoleMapper.deleteByRoleId(batchSetRoleDTO.getRoleId());
        dataScopeRoleMapper.batchInsert(dataScopeRoleList);
        return ResponseDTO.succ();
    }

}
