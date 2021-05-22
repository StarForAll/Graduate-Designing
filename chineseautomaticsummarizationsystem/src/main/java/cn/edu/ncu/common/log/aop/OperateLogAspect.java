package cn.edu.ncu.common.log.aop;

import cn.edu.ncu.common.annotation.OperateLog;
import cn.edu.ncu.common.constant.JudgeEnum;
import cn.edu.ncu.common.util.basic.StringUtil;
import cn.edu.ncu.common.util.web.RequestTokenUtil;
import cn.edu.ncu.dao.entity.UserOperateLog;
import cn.edu.ncu.pojo.bo.RequestTokenBO;
import cn.edu.ncu.service.LogService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Objects;
/**
 * @Author: XiongZhiCong
 * @Description: [  操作日志记录处理,对所有OperateLog注解的Controller进行操作日志监控 ]
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Slf4j
@Aspect
@Component
public class OperateLogAspect {

    @Autowired
    private LogService logService;

    @Pointcut("execution(* cn.edu.ncu.controller..*Controller.*(..)))")
    public void logPointCut() {
    }

    @AfterReturning(pointcut = "logPointCut()")
    public void doAfterReturning(JoinPoint joinPoint) {
        handleLog(joinPoint, null);
    }

    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            OperateLog operateLog = this.getAnnotationLog(joinPoint);
            if (operateLog == null) {
                return;
            }
            RequestTokenBO requestToken = RequestTokenUtil.getRequestUser();
            if (requestToken == null) {
                return;
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            String operateMethod = className + "." + methodName;
            Object[] args = joinPoint.getArgs();
            StringBuilder sb = new StringBuilder();
            for (Object obj : args) {
                //文件类型不进行序列化
                String simpleName = obj.getClass().getSimpleName();
                if(!"StandardMultipartFile".equals(simpleName)){
                    sb.append(simpleName);
                    sb.append("[");
                    sb.append(JSON.toJSONString(obj));
                    sb.append("]");
                }
            }
            String params = sb.toString();
            String failReason = null;
            Integer result = JudgeEnum.YES.getValue();
            if (e != null) {
                result = JudgeEnum.NO.getValue();
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw, true);
                e.printStackTrace(pw);
                failReason = sw.toString();
                pw.flush();
                pw.close();
                sw.flush();
                sw.close();
            }
            UserOperateLog operateLogEntity =
                UserOperateLog.builder().userId(requestToken.getRequestUserId()).userName(requestToken.getEmployeeBO().getActualName()).url(request.getRequestURI()).method(operateMethod).param(params).failReason(failReason).result(result).build();
            ApiOperation apiOperation = this.getApiOperation(joinPoint);
            if (apiOperation != null) {
                operateLogEntity.setContent(apiOperation.value());
            }
            Api api = this.getApi(joinPoint);
            if (api != null) {
                String[] tags = api.tags();
                operateLogEntity.setModule(StringUtil.join(tags, ","));
            }
            logService.addLog(operateLogEntity);
        } catch (Exception exp) {
            log.error("保存操作日志异常:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    private OperateLog getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        return AnnotationUtils.findAnnotation(method.getDeclaringClass(), OperateLog.class);
    }

    /**
     * swagger API
     *
     * @param joinPoint
     * @return
     * @throws Exception
     */
    private Api getApi(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Api classAnnotation = AnnotationUtils.findAnnotation(method.getDeclaringClass(), Api.class);

        if (method != null) {
            return classAnnotation;
        }
        return null;
    }

    /**
     * swagger ApiOperation
     *
     * @param joinPoint
     * @return
     * @throws Exception
     */
    private ApiOperation getApiOperation(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(ApiOperation.class);
        }
        return null;
    }

}
