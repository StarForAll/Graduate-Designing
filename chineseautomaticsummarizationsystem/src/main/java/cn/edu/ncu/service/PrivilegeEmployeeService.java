package cn.edu.ncu.service;

import cn.edu.ncu.dao.entity.Privilege;
import cn.edu.ncu.pojo.bo.RequestTokenBO;

import java.util.*;

/**
 * [ 后台员工权限缓存方法 ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2018 1024lab.netInc. All rights reserved.
 * @date 2019/3/28 0028 下午 14:07
 * @since JDK1.8
 */
public interface PrivilegeEmployeeService {


    /**
     * 移除某人缓存中的权限
     *
     * @param employeeId
     */
     void removeCache(Long employeeId);

    /**
     * 检查某人是否有访问某个方法的权限
     *
     * @param requestTokenBO
     * @param controllerName
     * @param methodName
     * @return
     */
     Boolean checkEmployeeHavePrivilege(RequestTokenBO requestTokenBO, String controllerName, String methodName) ;

     List<Privilege> getEmployeeAllPrivilege(Long employeeId);

    /**
     * 判断是否为超级管理员
     *
     * @param employeeId
     * @return
     */
     Boolean isSuperman(Long employeeId) ;

    /**
     * 根据员工ID 获取 权限信息
     *
     * @param employeeId
     * @return
     */
     List<Privilege> getPrivilegesByEmployeeId(Long employeeId) ;


     Map<String, List<String>> updateCachePrivilege(Long employeeId, List<Privilege> privilegeEntities) ;

     void updateOnlineEmployeePrivilegeByRoleId(Long roleId);
}
