package cn.edu.ncu.service;

import cn.edu.ncu.pojo.vo.DepartmentVO;

import java.util.List;

/**
 * [  ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2018 1024lab.netInc. All rights reserved.
 * @date 2019/4/29 0029 下午 13:52
 * @since JDK1.8
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
