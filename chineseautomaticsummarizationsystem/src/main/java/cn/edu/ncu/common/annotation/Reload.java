package cn.edu.ncu.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @Author: XiongZhiCong
 * @Description: 定义 Reload 注解
 * @Date: Created in 14:16 2021/4/8
 * @Modified By:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Reload {

    String value();
}
