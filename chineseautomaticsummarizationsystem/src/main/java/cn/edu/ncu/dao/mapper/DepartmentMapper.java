package cn.edu.ncu.dao.mapper;

import cn.edu.ncu.common.core.dao.CommonMapper;
import cn.edu.ncu.dao.entity.Department;
import cn.edu.ncu.pojo.vo.DepartmentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: 部门Mapper
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Mapper
public interface DepartmentMapper extends CommonMapper<Department> {
    void update(Department department);
    /**
     * 根据部门id，查询此部门直接子部门的数量
     *
     * @param deptId
     * @return int 子部门的数量
     */
    Integer countSubDepartment(@Param("deptId") Long deptId);

    /**
     * 获取全部部门列表
     *
     * @return List<DepartmentVO>
     */
    List<DepartmentVO> listAll();

    /**
     * 功能描述: 根据父部门id查询
     *
     * @param
     * @return
     * @auther yandanyang
     * @date 2018/8/25 0025 上午 9:46
     */
    List<DepartmentVO> selectByParentId(@Param("departmentId") Long departmentId);

}
