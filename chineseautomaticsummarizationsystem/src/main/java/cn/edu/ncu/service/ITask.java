package cn.edu.ncu.service;

/**
 * @Author: XiongZhiCong
 * @Description: 定时任务接口
 * @Date: Created in 14:16 2021/4/8
 * @Modified By:
 */
public interface ITask {

    void execute(String paramJson) throws Exception;
}