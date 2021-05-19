package cn.edu.ncu.controller;

import cn.edu.ncu.api.RolePrivilegeApi;
import cn.edu.ncu.service.RolePrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.RolePrivilegeDTO;
import cn.edu.ncu.pojo.vo.RolePrivilegeTreeVO;
import javax.validation.Valid;

/**
 * [ 与员工权限相关：角色权限关系、权限列表 ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2019 1024lab.netInc. All rights reserved.
 * @date
 * @since JDK1.8
 */
@RestController
public class RolePrivilegeController implements RolePrivilegeApi {

    @Autowired
    private RolePrivilegeService rolePrivilegeService;

    @Override
    public ResponseDTO<String> updateRolePrivilege(@Valid @RequestBody RolePrivilegeDTO updateDTO) {
        return rolePrivilegeService.updateRolePrivilege(updateDTO);
    }
    @Override
    public ResponseDTO<RolePrivilegeTreeVO> listPrivilegeByRoleId(@PathVariable("roleId") Long roleId) {
        return rolePrivilegeService.listPrivilegeByRoleId(roleId);
    }

}
