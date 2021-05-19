package cn.edu.ncu.service;

import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.bo.RequestTokenBO;
import cn.edu.ncu.pojo.dto.EmployeeLoginFormDTO;
import cn.edu.ncu.pojo.dto.LoginPrivilegeDTO;
import cn.edu.ncu.pojo.vo.KaptchaVO;
import cn.edu.ncu.pojo.vo.LoginDetailVO;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * [  ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2018 1024lab.netInc. All rights reserved.
 * @date 2019/3/27 0027 下午 18:10
 * @since JDK1.8
 */
public interface LoginService {

    /**
     * 登陆
     *
     * @param loginForm 登录名 密码
     * @return 登录用户基本信息
     */
     ResponseDTO<LoginDetailVO> login(@Valid EmployeeLoginFormDTO loginForm, HttpServletRequest request) ;

    /**
     * 手机端退出登陆，清除token缓存
     *
     * @param requestToken
     * @return 退出登陆是否成功，bool
     */
     ResponseDTO<Boolean> logoutByToken(RequestTokenBO requestToken) ;

    /**
     * 获取验证码
     *
     * @return
     */
     ResponseDTO<KaptchaVO> verificationCode() ;


    /**
     * 初始化员工权限
     *
     * @param employeeId
     * @return
     */
     List<LoginPrivilegeDTO> initEmployeePrivilege(Long employeeId) ;

     LoginDetailVO getSession(RequestTokenBO requestUser) ;
}
