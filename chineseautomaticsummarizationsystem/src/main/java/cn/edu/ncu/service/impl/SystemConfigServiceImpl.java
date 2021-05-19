package cn.edu.ncu.service.impl;

import cn.edu.ncu.common.annotation.Reload;
import cn.edu.ncu.common.constant.*;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.common.util.bean.BeanUtil;
import cn.edu.ncu.common.util.dao.IdGeneratorUtil;
import cn.edu.ncu.dao.entity.SystemConfig;
import cn.edu.ncu.dao.mapper.SystemConfigMapper;
import cn.edu.ncu.pojo.dto.SystemConfigAddDTO;
import cn.edu.ncu.pojo.dto.SystemConfigDTO;
import cn.edu.ncu.pojo.dto.SystemConfigQueryDTO;
import cn.edu.ncu.pojo.dto.SystemConfigUpdateDTO;
import cn.edu.ncu.pojo.vo.SystemConfigVO;
import cn.edu.ncu.service.ReloadService;
import cn.edu.ncu.service.SystemConfigService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * 系统配置业务类
 *
 * @author GHQ
 * @date 2017-12-23 15:09
 */
@Slf4j
@Service
public class SystemConfigServiceImpl implements SystemConfigService {

    /**
     * 系统配置缓存
     */
    private ConcurrentHashMap<String, SystemConfig> systemConfigMap = new ConcurrentHashMap<>();
    @Resource
    private IdGeneratorUtil idGeneratorUtil;
    @Resource
    private SystemConfigMapper systemConfigMapper;

    @Autowired
    private ReloadService reloadService;

    /**
     * 初始化系统设置缓存
     */
    @PostConstruct
    private void initSystemConfigCache() {
        List<SystemConfig> entityList = systemConfigMapper.selectAll();
        if (CollectionUtils.isEmpty(entityList)) {
            return;
        }

        systemConfigMap.clear();
        entityList.forEach(entity -> this.systemConfigMap.put(entity.getConfigKey().toLowerCase(), entity));
        log.info("系统设置缓存初始化完毕:{}", systemConfigMap.size());

        reloadService.register(this);
    }

    @Reload(ReloadTagConst.SYSTEM_CONFIG)
    public boolean reload(String args) {
        this.initSystemConfigCache();
        log.warn("<<Reload>> <<{}>> , args {} reload success ", ReloadTagConst.SYSTEM_CONFIG, args);
        return true;
    }

