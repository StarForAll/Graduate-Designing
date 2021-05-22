package cn.edu.ncu.service;

import cn.edu.ncu.pojo.bo.RequestTokenBO;
import cn.edu.ncu.pojo.dto.EmployeeDTO;

/**
 * @Author: XiongZhiCong
 * @Description: 登录Token
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public interface LoginTokenService {


    /**
     * 功能描述: 生成JWT TOKEN
     *
     * @param employeeDTO
     * @return
     * @auther yandanyang
     * @date 2018/9/12 0012 上午 10:08
     */
     String generateToken(EmployeeDTO employeeDTO) ;

    /**
     * 功能描述: 根据登陆token获取登陆信息
     *
     * @param
     * @return
     * @auther yandanyang
     * @date 2018/9/12 0012 上午 10:11
     */
     RequestTokenBO getEmployeeTokenInfo(String token) ;

}
