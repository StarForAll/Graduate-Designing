package cn.edu.ncu.controller;

import cn.edu.ncu.api.DepartmentApi;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.DepartmentCreateDTO;
import cn.edu.ncu.pojo.dto.DepartmentUpdateDTO;
import cn.edu.ncu.pojo.vo.DepartmentVO;
import cn.edu.ncu.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 部门管理路由器
 *
 * @author listen
 * @date 2017/12/19 14:29
 */
@RestController
public class DepartmentController implements DepartmentApi {

    @Autowired
    private DepartmentService departmentService;
    @Override
    public ResponseDTO<List<DepartmentVO>> listDepartment() {
        return departmentService.listDepartment();
    }
    @Override
    public ResponseDTO<List<DepartmentVO>> listDepartmentEmployee() {
        return departmentService.listAllDepartmentEmployee(null);
    }
    @Override
    public ResponseDTO<List<DepartmentVO>> listDepartmentEmployee(String departmentName) {
        return departmentService.listAllDepartmentEmployee(departmentName);
    }
    @Override
    public ResponseDTO<String> addDepartment(@Valid @RequestBody DepartmentCreateDTO departmentCreateDTO) {
        return departmentService.addDepartment(departmentCreateDTO);
    }
    @Override
    public ResponseDTO<String> updateDepartment(@Valid @RequestBody DepartmentUpdateDTO departmentUpdateDTO) {
        return departmentService.updateDepartment(departmentUpdateDTO);
    }
    @Override
    public ResponseDTO<String> delDepartment(@PathVariable Long deptId) {
        return departmentService.delDepartment(deptId);
    }
    @Override
    public ResponseDTO<DepartmentVO> getDepartment(@PathVariable Long deptId) {
        return departmentService.getDepartmentById(deptId);
    }
    @Override
    public ResponseDTO<List<DepartmentVO>> listAll() {
        return departmentService.listAll();
    }

    @Override
    public ResponseDTO<String> upOrDown(@PathVariable Long deptId, @PathVariable Long swapId) {
        return departmentService.upOrDown(deptId, swapId);
    }

    @Override
    public ResponseDTO<String> upgrade(@PathVariable Long deptId) {
        return departmentService.upgrade(deptId);
    }
    @Override
    public ResponseDTO<String> downgrade(@PathVariable Long deptId, @PathVariable Long preId) {
        return departmentService.downgrade(deptId, preId);
    }


}
