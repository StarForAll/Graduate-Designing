package cn.edu.ncu.service;

import cn.edu.ncu.pojo.vo.DepartmentVO;

import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: 部门树
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public interface DepartmentTreeService {


    /**
     * 构建部门树结构
     * @param departmentVOList
     * @return
     */
     List<DepartmentVO> buildTree(List<DepartmentVO> departmentVOList) ;




    /**
     * 通过部门id,获取当前以及下属部门
     */
     void buildIdList(Long deptId, List<Long> result) ;

}
