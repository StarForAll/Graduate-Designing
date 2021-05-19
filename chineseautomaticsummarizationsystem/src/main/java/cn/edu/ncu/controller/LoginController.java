package cn.edu.ncu.controller;

import cn.edu.ncu.api.LoginApi;
import cn.edu.ncu.common.constant.LoginResponseCodeConst;
import cn.edu.ncu.common.util.web.RequestTokenUtil;
import cn.edu.ncu.pojo.bo.RequestTokenBO;
import cn.edu.ncu.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.EmployeeLoginFormDTO;
import cn.edu.ncu.pojo.vo.KaptchaVO;
import cn.edu.ncu.pojo.vo.LoginDetailVO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 后台登录
 *
 * @author lidoudou
 * @date 2017年12月19日上午11:46:04
 */
@RestController
public class LoginController implements LoginApi {

    @Autowired
    private LoginService loginService;
    @Override
    public ResponseDTO<LoginDetailVO> login(@Valid @RequestBody EmployeeLoginFormDTO loginForm, HttpServletRequest request) {
        return loginService.login(loginForm, request);
    }
    @Override
    public ResponseDTO<LoginDetailVO> getSession() {
        RequestTokenBO requestUser = RequestTokenUtil.getRequestUser();
        return ResponseDTO.succData(loginService.getSession(requestUser));
    }
    @Override
    public ResponseDTO<Boolean> logOut() {
        RequestTokenBO requestToken = RequestTokenUtil.getRequestUser();
        if (null == requestToken) {
            return ResponseDTO.wrap(LoginResponseCodeConst.LOGIN_ERROR);
        }
        return loginService.logoutByToken(requestToken);
    }

    @Override
    public ResponseDTO<KaptchaVO> verificationCode() {
        return loginService.verificationCode();
    }

}
