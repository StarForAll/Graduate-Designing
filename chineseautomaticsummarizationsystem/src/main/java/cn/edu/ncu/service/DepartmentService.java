package cn.edu.ncu.service;

import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.DepartmentCreateDTO;
import cn.edu.ncu.pojo.dto.DepartmentUpdateDTO;
import cn.edu.ncu.pojo.vo.DepartmentVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: 部门管理业务类
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public interface DepartmentService {


    /**
     * 获取部门树形结构
     *
     * @return
     */
     ResponseDTO<List<DepartmentVO>> listDepartment();

    /**
     * 获取所有部门和员工信息
     *
     * @param departmentName
     * @return
     */
     ResponseDTO<List<DepartmentVO>> listAllDepartmentEmployee(String departmentName);


    /**
     * 新增添加部门
     *
     * @param departmentCreateDTO
     * @return AjaxResult
     */
    @Transactional(rollbackFor = Exception.class)
    ResponseDTO<String> addDepartment(DepartmentCreateDTO departmentCreateDTO) ;

    /**
     * 更新部门信息
     *
     * @param updateDTO
     * @return AjaxResult<String>
     */
    @Transactional(rollbackFor = Exception.class)
     ResponseDTO<String> updateDepartment(DepartmentUpdateDTO updateDTO) ;

    /**
     * 根据id删除部门
     * 1、需要判断当前部门是否有子部门,有子部门则不允许删除
     * 2、需要判断当前部门是否有员工，有员工则不能删除
     *
     * @param deptId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
     ResponseDTO<String> delDepartment(Long deptId) ;

    /**
     * 根据id获取部门信息
     *
     * @param departmentId
     * @return AjaxResult<DepartmentVO>
     */
     ResponseDTO<DepartmentVO> getDepartmentById(Long departmentId) ;

    /**
     * 获取所有部门
     *
     * @return
     */
     ResponseDTO<List<DepartmentVO>> listAll() ;

    /**
     * 上下移动
     *
     * @param departmentId
     * @param swapId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
     ResponseDTO<String> upOrDown(Long departmentId, Long swapId) ;

    /**
     * 部门升级
     *
     * @param departmentId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
     ResponseDTO<String> upgrade(Long departmentId);

    /**
     * 部门降级
     *
     * @param departmentId
     * @param preId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
     ResponseDTO<String> downgrade(Long departmentId, Long preId) ;
}
