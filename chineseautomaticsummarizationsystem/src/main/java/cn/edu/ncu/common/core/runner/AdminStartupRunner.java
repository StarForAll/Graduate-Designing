package cn.edu.ncu.common.core.runner;

import cn.edu.ncu.common.constant.ResponseCodeConst;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Author: XiongZhiCong
 * @Description: 应用启动以后检测code码
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Component
public class AdminStartupRunner implements CommandLineRunner {

    @Override
    public void run(String... args) {
        ResponseCodeConst.init();
    }
}