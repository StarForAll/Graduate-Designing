package cn.edu.ncu.common.annotation;

import java.lang.annotation.*;

/**
 * @Author: XiongZhiCong
 * @Description: 用户操作日志
 * @Date: Created in 14:16 2021/4/8
 * @Modified By:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface OperateLog {

}
