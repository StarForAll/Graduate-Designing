package cn.edu.ncu.service;

import cn.edu.ncu.dao.entity.Privilege;
import cn.edu.ncu.pojo.bo.RequestTokenBO;

import java.util.*;

/**
 * @Author: XiongZhiCong
 * @Description: 权限员工
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
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
