package cn.edu.ncu.service.impl;
import cn.edu.ncu.common.constant.DepartmentResponseCodeConst;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.common.util.bean.BeanUtil;
import cn.edu.ncu.common.util.dao.IdGeneratorUtil;
import cn.edu.ncu.dao.entity.Department;
import cn.edu.ncu.pojo.dto.DepartmentCreateDTO;
import cn.edu.ncu.pojo.dto.DepartmentUpdateDTO;
import cn.edu.ncu.pojo.dto.EmployeeDTO;
import cn.edu.ncu.pojo.vo.DepartmentVO;
import cn.edu.ncu.dao.mapper.DepartmentMapper;
import cn.edu.ncu.dao.mapper.EmployeeMapper;
import cn.edu.ncu.service.DepartmentService;
import cn.edu.ncu.service.DepartmentTreeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: XiongZhiCong
 * @Description: 部门管理业务类
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    private DepartmentMapper departmentMapper;

    @Resource
    private EmployeeMapper employeeMapper;
    @Autowired
    private IdGeneratorUtil idGeneratorUtil;
    @Autowired
    private DepartmentTreeService departmentTreeService;

    /**
     * 获取部门树形结构
     *
     * @return
     */
    @Override
    public ResponseDTO<List<DepartmentVO>> listDepartment() {
        List<DepartmentVO> departmentVOList = departmentMapper.listAll();
        List<DepartmentVO> result = departmentTreeService.buildTree(departmentVOList);
        return ResponseDTO.succData(result);
    }

    /**
     * 获取所有部门和员工信息
     *
     * @param departmentName
     * @return
     */
    @Override
    public ResponseDTO<List<DepartmentVO>> listAllDepartmentEmployee(String departmentName) {

        // 获取全部部门列表
        List<DepartmentVO> departmentVOList = departmentMapper.listAll();
        if (StringUtils.isNotBlank(departmentName)) {
            // 检索条件不为空的时候 过滤部门列表
            departmentVOList = filterDepartment(departmentVOList, departmentName);
        }

        Map<Long, DepartmentVO> departmentMap = departmentVOList.stream().collect(Collectors.toMap(DepartmentVO::getId, Function.identity()));
        // 获取全部员工列表
        List<EmployeeDTO> employeeList = employeeMapper.listAll();
        employeeList.forEach(employeeDTO -> {

            DepartmentVO departmentVO = departmentMap.get(employeeDTO.getDepartmentId());
            if (null == departmentVO) {
                return;
            }
            List<EmployeeDTO> employeeDTOList = departmentVO.getEmployees();
            if (null == employeeDTOList) {
                employeeDTOList = new ArrayList<>();
            }
            employeeDTOList.add(employeeDTO);
            departmentVO.setEmployees(employeeDTOList);
        });
        List<DepartmentVO> result = departmentTreeService.buildTree(departmentVOList);
        return ResponseDTO.succData(result);
    }

    /**
     * 过滤部门名称，获取过滤后的结果
     *
     * @author lidoudou
     * @date 2019/4/28 20:17
     */
    private List<DepartmentVO> filterDepartment(List<DepartmentVO> departmentVOList, String departmentName) {
        Map<Long, DepartmentVO> departmentMap = new HashMap<>();
        departmentVOList.forEach(item -> {
            if (item.getName().indexOf(departmentName) < 0) {
                return;
            }
            // 当前部门包含关键字
            departmentMap.put(item.getId(), item);
            Long parentId = item.getParentId();
            if (null != parentId) {
                List<DepartmentVO> filterResult = new ArrayList<>();
                getParentDepartment(departmentVOList, parentId, filterResult);
                for (DepartmentVO dto : filterResult) {
                    if (!departmentMap.containsKey(dto.getId())) {
                        departmentMap.put(dto.getId(), dto);
                    }
                }
            }
        });
        return departmentMap.values().stream().collect(Collectors.toList());
    }

    private List<DepartmentVO> getParentDepartment(List<DepartmentVO> departmentVOList, Long parentId, List<DepartmentVO> result) {
        List<DepartmentVO> deptList = departmentVOList.stream().filter(e -> e.getId().equals(parentId)).collect(Collectors.toList());
        for (DepartmentVO item : deptList) {
            result.add(item);
            if (item.getParentId() != 0 && item.getParentId() != null) {
                result.addAll(getParentDepartment(departmentVOList, item.getParentId(), result));
            }
        }
        return result;
    }

    /**
     * 新增添加部门
     *
     * @param departmentCreateDTO
     * @return AjaxResult
     */
    @Override
    public ResponseDTO<String> addDepartment(DepartmentCreateDTO departmentCreateDTO) {
        Department departmentEntity = BeanUtil.copy(departmentCreateDTO, Department.class);
        departmentEntity.setSort(0L);
        if(departmentEntity.getId()==null){
            departmentEntity.setId(idGeneratorUtil.snowflakeId());
        }
        if(departmentEntity.getUpdateTime()==null){
            departmentEntity.setUpdateTime(new Date(System.currentTimeMillis()));
        }
        if(departmentEntity.getCreateTime()==null){
            departmentEntity.setCreateTime(new Date(System.currentTimeMillis()));
        }
        departmentMapper.insert(departmentEntity);
        departmentEntity.setSort(departmentEntity.getId());
        departmentMapper.update(departmentEntity);
        return ResponseDTO.succ();
    }

    /**
     * 更新部门信息
     *
     * @param updateDTO
     * @return AjaxResult<String>
     */
    @Override
    public ResponseDTO<String> updateDepartment(DepartmentUpdateDTO updateDTO) {
        if (updateDTO.getParentId() == null) {
            return ResponseDTO.wrap(DepartmentResponseCodeConst.PARENT_ID_ERROR);
        }
        Department entity = departmentMapper.selectByPrimaryKey(updateDTO.getId());
        if (entity == null) {
            return ResponseDTO.wrap(DepartmentResponseCodeConst.NOT_EXISTS);
        }
        Department departmentEntity = BeanUtil.copy(updateDTO, Department.class);
        departmentEntity.setSort(entity.getSort());
        departmentMapper.update(departmentEntity);
        return ResponseDTO.succ();
    }

    /**
     * 根据id删除部门
     * 1、需要判断当前部门是否有子部门,有子部门则不允许删除
     * 2、需要判断当前部门是否有员工，有员工则不能删除
     *
     * @param deptId
     * @return
     */
    @Override
    public ResponseDTO<String> delDepartment(Long deptId) {
        Department departmentEntity = departmentMapper.selectByPrimaryKey(deptId);
        if (null == departmentEntity) {
            return ResponseDTO.wrap(DepartmentResponseCodeConst.DEPT_NOT_EXISTS);
        }
        // 是否有子级部门
        int subDepartmentNum = departmentMapper.countSubDepartment(deptId);
        if (subDepartmentNum > 0) {
            return ResponseDTO.wrap(DepartmentResponseCodeConst.CANNOT_DEL_DEPARTMENT_WITH_CHILD);
        }

        // 是否有未删除员工
        int employeeNum = employeeMapper.countByDepartmentId(deptId, false);
        if (employeeNum > 0) {
            return ResponseDTO.wrap(DepartmentResponseCodeConst.CANNOT_DEL_DEPARTMENT_WITH_EMPLOYEE);
        }
        departmentMapper.deleteByPrimaryKey(deptId);
        return ResponseDTO.succ();
    }

    /**
     * 根据id获取部门信息
     *
     * @param departmentId
     * @return AjaxResult<DepartmentVO>
     */
    @Override
    public ResponseDTO<DepartmentVO> getDepartmentById(Long departmentId) {
        Department departmentEntity = departmentMapper.selectByPrimaryKey(departmentId);
        if (departmentEntity == null) {
            return ResponseDTO.wrap(DepartmentResponseCodeConst.DEPT_NOT_EXISTS);
        }
        DepartmentVO departmentVO = BeanUtil.copy(departmentEntity, DepartmentVO.class);
        return ResponseDTO.succData(departmentVO);
    }

    /**
     * 获取所有部门
     *
     * @return
     */
    @Override
    public ResponseDTO<List<DepartmentVO>> listAll() {
        List<DepartmentVO> departmentVOList = departmentMapper.listAll();
        return ResponseDTO.succData(departmentVOList);
    }

    /**
     * 上下移动
     *
     * @param departmentId
     * @param swapId
     * @return
     */
    @Override
    public ResponseDTO<String> upOrDown(Long departmentId, Long swapId) {
        Department departmentEntity = departmentMapper.selectByPrimaryKey(departmentId);
        if (departmentEntity == null) {
            return ResponseDTO.wrap(DepartmentResponseCodeConst.NOT_EXISTS);
        }
        Department swapEntity = departmentMapper.selectByPrimaryKey(swapId);
        if (swapEntity == null) {
            return ResponseDTO.wrap(DepartmentResponseCodeConst.NOT_EXISTS);
        }
        Long departmentSort = departmentEntity.getSort();
        departmentEntity.setSort(swapEntity.getSort());
        departmentMapper.update(departmentEntity);
        swapEntity.setSort(departmentSort);
        departmentMapper.update(swapEntity);
        return ResponseDTO.succ();
    }

    /**
     * 部门升级
     *
     * @param departmentId
     * @return
     */
    @Override
    public ResponseDTO<String> upgrade(Long departmentId) {
        Department departmentEntity = departmentMapper.selectByPrimaryKey(departmentId);
        if (departmentEntity == null) {
            return ResponseDTO.wrap(DepartmentResponseCodeConst.NOT_EXISTS);
        }
        if (departmentEntity.getParentId() == null || departmentEntity.getParentId().equals(0)) {
            return ResponseDTO.wrap(DepartmentResponseCodeConst.ERROR_PARAM, "此部门已经是根节点无法移动");
        }
        Department parentEntity = departmentMapper.selectByPrimaryKey(departmentEntity.getParentId());

        departmentEntity.setParentId(parentEntity.getParentId());
        departmentMapper.update(departmentEntity);
        return ResponseDTO.succ();
    }

    /**
     * 部门降级
     *
     * @param departmentId
     * @param preId
     * @return
     */
    @Override
    public ResponseDTO<String> downgrade(Long departmentId, Long preId) {
        Department departmentEntity = departmentMapper.selectByPrimaryKey(departmentId);
        if (departmentEntity == null) {
            return ResponseDTO.wrap(DepartmentResponseCodeConst.NOT_EXISTS);
        }
        Department preEntity = departmentMapper.selectByPrimaryKey(preId);
        if (preEntity == null) {
            return ResponseDTO.wrap(DepartmentResponseCodeConst.NOT_EXISTS);
        }
        departmentEntity.setParentId(preEntity.getId());
        departmentMapper.update(departmentEntity);
        return ResponseDTO.succ();
    }
}
