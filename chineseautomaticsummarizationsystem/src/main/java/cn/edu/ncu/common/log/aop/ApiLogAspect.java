package cn.edu.ncu.common.log.aop;

import cn.edu.ncu.common.core.exception.BusinessException;
import cn.edu.ncu.common.constant.BusinessErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;

/**
 * @Author: XiongZhiCong
 * @Description: 实现ApiLog的功能，即打印请求日志和响应日志
 * @Date: Created in 14:20 2021/4/8
 * @Modified By:
 */
@Aspect
@Component
@Slf4j
public class ApiLogAspect {

    /**
     * 在控制层中有RestController注解的都会生效
     * @param: void
     * @return: void
     * @desc: 定义空方法用于切点表达式
     * @see
     * @since
     */
    @Pointcut("execution(* cn.edu.ncu.controller..*Controller.*(..)))")
    public void pointcut(){}

    /**
     * @param: [joinPoint]
     * @return: java.lang.Object
     * @desc: 返回信息后，打印应答报文的日志 ，增加了对分页方法的判断，如果是分页方法则增加对分页
     * 对象的调用。
     * @see
     * @since
     */
    @Around("pointcut()")
    public Object printResponseDatagram(ProceedingJoinPoint joinPoint) {
        Object result;
        try {
            // 方法执行前获取
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            String ip = getIpAddress(request);
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            Object[] args = joinPoint.getArgs ();
            Long requestStartTime=System.currentTimeMillis();
            result = joinPoint.proceed ();
            StringBuilder stringBuilder=new StringBuilder();
            long respTime = System.currentTimeMillis()-requestStartTime;
            String time = String.valueOf(respTime);
            log.info (stringBuilder.append("==> 拦截到请求\n").append("==> 请求者IP：").append(ip).append( "\n")
                    .append("==> 请求时间：").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())).append("\n")
                    .append( "==> 请求接口：").append(request.getMethod()).append(" ").append(request.getRequestURL()).append("\n")
                    .append( "==> 请求方法：").append(method.getName()).append("\n")
                    .append("==> 参数内容：").append(Arrays.toString(args))
                    .append("<== 请求耗时：").append(Double.parseDouble(time)/1000).append("s\n")
                    .append("<== 应答内容：").append( result )
                    .toString()
            );

            return result;
        } catch (Throwable throwable) {
            throw new BusinessException(BusinessErrorCode.LOG_AOP_RROR, throwable);
        }

    }

    /**
     * @param: [request]
     * @return: java.lang.String
     * @desc: 获取IP地址
     * @see
     * @since
     */
    private String getIpAddress(HttpServletRequest request){
        final String UNKNOWN = "unknown";
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
