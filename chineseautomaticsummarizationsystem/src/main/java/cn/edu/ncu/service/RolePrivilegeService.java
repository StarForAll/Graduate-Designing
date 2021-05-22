package cn.edu.ncu.service;

import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.RolePrivilegeDTO;
import cn.edu.ncu.pojo.vo.RolePrivilegeTreeVO;

/**
 * @Author: XiongZhiCong
 * @Description: 后台员工权限
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public interface RolePrivilegeService {

    /**
     * 更新角色权限
     *
     * @param updateDTO
     * @return ResponseDTO
     */
     ResponseDTO<String> updateRolePrivilege(RolePrivilegeDTO updateDTO) ;

     ResponseDTO<RolePrivilegeTreeVO> listPrivilegeByRoleId(Long roleId);

}
