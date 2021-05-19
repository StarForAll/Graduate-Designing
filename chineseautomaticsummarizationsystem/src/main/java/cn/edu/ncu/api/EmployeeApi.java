package cn.edu.ncu.api;

import cn.edu.ncu.common.annotation.NoValidPrivilege;
import cn.edu.ncu.common.annotation.OperateLog;
import cn.edu.ncu.common.constant.SwaggerTagConst;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.*;
import cn.edu.ncu.pojo.vo.EmployeeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 员工管理
 *
 * @author lidoudou
 * @date 2017年12月19日上午11:34:52
 */
@Api(tags = {SwaggerTagConst.Admin.MANAGER_USER})
@OperateLog
public interface EmployeeApi {

    @PostMapping("/employee/query")
    @ApiOperation(value = "员工管理查询", notes = "员工管理查询 @author lidoudou")
     ResponseDTO<PageResultDTO<EmployeeVO>> query(@RequestBody EmployeeQueryDTO query) ;

    @GetMapping("/employee/get/all")
    @ApiOperation(value = "查询所有员工基本信息，用于选择框", notes = "查询所有员工基本信息，用于选择框")
    @NoValidPrivilege
     ResponseDTO<List<EmployeeVO>> getAll() ;

    @ApiOperation(value = "添加员工", notes = "@author yandanyang")
    @PostMapping("/employee/add")
     ResponseDTO<String> addEmployee(@Valid @RequestBody EmployeeAddDTO emp) ;

    @ApiOperation(value = "禁用单个员工", notes = "@author yandanyang")
    @GetMapping("/employee/updateStatus/{employeeId}/{status}")
     ResponseDTO<String> updateStatus(@PathVariable("employeeId") Long employeeId, @PathVariable("status") Integer status) ;

    @ApiOperation(value = "批量禁用", notes = "@author yandanyang")
    @PostMapping("/employee/batchUpdateStatus")
     ResponseDTO<String> batchUpdateStatus(@Valid @RequestBody EmployeeBatchUpdateStatusDTO batchUpdateStatusDTO);

    @ApiOperation(value = "更新员工信息", notes = "@author yandanyang")
    @PostMapping("/employee/update")
     ResponseDTO<String> updateEmployee(@Valid @RequestBody EmployeeUpdateDTO employeeUpdateDto) ;

    @ApiOperation(value = "删除员工信息", notes = "@author yandanyang")
    @PostMapping("/employee/delete/{employeeId}")
     ResponseDTO<String> deleteEmployeeById(@PathVariable("employeeId") Long employeeId) ;

    @ApiOperation(value = "单个员工角色授权", notes = "@author yandanyang")
    @PostMapping("/employee/updateRoles")
     ResponseDTO<String> updateRoles(@Valid @RequestBody EmployeeUpdateRolesDTO updateRolesDTO);

    @ApiOperation(value = "修改密码", notes = "@author yandanyang")
    @PostMapping("/employee/updatePwd")
     ResponseDTO<String> updatePwd(@Valid @RequestBody EmployeeUpdatePwdDTO updatePwdDTO) ;

    @ApiOperation(value = "通过部门id获取当前部门的人员&没有部门的人", notes = "@author yandanyang")
    @GetMapping("/employee/listEmployeeByDeptId/{deptId}")
     ResponseDTO<List<EmployeeVO>> listEmployeeByDeptId(@PathVariable Long deptId) ;

    @ApiOperation(value = "员工重置密码", notes = "@author lizongliang")
    @GetMapping("/employee/resetPasswd/{employeeId}")
    ResponseDTO resetPasswd(@PathVariable("employeeId") Integer employeeId) ;

}
