package cn.edu.ncu.common.heartbeat;

import cn.edu.ncu.common.core.pojo.dto.HeartBeatRecordDTO;
import cn.edu.ncu.common.util.basic.StringUtil;
import cn.edu.ncu.common.util.web.HeatBeatRecordUtil;
import cn.edu.ncu.common.util.web.IpUtil;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.Date;
import java.util.List;
import java.util.concurrent.*;


/**
* @Description: 心跳服务
* @Author: simajinqiang
* @Date: 2018/7/9 16:26
*/
public abstract class AbstractHeartBeatCommand implements HeartBeatRecordCommendInterface {


    ScheduledExecutorService executorService;

    int threadNum = 1;

    /**
     * 项目路径
     */
    private String projectPath;
    /**
     * 服务器ip（多网卡）
     */
    private List<String> serverIps;
    /**
     * 进程号
     */
    private Integer processNo;
    /**
     * 进程开启时间
     */
    private Date processStartTime;

    HeartBeatLogger logger;

    /**
     * 初始化
     */
    public void init(HeartBeatConfig config, HeartBeatLogger logger){
        this.handlerHeartBeat();
        executorService = new ScheduledThreadPoolExecutor(threadNum,
                new BasicThreadFactory.Builder().namingPattern("AbstractHeartBeatCommand-%d").daemon(true).build());

        executorService.scheduleWithFixedDelay(new DoHeartBeat(), config.getDelayHandlerTime(), config.getIntervalTime(),
                TimeUnit.MILLISECONDS);
    }

    public void handlerHeartBeat(){
        try {
            projectPath = HeatBeatRecordUtil.getProjectPath();
            serverIps = IpUtil.getLocalIPS();
            processNo = HeatBeatRecordUtil.getProcessID();
            processStartTime = HeatBeatRecordUtil.getStartTime();
        }catch (Throwable e){
            logger.error("get heart beat info error.", e);
        }
    }

    /**
     * 销毁线程池
     */
    public void destroy(){
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
            executorService = null;
        }
    }

    public class DoHeartBeat implements Runnable{

        @Override
        public void run() {
            try {
                HeartBeatRecordDTO heartBeatRecord = new HeartBeatRecordDTO();
                heartBeatRecord.setProjectPath(projectPath);
                heartBeatRecord.setServerIp(StringUtil.join(serverIps,";"));
                heartBeatRecord.setProcessNo(processNo);
                heartBeatRecord.setProcessStartTime(processStartTime);
                heartBeatRecord.setHeartBeatTime(new Date());
                handler(heartBeatRecord);
            }catch (Throwable t){
                logger.error("handler heartbeat error.", t);
            }

        }
    }

}
