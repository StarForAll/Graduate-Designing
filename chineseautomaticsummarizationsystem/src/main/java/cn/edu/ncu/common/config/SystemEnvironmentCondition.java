package cn.edu.ncu.common.config;
import cn.edu.ncu.common.constant.SystemEnvironmentEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @Author: XiongZhiCong
 * @Description: 系统环境
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public class SystemEnvironmentCondition implements Condition {

    @Value("${spring.profiles.active}")
    private String systemEnvironment;

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        return ! SystemEnvironmentEnum.PROD.equalsValue(systemEnvironment);
    }
}
