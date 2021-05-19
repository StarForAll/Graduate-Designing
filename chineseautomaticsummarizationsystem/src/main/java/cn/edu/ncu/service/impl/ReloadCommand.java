package cn.edu.ncu.service.impl;

import cn.edu.ncu.common.util.bean.BeanUtil;
import cn.edu.ncu.common.util.dao.IdGeneratorUtil;
import cn.edu.ncu.dao.entity.*;
import cn.edu.ncu.dao.entity.ReloadResult;
import cn.edu.ncu.dao.mapper.ReloadItemMapper;
import cn.edu.ncu.dao.mapper.ReloadResultMapper;
import cn.edu.ncu.service.AbstractReloadCommand4Spring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *  Reload 业务
 *
 * @author listen
 * @date 2018/02/10 09:18
 */
@Component
public class ReloadCommand extends AbstractReloadCommand4Spring {

    @Resource
    private ReloadItemMapper reloadItemMapper;

    @Resource
    private ReloadResultMapper reloadResultMapper;
    /**
     * 读取数据库中Reload项
     *
     * @return List<ReloadItem>
     */
    @Override
    public List<ReloadItem> readReloadItem() {
        List<ReloadItemEntity> reloadItemEntityList = reloadItemMapper.selectAll();
        return BeanUtil.copyList(reloadItemEntityList, ReloadItem.class);
    }

    /**
     * 保存reload结果
     * 无主键
     * @param reloadResult
     */
    @Override
    public void handleReloadResult(ReloadResult reloadResult) {
        ReloadResult reloadResultEntity = BeanUtil.copy(reloadResult, ReloadResult.class);
        if(reloadResultEntity.getCreateTime()==null){
            reloadResultEntity.setCreateTime(new Date(System.currentTimeMillis()));
        }
        reloadResultMapper.insert(reloadResultEntity);
    }
}
