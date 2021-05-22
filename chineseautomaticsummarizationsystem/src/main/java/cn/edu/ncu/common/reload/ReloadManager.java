package cn.edu.ncu.common.reload;


import cn.edu.ncu.common.annotation.Reload;
import cn.edu.ncu.dao.entity.ReloadItem;
import cn.edu.ncu.dao.entity.ReloadResult;
import cn.edu.ncu.pojo.object.AbstractReloadObject;
import cn.edu.ncu.pojo.object.AnnotationReloadObject;
import cn.edu.ncu.pojo.object.InterfaceReloadObject;
import cn.edu.ncu.service.ReloadCommandInterface;
import cn.edu.ncu.service.ReloadThreadLogger;
import cn.edu.ncu.service.Reloadable;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.requireNonNull;

/**
 * @Author: XiongZhiCong
 * @Description: ReloadManager 管理器；可以在此类中添加 检测任务 以及注册 处理程序
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public class ReloadManager {

    private Map<String, AbstractReloadObject> tagReloadObject;

    private ReloadScheduler reloadScheduler;

    private ReloadThreadLogger logger;

    public ReloadManager(ReloadThreadLogger logger, int threadCount) {
        this.tagReloadObject = new ConcurrentHashMap<>();
        if (logger == null) {
            throw new ExceptionInInitializerError("ReloadLoggerImp cannot be null");
        }

        if (threadCount < 1) {
            throw new ExceptionInInitializerError("threadCount must be greater than 1");
        }

        this.logger = logger;
        this.reloadScheduler = new ReloadScheduler(this.logger, threadCount);
    }

    /**
     * 默认创建单个线程
     *
     * @param logger
     */
    public ReloadManager(ReloadThreadLogger logger) {
        this(logger, 1);
    }

    /**
     * 停止
     */
    public void shutdown() {
        reloadScheduler.shutdown();
    }

    /**
     * 添加任务
     *
     * @param command      ReloadCommand实现类
     * @param initialDelay 第一次执行前的延迟时间
     * @param delay        任务间隔时间
     * @param unit         延迟单位 TimeUnit 天、小时、分、秒等
     */
    public void addCommand(ReloadCommandInterface command, long initialDelay, long delay, TimeUnit unit) {
        reloadScheduler.addCommand(command, initialDelay, delay, unit);
    }

    /**
     * 注册  实现接口的方式
     *
     * @param tag
     * @param reloadable
     */
    public void register(String tag, Reloadable reloadable) {
        requireNonNull(reloadable);
        requireNonNull(tag);
        if (tagReloadObject.containsKey(tag)) {
            logger.error("<<ReloadManager>> register duplicated tag reload : " + tag + " , and it will be cover!");
        }
        tagReloadObject.put(tag, new InterfaceReloadObject(reloadable));
    }

    /**
     * 注册 要求此类必须包含使用了Reload注解的方法
     *
     * @param reloadObject
     */
    public void register(Object reloadObject) {
        requireNonNull(reloadObject);
        Method[] declaredMethods = reloadObject.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            Reload annotation = method.getAnnotation(Reload.class);
            if (annotation != null) {
                String reloadTag = annotation.value();
                this.register(reloadTag, new AnnotationReloadObject(reloadObject, method));
            }
        }
    }

    private void register(String tag, AbstractReloadObject reloadObject) {
        if (tagReloadObject.containsKey(tag)) {
            logger.error("<<ReloadManager>> register duplicated tag reload : " + tag + " , and it will be cover!");
        }
        tagReloadObject.put(tag, reloadObject);
    }

    /**
     * Reload 已注册的ReloadItem
     *
     * @param reloadItem
     * @return ReloadResult
     */
    public ReloadResult doReload(ReloadItem reloadItem) {
        AbstractReloadObject reloadObject = tagReloadObject.get(reloadItem.getTag());
        if (reloadObject != null) {
            return reloadObject.reload(reloadItem);
        }
        // 返回注册结果
        return new ReloadResult(reloadItem.getTag(), reloadItem.getArgs(), reloadItem.getIdentification(), false, "No registered reload handler was found");
    }

}
