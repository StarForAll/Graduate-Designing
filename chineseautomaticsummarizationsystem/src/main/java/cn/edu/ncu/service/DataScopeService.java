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
 * @Author: XiongZhiCong
 * @Description:数据范围
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
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
