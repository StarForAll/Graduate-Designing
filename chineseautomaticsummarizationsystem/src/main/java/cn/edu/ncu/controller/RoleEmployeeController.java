package cn.edu.ncu.controller;

import cn.edu.ncu.api.RoleEmployeeApi;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.RoleBatchDTO;
import cn.edu.ncu.pojo.dto.RoleQueryDTO;
import cn.edu.ncu.pojo.vo.EmployeeVO;
import cn.edu.ncu.pojo.vo.RoleSelectedVO;
import cn.edu.ncu.service.RoleEmployeeService;
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
@RestController
public class RoleEmployeeController implements RoleEmployeeApi {

    @Autowired
    private RoleEmployeeService roleEmployeeService;
    @Override
    public ResponseDTO<PageResultDTO<EmployeeVO>> listEmployeeByName(@Valid @RequestBody RoleQueryDTO queryDTO) {
        return roleEmployeeService.listEmployeeByName(queryDTO);
    }
    @Override
    public ResponseDTO<List<EmployeeVO>> listAllEmployeeRoleId(@PathVariable Long roleId) {
        return roleEmployeeService.getAllEmployeeByRoleId(roleId);
    }
    @Override
    public ResponseDTO<String> removeEmployee(Long employeeId, Long roleId) {
        return roleEmployeeService.removeEmployeeRole(employeeId, roleId);
    }
    @Override
    public ResponseDTO<String> removeEmployeeList(@Valid @RequestBody RoleBatchDTO removeDTO) {
        return roleEmployeeService.batchRemoveEmployeeRole(removeDTO);
    }
    @Override
    public ResponseDTO<String> addEmployeeList(@Valid @RequestBody RoleBatchDTO addDTO) {
        return roleEmployeeService.batchAddEmployeeRole(addDTO);
    }

    @Override
    public ResponseDTO<List<RoleSelectedVO>> getRoleByEmployeeId(@PathVariable Long employeeId) {
        return roleEmployeeService.getRolesByEmployeeId(employeeId);
    }
}
