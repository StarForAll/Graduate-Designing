package cn.edu.ncu.pojo.object;


import cn.edu.ncu.common.annotation.Reload;
import cn.edu.ncu.dao.entity.ReloadItem;
import cn.edu.ncu.dao.entity.ReloadResult;

import java.lang.reflect.Method;

/**
 * @Author: XiongZhiCong
 * @Description: Reload 处理程序的实现类 用于包装以注解 Reload 实现的处理类
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public class AnnotationReloadObject extends AbstractReloadObject {

    private Object reloadObject;

    private Method method;

    public AnnotationReloadObject(Object reloadObject, Method method) {
        super();
        this.reloadObject = reloadObject;
        this.method = method;
        this.method.setAccessible(true);
    }

    @Override
    public ReloadResult reload(ReloadItem reloadItem) {
        ReloadResult result = new ReloadResult();
        String tag = method.getAnnotation(Reload.class).value();
        result.setTag(tag);
        result.setArgs(reloadItem.getArgs());
        result.setIdentification(reloadItem.getIdentification());
        try {
            Object invoke = method.invoke(reloadObject, reloadItem.getArgs());
            result.setResult((Boolean) invoke);
        } catch (Throwable e) {
            result.setException(getStackTrace(e));
        }
        return result;
    }

}
