package cn.edu.ncu.task;

import cn.edu.ncu.service.ThesaurusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Author: XiongZhiCong
 * @Description: 服务启动时加载词库到缓存中去
 * @Date: Created in 23:08 2021/4/8
 * @Modified By:
 */
@Component
@Slf4j
public class LoadWordTask {
    @Resource
    private ThesaurusService thesaurusService;
    @PostConstruct
    public void init(){
        log.info("项目启动时初始化加载词库...");
        thesaurusService.loadCache();
    }
}
