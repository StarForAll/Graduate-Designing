package cn.edu.ncu.service;

import cn.edu.ncu.common.core.pojo.dto.PageParamDTO;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.vo.HeartBeatRecordVO;

/**
 * @Author: XiongZhiCong
 * @Description: 心跳服务
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public interface HeartBeatService  {



     ResponseDTO<PageResultDTO<HeartBeatRecordVO>> pageQuery(PageParamDTO pageParamDTO);
}
