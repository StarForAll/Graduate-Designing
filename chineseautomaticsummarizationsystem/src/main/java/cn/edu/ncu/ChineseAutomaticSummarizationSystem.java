package cn.edu.ncu;

import cn.edu.ncu.common.core.dao.CommonMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: XiongZhiCong
 * @Description: 启动类
 * ,excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {CommonMapper.class})
 * @Date: Created in 16:51 2021/4/8
 * @Modified By:
 */
@SpringBootApplication(scanBasePackages = {"cn.edu.ncu", "cn.afterturn.easypoi"})
//@ComponentScan(basePackages = {"cn.edu.ncu.*"})
@EnableCaching
@EnableSwagger2
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class ChineseAutomaticSummarizationSystem {
    public static void main(String[] args) {
        SpringApplication.run(ChineseAutomaticSummarizationSystem.class,args);
    }
}
