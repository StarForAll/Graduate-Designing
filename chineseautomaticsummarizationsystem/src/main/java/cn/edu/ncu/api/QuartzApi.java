package cn.edu.ncu.api;

import cn.edu.ncu.common.annotation.NoValidPrivilege;
import cn.edu.ncu.common.annotation.OperateLog;
import cn.edu.ncu.common.constant.SwaggerTagConst;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.QuartzLogQueryDTO;
import cn.edu.ncu.pojo.dto.QuartzQueryDTO;
import cn.edu.ncu.pojo.dto.QuartzTaskDTO;
import cn.edu.ncu.pojo.vo.QuartzTaskLogVO;
import cn.edu.ncu.pojo.vo.QuartzTaskVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 
 * [  ]
 * 
 * @version 1.0
 * @since JDK1.8
 * @author yandanyang
 * @company 1024lab.net
 * @copyright (c) 2019 1024lab.netInc. All rights reserved.
 * @date  
 */
@OperateLog
@Api(tags = {SwaggerTagConst.Admin.MANAGER_TASK_SCHEDULER})
public interface QuartzApi {


    @PostMapping("/quartz/task/query")
    @ApiOperation(value = "查询任务")
    @NoValidPrivilege
    ResponseDTO<PageResultDTO<QuartzTaskVO>> query(@RequestBody @Valid QuartzQueryDTO queryDTO);


    @PostMapping("/quartz/task/queryLog")
    @ApiOperation(value = "查询任务运行日志")
    @NoValidPrivilege
     ResponseDTO<PageResultDTO<QuartzTaskLogVO>> queryLog(@RequestBody @Valid QuartzLogQueryDTO queryDTO);

    @PostMapping("/quartz/task/saveOrUpdate")
    @ApiOperation(value = "新建更新任务")
     ResponseDTO<String> saveOrUpdateTask(@RequestBody @Valid QuartzTaskDTO quartzTaskDTO)throws Exception;

    @GetMapping("/quartz/task/run/{taskId}")
    @ApiOperation(value = "立即运行某个任务")
     ResponseDTO<String> runTask(@PathVariable("taskId") Long taskId)throws Exception;

    @GetMapping("/quartz/task/pause/{taskId}")
    @ApiOperation(value = "暂停某个任务")
     ResponseDTO<String> pauseTask(@PathVariable("taskId")Long taskId)throws Exception;

    @GetMapping("/quartz/task/resume/{taskId}")
    @ApiOperation(value = "恢复某个任务")
     ResponseDTO<String> resumeTask(@PathVariable("taskId")Long taskId)throws Exception;

    @GetMapping("/quartz/task/delete/{taskId}")
    @ApiOperation(value = "删除某个任务")
     ResponseDTO<String> deleteTask(@PathVariable("taskId")Long taskId)throws Exception;
}
