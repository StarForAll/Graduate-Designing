package cn.edu.ncu.controller;

import cn.edu.ncu.api.QuartzApi;
import cn.edu.ncu.service.QuartzTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.QuartzLogQueryDTO;
import cn.edu.ncu.pojo.dto.QuartzQueryDTO;
import cn.edu.ncu.pojo.dto.QuartzTaskDTO;
import cn.edu.ncu.pojo.vo.QuartzTaskLogVO;
import cn.edu.ncu.pojo.vo.QuartzTaskVO;
/**
 * @Author: XiongZhiCong
 * @Description: 定时任务
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@RestController
public class QuartzController implements QuartzApi {

    @Autowired
    private QuartzTaskService quartzTaskService;

    @Override
    public ResponseDTO<PageResultDTO<QuartzTaskVO>> query(@RequestBody @Valid QuartzQueryDTO queryDTO){
        return quartzTaskService.query(queryDTO);
    }

    @Override
    public ResponseDTO<PageResultDTO<QuartzTaskLogVO>> queryLog(@RequestBody @Valid QuartzLogQueryDTO queryDTO){
        return quartzTaskService.queryLog(queryDTO);
    }
    @Override
    public ResponseDTO<String> saveOrUpdateTask(@RequestBody @Valid QuartzTaskDTO quartzTaskDTO)throws Exception{
        return quartzTaskService.saveOrUpdateTask(quartzTaskDTO);
    }
    @Override
    public ResponseDTO<String> runTask(@PathVariable("taskId") Long taskId)throws Exception{
        return quartzTaskService.runTask(taskId);
    }
    @Override
    public ResponseDTO<String> pauseTask(@PathVariable("taskId")Long taskId)throws Exception{
        return quartzTaskService.pauseTask(taskId);
    }
    @Override
    public ResponseDTO<String> resumeTask(@PathVariable("taskId")Long taskId)throws Exception{
        return quartzTaskService.resumeTask(taskId);
    }

    @Override
    public ResponseDTO<String> deleteTask(@PathVariable("taskId")Long taskId)throws Exception{
        return quartzTaskService.deleteTask(taskId);
    }
}
