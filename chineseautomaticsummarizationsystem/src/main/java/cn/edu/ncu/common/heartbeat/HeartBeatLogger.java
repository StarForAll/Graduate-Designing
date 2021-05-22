package cn.edu.ncu.common.heartbeat;
/**
 * @Author: XiongZhiCong
 * @Description:
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public interface HeartBeatLogger {

    void error(String string);

    void error(String string, Throwable e);

    void info(String string);
}
