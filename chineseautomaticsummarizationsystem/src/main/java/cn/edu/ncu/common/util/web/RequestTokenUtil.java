package cn.edu.ncu.common.util.web;

import cn.edu.ncu.pojo.bo.RequestTokenBO;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: XiongZhiCong
 * @Description: 请求Token工具
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public class RequestTokenUtil {

    private static final String USER_KEY = "user_token";

    private static ThreadLocal<RequestTokenBO> RequestUserThreadLocal = new ThreadLocal<>();

    public static void setUser(HttpServletRequest request, RequestTokenBO requestToken) {
        request.setAttribute(USER_KEY, requestToken);
        RequestUserThreadLocal.set(requestToken);
    }

    public static RequestTokenBO getThreadLocalUser() {
        return RequestUserThreadLocal.get();
    }

    public static RequestTokenBO getRequestUser() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            if (request != null) {
                return (RequestTokenBO) request.getAttribute(USER_KEY);
            }
        }
        return null;
    }

    public static Long getRequestUserId() {
        RequestTokenBO requestUser = getRequestUser();
        if (null == requestUser) {
            return null;
        }
        return requestUser.getRequestUserId();
    }

}
