package cn.edu.ncu.service.impl;

import cn.edu.ncu.common.constant.ResponseCodeConst;
import cn.edu.ncu.common.reload.ReloadManager;
import cn.edu.ncu.common.util.bean.BeanUtil;
import cn.edu.ncu.dao.entity.ReloadItemEntity;
import cn.edu.ncu.dao.entity.ReloadResult;
import cn.edu.ncu.dao.mapper.ReloadItemMapper;
import cn.edu.ncu.dao.mapper.ReloadResultMapper;
import cn.edu.ncu.service.ReloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.ReloadItemUpdateDTO;
import cn.edu.ncu.pojo.vo.ReloadItemVO;
import cn.edu.ncu.pojo.vo.ReloadResultVO;
/**
 * @Author: XiongZhiCong
 * @Description: reload业务
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Service
public class ReloadServiceImpl implements ReloadService {

    @Autowired
    private ReloadManager reloadManager;

    @Autowired
    private ReloadCommand reloadCommand;

    @Resource
    private ReloadItemMapper reloadItemDao;

    @Resource
    private ReloadResultMapper reloadResultDao;

    @Value("${reload.time-interval}")
    private Long timeInterval;

    @PostConstruct
    public void init() {
        reloadManager.addCommand(reloadCommand, 10, timeInterval, TimeUnit.SECONDS);
    }

    /**
     * 注册到Reload里
     */
    @Override
    public void register(Object reload) {
        reloadManager.register(reload);
    }

    /**
     * 获取所有 initDefines 项
     *
     * @return
     */
    @Override
    public ResponseDTO<List<ReloadItemVO>> listAllReloadItem() {
        List<ReloadItemEntity> reloadItemEntityList = reloadItemDao.selectAll();
        List<ReloadItemVO> reloadItemDTOList = BeanUtil.copyList(reloadItemEntityList, ReloadItemVO.class);
        return ResponseDTO.succData(reloadItemDTOList);
    }

    /**
     * 根据 tag
     * 获取所有 initDefines 运行结果
     *
     * @return ResponseDTO
     */
    @Override
    public ResponseDTO<List<ReloadResultVO>> listReloadItemResult(String tag) {
        ReloadResult query = new ReloadResult();
        query.setTag(tag);
        List<ReloadResult> reloadResultEntityList = reloadResultDao.query(tag);
        List<ReloadResultVO> reloadResultDTOList = BeanUtil.copyList(reloadResultEntityList, ReloadResultVO.class);
        return ResponseDTO.succData(reloadResultDTOList);
    }

    /**
     * 通过标签更新标识符
     *
     * @param updateDTO
     * @return
     */
    @Override
    public ResponseDTO<String> updateByTag(ReloadItemUpdateDTO updateDTO) {
        ReloadItemEntity entity = new ReloadItemEntity();
        entity.setTag(updateDTO.getTag());
        ReloadItemEntity reloadItemEntity = reloadItemDao.selectByPrimaryKey(entity.getTag());
        if (null == reloadItemEntity) {
            return ResponseDTO.wrap(ResponseCodeConst.NOT_EXISTS);
        }
        reloadItemEntity.setIdentification(updateDTO.getIdentification());
        reloadItemEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        reloadItemEntity.setArgs(updateDTO.getArgs());
        reloadItemDao.update(reloadItemEntity);
        return ResponseDTO.succ();
    }
}
