package cn.edu.ncu.service;


import cn.edu.ncu.common.reload.ReloadManager;
import cn.edu.ncu.dao.entity.ReloadItem;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: XiongZhiCong
 * @Description: 检测是否 Reload 的类
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public abstract class AbstractReloadCommand implements ReloadCommandInterface {

    /**
     * 当前ReloadItem的存储器
     */
    private ConcurrentHashMap<String, String> currentTags = null;

    /**
     * Reload的执行类
     */
    private ReloadManager reloadManager;

    public AbstractReloadCommand(ReloadManager reloadManager) {
        this.reloadManager = reloadManager;
        this.currentTags = new ConcurrentHashMap<>();
        // 初始获取ReloadItem数据
        List<ReloadItem> readTagStatesFromDb = readReloadItem();
        if (readTagStatesFromDb != null) {
            for (ReloadItem reloadItem : readTagStatesFromDb) {
                String tag = reloadItem.getTag();
                String tagChangeIdentifier = reloadItem.getIdentification();
                this.currentTags.put(tag, tagChangeIdentifier);
            }
        }
    }
    /**
     * 任务：
     * 读取数据库中 ReloadItem 数据
     * 校验是否发生变化
     * 执行重加载动作
     */
    @Override
    public void doTask() {
        // 获取数据库数据
        List<ReloadItem> readTagStatesFromDb = readReloadItem();
        String tag;
        String tagIdentifier;
        String preTagChangeIdentifier;
        for (ReloadItem reloadItem : readTagStatesFromDb) {
            tag = reloadItem.getTag();
            tagIdentifier = reloadItem.getIdentification();
            preTagChangeIdentifier = currentTags.get(tag);
            // 数据不一致
            if (preTagChangeIdentifier == null || ! preTagChangeIdentifier.equals(tagIdentifier)) {
                // 更新map数据
                currentTags.put(tag, tagIdentifier);
                // 执行重新加载此项的动作
                handleReloadResult(this.reloadManager.doReload(reloadItem));
            }
        }
    }
}
