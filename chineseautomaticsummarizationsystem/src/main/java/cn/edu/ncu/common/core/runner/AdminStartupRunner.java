package cn.edu.ncu.common.core.runner;

import cn.edu.ncu.common.constant.ResponseCodeConst;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 应用启动以后检测code码
 *
 * @author zhuo
 * @version 1.0
 * @since JDK1.8
 */

@Component
public class AdminStartupRunner implements CommandLineRunner {

    @Override
    public void run(String... args) {
        ResponseCodeConst.init();
    }
}