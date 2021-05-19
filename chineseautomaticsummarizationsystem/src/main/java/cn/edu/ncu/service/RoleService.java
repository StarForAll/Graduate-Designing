package cn.edu.ncu.service;

import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.RoleAddDTO;
import cn.edu.ncu.pojo.dto.RoleUpdateDTO;
import cn.edu.ncu.pojo.vo.RoleVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色管理业务
 *
 * @author listen
 * @date 2017/12/28 09:37
 */
public interface RoleService {


    /**
     * 新增添加角色
     *
     * @param roleAddDTO
     * @return ResponseDTO
     */
     ResponseDTO addRole(RoleAddDTO roleAddDTO) ;

    /**
     * 根据角色id 删除
     *
     * @param roleId
     * @return ResponseDTO
     */
    @Transactional(rollbackFor = Exception.class)
     ResponseDTO deleteRole(Long roleId) ;

    /**
     * 更新角色
     *
     * @param roleUpdateDTO
     * @return ResponseDTO
     */
    @Transactional(rollbackFor = Exception.class)
     ResponseDTO<String> updateRole(RoleUpdateDTO roleUpdateDTO) ;

    /**
     * 根据id获取角色数据
     *
     * @param roleId
     * @return ResponseDTO<RoleDTO>
     */
     ResponseDTO<RoleVO> getRoleById(Long roleId);

    /**
     * 获取所有角色列表
     *
     * @return ResponseDTO
     */
     ResponseDTO<List<RoleVO>> getAllRole() ;
}
