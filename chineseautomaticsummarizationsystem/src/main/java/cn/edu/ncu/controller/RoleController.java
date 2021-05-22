package cn.edu.ncu.controller;
import cn.edu.ncu.api.RoleApi;
import cn.edu.ncu.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.RoleAddDTO;
import cn.edu.ncu.pojo.dto.RoleUpdateDTO;
import cn.edu.ncu.pojo.vo.RoleVO;
import javax.validation.Valid;
import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: 角色
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@RestController
public class RoleController implements RoleApi {

    @Autowired
    private RoleService roleService;
    @Override
    public ResponseDTO addRole(@Valid @RequestBody RoleAddDTO roleAddDTO) {
        return roleService.addRole(roleAddDTO);
    }
    @Override
    public ResponseDTO<String> deleteRole(@PathVariable("roleId") Long roleId) {
        return roleService.deleteRole(roleId);
    }
    @Override
    public ResponseDTO<String> updateRole(@Valid @RequestBody RoleUpdateDTO roleUpdateDTO) {
        return roleService.updateRole(roleUpdateDTO);
    }
    @Override
    public ResponseDTO<RoleVO> getRole(@PathVariable("roleId") Long roleId) {
        return roleService.getRoleById(roleId);
    }

    @Override
    public ResponseDTO<List<RoleVO>> getAllRole() {
        return roleService.getAllRole();
    }

}
