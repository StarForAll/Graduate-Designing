package cn.edu.ncu.api;

import cn.edu.ncu.common.annotation.OperateLog;
import cn.edu.ncu.common.constant.SwaggerTagConst;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.RoleBatchDTO;
import cn.edu.ncu.pojo.dto.RoleQueryDTO;
import cn.edu.ncu.pojo.vo.EmployeeVO;
import cn.edu.ncu.pojo.vo.RoleSelectedVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 用户角色管理路由
 *
 * @author listen
 * @date 2017/12/28 10:10
 */
@Api(tags = {SwaggerTagConst.Admin.MANAGER_ROLE_USER})
@OperateLog
public interface RoleEmployeeApi {


    @ApiOperation(value = "获取角色成员-员工列表", notes = "获取角色成员-员工列表（分页）")
    @PostMapping("/role/listEmployee")
     ResponseDTO<PageResultDTO<EmployeeVO>> listEmployeeByName(@Valid @RequestBody RoleQueryDTO queryDTO) ;

    @ApiOperation(value = "根据角色id获取角色员工列表(无分页)", notes = "根据角色id获取角色成员-员工列表")
    @GetMapping("/role/listAllEmployee/{roleId}")
     ResponseDTO<List<EmployeeVO>> listAllEmployeeRoleId(@PathVariable Long roleId);

    @ApiOperation(value = "从角色成员列表中移除员工", notes = "从角色成员列表中移除员工")
    @ApiImplicitParams({@ApiImplicitParam(name = "employeeId", value = "员工id", paramType = "query", required = true), @ApiImplicitParam(name = "roleId", value = "角色id", paramType = "query",
            required = true)})
    @GetMapping("/role/removeEmployee")
     ResponseDTO<String> removeEmployee(Long employeeId, Long roleId) ;

    @ApiOperation(value = "从角色成员列表中批量移除员工", notes = "从角色成员列表中批量移除员工")
    @PostMapping("/role/removeEmployeeList")
     ResponseDTO<String> removeEmployeeList(@Valid @RequestBody RoleBatchDTO removeDTO);

    @ApiOperation(value = "角色成员列表中批量添加员工", notes = "角色成员列表中批量添加员工")
    @PostMapping("/role/addEmployeeList")
     ResponseDTO<String> addEmployeeList(@Valid @RequestBody RoleBatchDTO addDTO);

    @ApiOperation(value = "通过员工id获取所有角色以及员工具有的角色", notes = "通过员工id获取所有角色以及员工具有的角色")
    @GetMapping("/role/getRoles/{employeeId}")
    @ApiImplicitParams({@ApiImplicitParam(name = "employeeId", value = "员工id", paramType = "path", required = true)})
     ResponseDTO<List<RoleSelectedVO>> getRoleByEmployeeId(@PathVariable Long employeeId);
}
