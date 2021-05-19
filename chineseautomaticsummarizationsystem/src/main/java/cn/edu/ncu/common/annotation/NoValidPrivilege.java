package cn.edu.ncu.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Author: XiongZhiCong
 * @Description: 不需要权限验证
 * @Date: Created in 14:16 2021/4/8
 * @Modified By:
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface NoValidPrivilege {

}
