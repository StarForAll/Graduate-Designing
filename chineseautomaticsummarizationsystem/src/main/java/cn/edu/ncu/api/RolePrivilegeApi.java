package cn.edu.ncu.api;

import cn.edu.ncu.common.annotation.OperateLog;
import cn.edu.ncu.common.constant.SwaggerTagConst;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.RolePrivilegeDTO;
import cn.edu.ncu.pojo.vo.RolePrivilegeTreeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: XiongZhiCong
 * @Description: 角色权限API
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@OperateLog
@Api(tags = {SwaggerTagConst.Admin.MANAGER_ROLE_PRIVILEGE})
public interface RolePrivilegeApi {

    @ApiOperation(value = "更新角色权限", notes = "更新角色权限")
    @PostMapping("/privilege/updateRolePrivilege")
    ResponseDTO<String> updateRolePrivilege(@Valid @RequestBody RolePrivilegeDTO updateDTO) ;

    @ApiOperation(value = "获取角色可选的功能权限", notes = "获取角色可选的功能权限")
    @GetMapping("/privilege/listPrivilegeByRoleId/{roleId}")
     ResponseDTO<RolePrivilegeTreeVO> listPrivilegeByRoleId(@PathVariable("roleId") Long roleId) ;

}
