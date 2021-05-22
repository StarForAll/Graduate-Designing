package cn.edu.ncu.api;
import cn.edu.ncu.common.annotation.OperateLog;
import cn.edu.ncu.common.constant.SwaggerTagConst;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.RoleAddDTO;
import cn.edu.ncu.pojo.dto.RoleUpdateDTO;
import cn.edu.ncu.pojo.vo.RoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: 角色管理API
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Api(tags = {SwaggerTagConst.Admin.MANAGER_ROLE})
@OperateLog
public interface RoleApi {

    @ApiOperation(value = "添加角色", notes = "添加角色")
    @PostMapping("/role/add")
     ResponseDTO addRole(@Valid @RequestBody RoleAddDTO roleAddDTO) ;

    @ApiOperation(value = "删除角色", notes = "根据id删除角色")
    @GetMapping("/role/delete/{roleId}")
     ResponseDTO<String> deleteRole(@PathVariable("roleId") Long roleId) ;

    @ApiOperation(value = "更新角色", notes = "更新角色")
    @PostMapping("/role/update")
     ResponseDTO<String> updateRole(@Valid @RequestBody RoleUpdateDTO roleUpdateDTO) ;

    @ApiOperation(value = "获取角色数据", notes = "根据id获取角色数据")
    @GetMapping("/role/get/{roleId}")
     ResponseDTO<RoleVO> getRole(@PathVariable("roleId") Long roleId) ;

    @ApiOperation(value = "获取所有角色", notes = "获取所有角色数据")
    @GetMapping("/role/getAll")
     ResponseDTO<List<RoleVO>> getAllRole() ;

}