    /**
     * 分页获取系统配置
     *
     * @param queryDTO
     * @return
     */
    @Override
    public ResponseDTO<PageResultDTO<SystemConfigVO>> getSystemConfigPage(SystemConfigQueryDTO queryDTO) {
        PageResultDTO<SystemConfigVO> pageResultDTO=new PageResultDTO<>();
        int total=0;
        Boolean searchCount = queryDTO.getSearchCount();
        if(searchCount ==null||searchCount){
            total=systemConfigMapper.queryCount(queryDTO);
        }
        long pages=total/queryDTO.getPageSize()+1;
        pageResultDTO.setPageNum(Long.valueOf(queryDTO.getPageNum()));
        pageResultDTO.setPages(pages);
        pageResultDTO.setTotal((long) total);
        pageResultDTO.setPageSize(Long.valueOf(queryDTO.getPageSize()));
        if(queryDTO.getKey() != null){
            queryDTO.setKey(queryDTO.getKey().toLowerCase());
        }
        List<SystemConfig> entityList = systemConfigMapper.selectSystemSettingList(queryDTO);
        pageResultDTO.setList(BeanUtil.copyList(entityList,SystemConfigVO.class));
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * 根据参数key获得一条数据（数据库）
     *
     * @param configKey
     * @return
     */
    @Override
    public ResponseDTO<SystemConfigVO> selectByKey(String configKey) {
        if(configKey != null){
            configKey = configKey.toLowerCase();
        }
        SystemConfig entity = systemConfigMapper.getByKey(configKey);
        if (entity == null) {
            return ResponseDTO.wrap(SystemConfigResponseCodeConst.NOT_EXIST);
        }
        SystemConfigVO configDTO = BeanUtil.copy(entity, SystemConfigVO.class);
        return ResponseDTO.succData(configDTO);
    }

    /**
     * 根据参数key获得一条数据 并转换为 对象
     *
     * @param configKey
     * @param clazz
     * @param <T>
     * @return
     */
    @Override
    public <T> T selectByKey2Obj(String configKey, Class<T> clazz) {
        if(configKey != null){
            configKey = configKey.toLowerCase();
        }
        SystemConfig entity = systemConfigMapper.getByKey(configKey);
        if (entity == null) {
            return null;
        }
        SystemConfigDTO configDTO = BeanUtil.copy(entity, SystemConfigDTO.class);
        String configValue = configDTO.getConfigValue();
        if (StringUtils.isEmpty(configValue)) {
            return null;
        }
        T obj = JSON.parseObject(configValue, clazz);
        return obj;
    }
    @Override
    public SystemConfigDTO getCacheByKey(SystemConfigEnum.Key key) {
        SystemConfig entity = this.systemConfigMap.get(key.name().toLowerCase());
        if (entity == null) {
            return null;
        }
        return BeanUtil.copy(entity, SystemConfigDTO.class);
    }

    /**
     * 添加系统配置
     *
     * @param configAddDTO
     * @return
     */
    @Override
    public ResponseDTO<String> addSystemConfig(SystemConfigAddDTO configAddDTO) {
        configAddDTO.setConfigKey(configAddDTO.getConfigKey().toLowerCase());
        SystemConfig entity = systemConfigMapper.getByKey(configAddDTO.getConfigKey());
        if (entity != null) {
            return ResponseDTO.wrap(SystemConfigResponseCodeConst.ALREADY_EXIST);
        }
        ResponseDTO valueValid = this.configValueValid(configAddDTO.getConfigKey(),configAddDTO.getConfigValue());
        if(!valueValid.isSuccess()){
            return valueValid;
        }
        configAddDTO.setConfigKey(configAddDTO.getConfigKey().toLowerCase());
        SystemConfig addEntity = BeanUtil.copy(configAddDTO, SystemConfig.class);
        addEntity.setId(idGeneratorUtil.snowflakeId());
        addEntity.setIsUsing(JudgeEnum.YES.getValue());
        Date date=new Date(System.currentTimeMillis());
        if(addEntity.getUpdateTime()==null){
            addEntity.setUpdateTime(date);
        }
        if(addEntity.getCreateTime()==null){
            addEntity.setCreateTime(date);
        }
        systemConfigMapper.insert(addEntity);
        //刷新缓存
        this.initSystemConfigCache();
        return ResponseDTO.succ();
    }

    /**
     * 更新系统配置
     *
     * @param updateDTO
     * @return
     */
    @Override
    public ResponseDTO<String> updateSystemConfig(SystemConfigUpdateDTO updateDTO) {
        updateDTO.setConfigKey(updateDTO.getConfigKey().toLowerCase());
        SystemConfig entity = systemConfigMapper.selectByPrimaryKey(updateDTO.getId());
        //系统配置不存在
        if (entity == null) {
            return ResponseDTO.wrap(SystemConfigResponseCodeConst.NOT_EXIST);
        }
        SystemConfig alreadyEntity = systemConfigMapper.getByKeyExcludeId(updateDTO.getConfigKey().toLowerCase(), updateDTO.getId());
        if (alreadyEntity != null) {
            return ResponseDTO.wrap(SystemConfigResponseCodeConst.ALREADY_EXIST);
        }
        ResponseDTO valueValid = this.configValueValid(updateDTO.getConfigKey(),updateDTO.getConfigValue());
        if(!valueValid.isSuccess()){
            return valueValid;
        }
        entity = BeanUtil.copy(updateDTO, SystemConfig.class);
        updateDTO.setConfigKey(updateDTO.getConfigKey().toLowerCase());
        systemConfigMapper.update(entity);

        //刷新缓存
        this.initSystemConfigCache();
        return ResponseDTO.succ();
    }


    private ResponseDTO<String> configValueValid(String configKey , String configValue){
        SystemConfigEnum.Key configKeyEnum = SystemConfigEnum.Key.selectByKey(configKey);
        if(configKeyEnum == null){
            return ResponseDTO.succ();
        }
        SystemConfigDataType dataType = configKeyEnum.getDataType();
        if(dataType == null){
            return ResponseDTO.succ();
        }
        if(dataType.name().equals(SystemConfigDataType.TEXT.name())){
            return ResponseDTO.succ();
        }
        if(dataType.name().equals(SystemConfigDataType.JSON.name())){
            try {
                JSONObject jsonStr = JSONObject.parseObject(configValue);
                return ResponseDTO.succ();
            } catch (Exception e) {
                return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM,"数据格式不是JSON,请修改后提交。");
            }
        }
        if(StringUtils.isNotEmpty(dataType.getValid())){
            Boolean valid = Pattern.matches(dataType.getValid(), configValue);
            if(valid){
                return ResponseDTO.succ();
            }
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM,"数据格式不是"+dataType.name().toLowerCase()+",请修改后提交。");
        }

        return ResponseDTO.succ();
    }

    /**
     * 根据分组名称 获取获取系统设置
     *
     * @param group
     * @return
     */
    @Override
    public ResponseDTO<List<SystemConfigVO>> getListByGroup(String group) {

        List<SystemConfig> entityList = systemConfigMapper.getListByGroup(group);
        if (CollectionUtils.isEmpty(entityList)) {
            return ResponseDTO.succData(Lists.newArrayList());
        }
        List<SystemConfigVO> systemConfigList = BeanUtil.copyList(entityList, SystemConfigVO.class);
        return ResponseDTO.succData(systemConfigList);
    }

    /**
     * 根据分组名称 获取获取系统设置
     *
     * @param group
     * @return
     */
    @Override
    public List<SystemConfigDTO> getListByGroup(SystemConfigEnum.Group group) {
        List<SystemConfig> entityList = systemConfigMapper.getListByGroup(group.name());
        if (CollectionUtils.isEmpty(entityList)) {
            return Lists.newArrayList();
        }
        List<SystemConfigDTO> systemConfigList = BeanUtil.copyList(entityList, SystemConfigDTO.class);
        return systemConfigList;
    }

}
