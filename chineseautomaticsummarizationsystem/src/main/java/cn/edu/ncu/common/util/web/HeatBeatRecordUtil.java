package cn.edu.ncu.common.util.web;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Date;

/**
 * @Author: XiongZhiCong
 * @Description: 心跳工具类
 * @Date: Created in 9:12 2021/4/10
 * @Modified By:
 */
public class HeatBeatRecordUtil {
    /**
     * 获取进程号
     * @return
     */
    public static final Integer getProcessID() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return Integer.valueOf(runtimeMXBean.getName().split("@")[0])
                .intValue();
    }

    /**
     * 获取项目名称
     * @return
     */
    public static final String getProjectPath(){
        return System.getProperty("user.dir");
    }

    /**
     * 获取进程启动时间
     * @return
     */
    public static final Date getStartTime(){
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return new Date(runtimeMXBean.getStartTime());
    }

}
