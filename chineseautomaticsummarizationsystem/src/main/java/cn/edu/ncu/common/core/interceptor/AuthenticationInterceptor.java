package cn.edu.ncu.common.core.interceptor;

import cn.edu.ncu.common.annotation.NoNeedLogin;
import cn.edu.ncu.common.annotation.NoValidPrivilege;
import cn.edu.ncu.common.constant.CommonConst;
import cn.edu.ncu.common.constant.LoginResponseCodeConst;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.common.util.basic.StringUtil;
import cn.edu.ncu.common.util.web.RequestTokenUtil;
import cn.edu.ncu.pojo.bo.RequestTokenBO;
import cn.edu.ncu.service.LoginTokenService;
import cn.edu.ncu.service.PrivilegeEmployeeService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * [ 登录拦截器 ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2019 1024lab.netInc. All rights reserved.
 * @date
 * @since JDK1.8
 */
@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    public static final String TOKEN_NAME = "x-access-token";

    @Value("${access-control-allow-origin}")
    private String accessControlAllowOrigin;

    @Autowired
    private LoginTokenService loginTokenService;

    @Autowired
    private PrivilegeEmployeeService privilegeEmployeeService;

    /**
     * 拦截服务器端响应处理ajax请求返回结果
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //跨域设置
        this.crossDomainConfig(response);
        boolean isHandlerMethod = handler instanceof HandlerMethod;
        if (! isHandlerMethod) {
            return true;
        }

        //不需要登录的注解
        Boolean isNoNeedLogin = ((HandlerMethod) handler).getMethodAnnotation(NoNeedLogin.class) != null;
        if (isNoNeedLogin) {
            return true;
        }

        //放行的Uri前缀
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String target = uri.replaceFirst(contextPath, "");
        if (CommonConst.CommonCollection.contain(CommonConst.CommonCollection.IGNORE_URL, target)) {
            return true;
        }

        //需要做token校验, 消息头的token优先于请求query参数的token
        String xHeaderToken = request.getHeader(TOKEN_NAME);
        String xRequestToken = request.getParameter(TOKEN_NAME);
        String xAccessToken = null != xHeaderToken ? xHeaderToken : xRequestToken;
        if (null == xAccessToken) {
            this.outputResult(response, LoginResponseCodeConst.LOGIN_ERROR);
            return false;
        }

        //根据token获取登录用户
        RequestTokenBO requestToken = loginTokenService.getEmployeeTokenInfo(xAccessToken);
        if (null == requestToken) {
            this.outputResult(response, LoginResponseCodeConst.LOGIN_ERROR);
            return false;
        }

        //判断接口权限
        String methodName = ((HandlerMethod) handler).getMethod().getName();
        String className = ((HandlerMethod) handler).getBeanType().getName();
        List<String> list = StringUtil.splitConvertToList(className, "\\.");
        String controllerName = list.get(list.size() - 1);
        Method m = ((HandlerMethod) handler).getMethod();
        Class<?> cls = ((HandlerMethod) handler).getBeanType();
        boolean isClzAnnotation = cls.isAnnotationPresent(NoValidPrivilege.class);
        boolean isMethodAnnotation = m.isAnnotationPresent(NoValidPrivilege.class);
        NoValidPrivilege noValidPrivilege = null;
        if (isClzAnnotation) {
            noValidPrivilege = cls.getAnnotation(NoValidPrivilege.class);
        } else if (isMethodAnnotation) {
            noValidPrivilege = m.getAnnotation(NoValidPrivilege.class);
        }
        //不需验证权限
        if (noValidPrivilege != null) {
            RequestTokenUtil.setUser(request, requestToken);
            return true;
        }
        //需要验证权限
        Boolean privilegeValidPass = privilegeEmployeeService.checkEmployeeHavePrivilege(requestToken, controllerName, methodName);
        if (! privilegeValidPass) {
            this.outputResult(response, LoginResponseCodeConst.NOT_HAVE_PRIVILEGES);
            return false;
        }
        RequestTokenUtil.setUser(request, requestToken);
        return true;
    }

    /**
     * 配置跨域
     *
     * @param response
     */
    private void crossDomainConfig(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", accessControlAllowOrigin);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        response.setHeader("Access-Control-Expose-Headers", "*");
        response.setHeader("Access-Control-Allow-Headers", "Authentication,Origin, X-Requested-With, Content-Type, " + "Accept, x-access-token");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires ", "-1");
    }

    /**
     * 错误输出
     *
     * @param response
     * @param responseCodeConst
     * @throws IOException
     */
    private void outputResult(HttpServletResponse response, LoginResponseCodeConst responseCodeConst) throws IOException {
        ResponseDTO<Object> wrap = ResponseDTO.wrap(responseCodeConst);
        String msg = JSONObject.toJSONString(wrap);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(msg);
        response.flushBuffer();
    }
}
