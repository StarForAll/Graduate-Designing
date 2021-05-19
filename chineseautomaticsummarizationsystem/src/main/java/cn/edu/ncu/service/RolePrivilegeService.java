package cn.edu.ncu.service;

import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.RolePrivilegeDTO;
import cn.edu.ncu.pojo.vo.RolePrivilegeTreeVO;

/**
 * [ 后台员工权限 ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2019 1024lab.netInc. All rights reserved.
 * @date
 * @since JDK1.8
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
