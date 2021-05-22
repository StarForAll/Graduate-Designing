package cn.edu.ncu.controller;

import cn.edu.ncu.api.EmployeeApi;
import cn.edu.ncu.common.constant.SwaggerTagConst;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.common.util.web.RequestTokenUtil;
import cn.edu.ncu.pojo.bo.RequestTokenBO;
import cn.edu.ncu.pojo.dto.*;
import cn.edu.ncu.pojo.vo.EmployeeVO;
import cn.edu.ncu.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: 员工管理
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@RestController
public class EmployeeController implements EmployeeApi {

    @Autowired
    private EmployeeService employeeService;
    @Override
    public ResponseDTO<PageResultDTO<EmployeeVO>> query(@RequestBody EmployeeQueryDTO query) {
        return employeeService.selectEmployeeList(query);
    }
    @Override
    public ResponseDTO<List<EmployeeVO>> getAll() {
        return ResponseDTO.succData(employeeService.getAllEmployee());
    }
    @Override
    public ResponseDTO<String> addEmployee(@Valid @RequestBody EmployeeAddDTO emp) {
        RequestTokenBO requestToken = RequestTokenUtil.getRequestUser();
        return employeeService.addEmployee(emp, requestToken);
    }
    @Override
    public ResponseDTO<String> updateStatus(@PathVariable("employeeId") Long employeeId, @PathVariable("status") Integer status) {
        return employeeService.updateStatus(employeeId, status);
    }
    @Override
    public ResponseDTO<String> batchUpdateStatus(@Valid @RequestBody EmployeeBatchUpdateStatusDTO batchUpdateStatusDTO) {
        return employeeService.batchUpdateStatus(batchUpdateStatusDTO);
    }
    @Override
    public ResponseDTO<String> updateEmployee(@Valid @RequestBody EmployeeUpdateDTO employeeUpdateDto) {
        return employeeService.updateEmployee(employeeUpdateDto);
    }
    @Override
    public ResponseDTO<String> deleteEmployeeById(@PathVariable("employeeId") Long employeeId) {
        return employeeService.deleteEmployeeById(employeeId);
    }
    @Override
    public ResponseDTO<String> updateRoles(@Valid @RequestBody EmployeeUpdateRolesDTO updateRolesDTO) {
        return employeeService.updateRoles(updateRolesDTO);
    }
    @Override
    public ResponseDTO<String> updatePwd(@Valid @RequestBody EmployeeUpdatePwdDTO updatePwdDTO) {
        RequestTokenBO requestToken = RequestTokenUtil.getRequestUser();
        return employeeService.updatePwd(updatePwdDTO, requestToken);
    }
    @Override
    public ResponseDTO<List<EmployeeVO>> listEmployeeByDeptId(@PathVariable Long deptId) {
        return employeeService.getEmployeeByDeptId(deptId);
    }

    @Override
    public ResponseDTO resetPasswd(@PathVariable("employeeId") Integer employeeId) {
        return employeeService.resetPasswd(employeeId);
    }

}
