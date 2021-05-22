package cn.edu.ncu.api;

import cn.edu.ncu.common.annotation.OperateLog;
import cn.edu.ncu.common.constant.SwaggerTagConst;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.DepartmentCreateDTO;
import cn.edu.ncu.pojo.dto.DepartmentUpdateDTO;
import cn.edu.ncu.pojo.vo.DepartmentVO;
import cn.edu.ncu.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: 部门管理API
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Api(tags = {SwaggerTagConst.Admin.MANAGER_DEPARTMENT})
@OperateLog
public interface DepartmentApi {


    @ApiOperation(value = "查询部门树形列表", notes = "查询部门列表")
    @GetMapping("/department/list")
     ResponseDTO<List<DepartmentVO>> listDepartment();

    @ApiOperation(value = "查询部门及员工列表", notes = "查询部门及员工列表")
    @GetMapping("/department/listEmployee")
     ResponseDTO<List<DepartmentVO>> listDepartmentEmployee();

    @ApiOperation(value = "根据部门名称查询部门及员工列表", notes = "根据部门名称查询部门及员工列表")
    @GetMapping("/department/listEmployeeByDepartmentName")
     ResponseDTO<List<DepartmentVO>> listDepartmentEmployee(String departmentName) ;

    @ApiOperation(value = "添加部门", notes = "添加部门")
    @PostMapping("/department/add")
     ResponseDTO<String> addDepartment(@Valid @RequestBody DepartmentCreateDTO departmentCreateDTO) ;

    @ApiOperation(value = "更新部门信息", notes = "更新部门信息")
    @PostMapping("/department/update")
     ResponseDTO<String> updateDepartment(@Valid @RequestBody DepartmentUpdateDTO departmentUpdateDTO);

    @ApiOperation(value = "删除部门", notes = "删除部门")
    @PostMapping("/department/delete/{deptId}")
     ResponseDTO<String> delDepartment(@PathVariable Long deptId);

    @ApiOperation(value = "获取部门信息", notes = "获取部门")
    @GetMapping("/department/query/{deptId}")
     ResponseDTO<DepartmentVO> getDepartment(@PathVariable Long deptId) ;

    @ApiOperation(value = "查询部门列表", notes = "查询部门列表")
    @GetMapping("/department/listAll")
     ResponseDTO<List<DepartmentVO>> listAll();


    @ApiOperation(value = "上下移动")
    @GetMapping("/department/upOrDown/{deptId}/{swapId}")
     ResponseDTO<String> upOrDown(@PathVariable Long deptId, @PathVariable Long swapId);

    @ApiOperation(value = "升级")
    @GetMapping("/department/upgrade/{deptId}")
     ResponseDTO<String> upgrade(@PathVariable Long deptId);

    @ApiOperation(value = "降级")
    @GetMapping("/department/downgrade/{deptId}/{preId}")
    ResponseDTO<String> downgrade(@PathVariable Long deptId, @PathVariable Long preId) ;


}
