package cn.edu.ncu.service;

import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.DataScopeBatchSetRoleDTO;
import cn.edu.ncu.pojo.dto.DataScopeDTO;
import cn.edu.ncu.pojo.vo.DataScopeAndViewTypeVO;
import cn.edu.ncu.pojo.vo.DataScopeSelectVO;
import cn.edu.ncu.pojo.vo.DataScopeViewTypeVO;
import org.springframework.transaction.annotation.Transactional;
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
public interface DataScopeService {


    /**
     * 获取所有可以进行数据范围配置的信息
     *
     * @return
     */
     ResponseDTO<List<DataScopeAndViewTypeVO>> dataScopeList();
    /**
     * 获取当前系统存在的数据可见范围
     *
     * @return
     */
     List<DataScopeViewTypeVO> getViewType() ;

     List<DataScopeDTO> getDataScopeType() ;

    /**
     * 获取某个角色的数据范围设置信息
     *
     * @param roleId
     * @return
     */
     ResponseDTO<List<DataScopeSelectVO>> dataScopeListByRole(Long roleId) ;

    /**
     * 批量设置某个角色的数据范围设置信息
     *
     * @param batchSetRoleDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
     ResponseDTO<String> dataScopeBatchSet(DataScopeBatchSetRoleDTO batchSetRoleDTO) ;

}
