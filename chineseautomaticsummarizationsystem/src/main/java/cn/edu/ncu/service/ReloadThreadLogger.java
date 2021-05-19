package cn.edu.ncu.service;

/**
 * ReloadThreadLogger 日志类
 */
public interface ReloadThreadLogger {

    void error(String string);

    void error(String string, Throwable e);

}
