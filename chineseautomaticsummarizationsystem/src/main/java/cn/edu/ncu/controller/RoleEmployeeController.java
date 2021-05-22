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
 * @Author: XiongZhiCong
 * @Description: 用户角色
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
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
