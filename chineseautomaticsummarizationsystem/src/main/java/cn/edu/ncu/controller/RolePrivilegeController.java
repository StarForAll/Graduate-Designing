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
 * @Author: XiongZhiCong
 * @Description: 角色权限
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
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
