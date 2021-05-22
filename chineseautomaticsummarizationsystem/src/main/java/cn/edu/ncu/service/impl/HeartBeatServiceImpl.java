package cn.edu.ncu.service.impl;

import cn.edu.ncu.common.config.HeartBeatConfig;
import cn.edu.ncu.common.core.pojo.dto.HeartBeatRecordDTO;
import cn.edu.ncu.common.core.pojo.dto.PageParamDTO;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.common.heartbeat.AbstractHeartBeatCommand;
import cn.edu.ncu.common.heartbeat.HeartBeatLogger;
import cn.edu.ncu.common.util.bean.BeanUtil;
import cn.edu.ncu.common.util.dao.IdGeneratorUtil;
import cn.edu.ncu.dao.entity.HeartBeatRecord;
import cn.edu.ncu.dao.mapper.HeartBeatRecordMapper;
import cn.edu.ncu.pojo.vo.HeartBeatRecordVO;
import cn.edu.ncu.service.HeartBeatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: 心跳范围业务
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Slf4j
@Service
public class HeartBeatServiceImpl extends AbstractHeartBeatCommand implements HeartBeatService {

    @Resource
    private HeartBeatRecordMapper heartBeatRecordMapper;

    @Autowired
    private HeartBeatConfig heartBeatConfig;
    @Autowired
    private IdGeneratorUtil idGeneratorUtil;
    @PostConstruct
    public void init() {

        cn.edu.ncu.common.heartbeat.HeartBeatConfig config = cn.edu.ncu.common.heartbeat.HeartBeatConfig.builder().delayHandlerTime(heartBeatConfig.getDelayHandlerTime()).intervalTime(heartBeatConfig.getIntervalTime()).build();

        super.init(config, new HeartBeatLogger() {
            @Override
            public void error(String string) {
                log.error(string);
            }

            @Override
            public void error(String string, Throwable e) {
                log.error(string, e);
            }

            @Override
            public void info(String string) {
                log.info(string);
            }
        });
    }

    @PreDestroy
    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void handler(HeartBeatRecordDTO heartBeatRecordDTO) {
        HeartBeatRecord heartBeatRecordEntity = BeanUtil.copy(heartBeatRecordDTO, HeartBeatRecord.class);
        HeartBeatRecord heartBeatRecordOld = heartBeatRecordMapper.query(heartBeatRecordEntity);
        if (heartBeatRecordOld == null) {
            if(heartBeatRecordEntity.getId()==null){
                heartBeatRecordEntity.setId(idGeneratorUtil.snowflakeId());
            }
            heartBeatRecordMapper.insertHeartBeat(heartBeatRecordEntity);
        } else {
            heartBeatRecordMapper.updateHeartBeatTimeById(heartBeatRecordOld.getId(), heartBeatRecordEntity.getHeartBeatTime());
        }

    }

    @Override
    public ResponseDTO<PageResultDTO<HeartBeatRecordVO>> pageQuery(PageParamDTO pageParamDTO) {
        PageResultDTO<HeartBeatRecordVO> pageResultDTO =new PageResultDTO<>();
        int total=0;
        Boolean searchCount = pageParamDTO.getSearchCount();
        if(searchCount ==null||searchCount){
            total=heartBeatRecordMapper.pageQueryCount(pageParamDTO);
        }
        long pages=total/pageParamDTO.getPageSize()+1;
        pageResultDTO.setPageNum(Long.valueOf(pageParamDTO.getPageNum()));
        pageResultDTO.setPages(pages);
        pageResultDTO.setTotal((long) total);
        pageResultDTO.setPageSize(Long.valueOf(pageParamDTO.getPageSize()));
        List<HeartBeatRecordVO> recordVOList = heartBeatRecordMapper.pageQuery(pageParamDTO);
        pageResultDTO.setList(recordVOList);
        return ResponseDTO.succData(pageResultDTO);

    }
}
