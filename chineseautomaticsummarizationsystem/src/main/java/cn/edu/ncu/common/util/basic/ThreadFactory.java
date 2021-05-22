package cn.edu.ncu.common.util.basic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: XiongZhiCong
 * @Description: 拥有自己的thread facotry是为了jstack时候能看到是哪个线程
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public class ThreadFactory implements java.util.concurrent.ThreadFactory {

    public static ThreadFactory create(String namePrefix) {
        return new ThreadFactory(namePrefix);
    }

    private final AtomicInteger poolNumber = new AtomicInteger(1);

    private final ThreadGroup group;

    private final AtomicInteger threadNumber = new AtomicInteger(1);

    private final String namePrefix;

    private ThreadFactory(String namePrefix) {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        this.namePrefix = namePrefix + " pool " + poolNumber.getAndIncrement() + "-thread-";
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
        if (t.isDaemon()) {
            t.setDaemon(false);
        }

        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }

}
