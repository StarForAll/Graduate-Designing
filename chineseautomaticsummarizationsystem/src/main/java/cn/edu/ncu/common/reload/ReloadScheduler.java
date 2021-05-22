package cn.edu.ncu.common.reload;


import cn.edu.ncu.service.ReloadCommandInterface;
import cn.edu.ncu.service.ReloadThreadLogger;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Reload 调度器
 *
 */
public class ReloadScheduler {

    private ScheduledThreadPoolExecutor executor;

    private ReloadThreadLogger logger;

    ReloadScheduler(ReloadThreadLogger logger, int threadCount) {
        this.executor = new ScheduledThreadPoolExecutor(threadCount, new ReloadSchedulerThreadFactory());
        this.logger = logger;
    }

    void shutdown() {
        try {
            executor.shutdown();
        } catch (Throwable e) {
            logger.error("<<ReloadScheduler>> shutdown ", e);
        }
    }

    void addCommand(ReloadCommandInterface command, long initialDelay, long delay, TimeUnit unit) {
        executor.scheduleWithFixedDelay(new ScheduleRunnable(command, this.logger), initialDelay, delay, unit);
    }

    static class ScheduleRunnable implements Runnable {

        private ReloadCommandInterface command;

        private ReloadThreadLogger logger;

        public ScheduleRunnable(ReloadCommandInterface command, ReloadThreadLogger logger) {
            this.command = command;
            this.logger = logger;
        }

        @Override
        public void run() {
            try {
                command.doTask();
            } catch (Throwable e) {
                logger.error("", e);
            }
        }
    }

    static class ReloadSchedulerThreadFactory implements ThreadFactory {

        private static final AtomicInteger poolNumber = new AtomicInteger(1);

        private final ThreadGroup group;

        private final AtomicInteger threadNumber = new AtomicInteger(1);

        private final String namePrefix;

        ReloadSchedulerThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "reload-" + poolNumber.getAndIncrement() + "-thread-";
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

}
