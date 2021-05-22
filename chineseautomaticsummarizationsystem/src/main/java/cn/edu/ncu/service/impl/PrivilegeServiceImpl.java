package cn.edu.ncu.service.impl;

import cn.edu.ncu.common.constant.PrivilegeResponseCodeConst;
import cn.edu.ncu.common.constant.PrivilegeTypeEnum;
import cn.edu.ncu.common.util.dao.IdGeneratorUtil;
import cn.edu.ncu.dao.entity.Privilege;
import cn.edu.ncu.dao.mapper.PrivilegeMapper;
import cn.edu.ncu.dao.mapper.RolePrivilegeMapper;
import cn.edu.ncu.service.PrivilegeRequestUrlService;
import cn.edu.ncu.service.PrivilegeService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.edu.ncu.common.core.pojo.ValidateList;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.PrivilegeFunctionDTO;
import cn.edu.ncu.pojo.dto.PrivilegeMenuDTO;
import cn.edu.ncu.pojo.vo.PrivilegeFunctionVO;
import cn.edu.ncu.pojo.vo.PrivilegeMenuVO;
import cn.edu.ncu.pojo.vo.PrivilegeRequestUrlVO;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: XiongZhiCong
 * @Description: [ 后台员工权限 ]
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Service
public class PrivilegeServiceImpl implements PrivilegeService {
    @Autowired
    private IdGeneratorUtil idGeneratorUtil;
    @Autowired
    private PrivilegeRequestUrlService privilegeRequestUrlService;

    @Resource
    private PrivilegeMapper privilegeMapper;

    @Resource
    private RolePrivilegeMapper rolePrivilegeMapper;

    /**
     * 获取系统所有请求路径
     *
     * @return
     */
    @Override
    public ResponseDTO<List<PrivilegeRequestUrlVO>> getPrivilegeUrlDTOList() {
        List<PrivilegeRequestUrlVO> privilegeUrlList = privilegeRequestUrlService.getPrivilegeList();
        return ResponseDTO.succData(privilegeUrlList);
    }

    /**
     * 批量保存权限菜单项
     *
     * @param menuList
     * @return
     */
    @Override
    public ResponseDTO<String> menuBatchSave(List<PrivilegeMenuDTO> menuList) {
        if (CollectionUtils.isEmpty(menuList)) {
            return ResponseDTO.succ();
        }
        //使用前端发送权限的排序
        for (int i = 0; i < menuList.size(); i++) {
            menuList.get(i).setSort(i);
        }

        List<Privilege> privilegeList = privilegeMapper.selectByExcludeType(PrivilegeTypeEnum.POINTS.getValue());
        //若数据库无数据 直接全部保存
        if (CollectionUtils.isEmpty(privilegeList)) {
            List<Privilege> menuSaveEntity = this.buildPrivilegeMenuEntity(menuList);
            privilegeMapper.batchInsert(menuSaveEntity);
            return ResponseDTO.succ();
        }
        //处理需更新的菜单项
        Map<String, PrivilegeMenuDTO> storageMap = menuList.stream().collect(Collectors.toMap(PrivilegeMenuDTO::getMenuKey, e -> e));
        Set<String> menuKeyList = storageMap.keySet();
        List<Privilege> updatePrivilegeList = privilegeList.stream().filter(e -> menuKeyList.contains(e.getKey())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(updatePrivilegeList)) {
            this.rebuildPrivilegeMenuEntity(storageMap, updatePrivilegeList);
            privilegeMapper.batchUpdate(updatePrivilegeList);
        }
        //处理需删除的菜单项
        List<String> delKeyList = privilegeList.stream().filter(e -> !menuKeyList.contains(e.getKey())).map(Privilege::getKey).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(delKeyList)) {
            privilegeMapper.delByKeyList(delKeyList);
            //处理需删除的功能点
            privilegeMapper.delByParentKeyList(delKeyList);
            rolePrivilegeMapper.deleteByPrivilegeKey(delKeyList);
        }

        //处理需新增的菜单项
        List<String> dbKeyList = privilegeList.stream().map(Privilege::getKey).collect(Collectors.toList());
        List<PrivilegeMenuDTO> addPrivilegeList = menuList.stream().filter(e -> !dbKeyList.contains(e.getMenuKey())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(addPrivilegeList)) {
            List<Privilege> menuAddEntity = this.buildPrivilegeMenuEntity(addPrivilegeList);
            privilegeMapper.batchInsert(menuAddEntity);
        }
        return ResponseDTO.succ();
    }

    /**
     * 构建权限菜单项类别
     *
     * @param menuList
     * @return
     */
    private List<Privilege> buildPrivilegeMenuEntity(List<PrivilegeMenuDTO> menuList) {
        List<Privilege> privilegeList = Lists.newArrayList();
        Privilege privilegeEntity;
        for (PrivilegeMenuDTO menuDTO : menuList) {
            privilegeEntity = new Privilege();
            privilegeEntity.setId(idGeneratorUtil.snowflakeId());
            privilegeEntity.setKey(menuDTO.getMenuKey());
            privilegeEntity.setName(menuDTO.getMenuName());
            privilegeEntity.setParentKey(menuDTO.getParentKey());
            privilegeEntity.setType(menuDTO.getType());
            privilegeEntity.setSort(menuDTO.getSort());
            privilegeEntity.setUrl(menuDTO.getUrl());
            privilegeList.add(privilegeEntity);
        }
        return privilegeList;
    }

    /**
     * 更新权限菜单项
     *
     * @param menuMap
     * @param menuEntityList
     */
    private void rebuildPrivilegeMenuEntity(Map<String, PrivilegeMenuDTO> menuMap, List<Privilege> menuEntityList) {
        for (Privilege menuEntity : menuEntityList) {
            PrivilegeMenuDTO menuDTO = menuMap.get(menuEntity.getKey());
            menuEntity.setName(menuDTO.getMenuName());
            menuEntity.setParentKey(menuDTO.getParentKey());
            menuEntity.setType(menuDTO.getType());
            menuEntity.setSort(menuDTO.getSort());
        }

    }

    /**
     * 查询所有的权限菜单
     *
     * @return
     */
    @Override
    public ResponseDTO<List<PrivilegeMenuVO>> menuQueryAll() {
        List<Privilege> privilegeEntityList = privilegeMapper.selectByType(PrivilegeTypeEnum.MENU.getValue());
        if (CollectionUtils.isEmpty(privilegeEntityList)) {
            return ResponseDTO.succData(Lists.newArrayList());
        }

        List<PrivilegeMenuVO> voList = privilegeEntityList.stream().map(e -> {
            PrivilegeMenuVO vo = new PrivilegeMenuVO();
            vo.setMenuKey(e.getKey());
            vo.setMenuName(e.getName());
            vo.setParentKey(e.getParentKey());
            vo.setSort(e.getSort());
            vo.setUrl(e.getUrl());
            return vo;
        }).collect(Collectors.toList());

        return ResponseDTO.succData(voList);
    }


    /**
     * 保存更新功能点
     *
     * @param privilegeFunctionDTO
     * @return
     */
    @Override
    public ResponseDTO<String> functionSaveOrUpdate(PrivilegeFunctionDTO privilegeFunctionDTO) {
        String functionKey = privilegeFunctionDTO.getFunctionKey();
        Privilege functionEntity = privilegeMapper.selectByKey(functionKey);
        if (functionEntity == null) {
            return ResponseDTO.wrap(PrivilegeResponseCodeConst.POINT_NOT_EXIST);
        }
        functionEntity.setUrl(privilegeFunctionDTO.getUrl());
        functionEntity.setName(privilegeFunctionDTO.getFunctionName());
        functionEntity.setParentKey(privilegeFunctionDTO.getMenuKey());
        functionEntity.setSort(privilegeFunctionDTO.getSort());
        privilegeMapper.update(functionEntity);

        return ResponseDTO.succ();
    }

    /**
     * 查询功能点
     *
     * @param menuKey
     * @return
     */
    @Override
    public ResponseDTO<List<PrivilegeFunctionVO>> functionQuery(String menuKey) {
        List<Privilege> functionPrivilegeList = privilegeMapper.selectByParentKey(menuKey);
        if (CollectionUtils.isEmpty(functionPrivilegeList)) {
            return ResponseDTO.succData(Lists.newArrayList());
        }
        List<PrivilegeFunctionVO> functionList = Lists.newArrayList();
        for (Privilege functionEntity : functionPrivilegeList) {
            PrivilegeFunctionVO functionDTO = new PrivilegeFunctionVO();
            functionDTO.setFunctionKey(functionEntity.getKey());
            functionDTO.setFunctionName(functionEntity.getName());
            functionDTO.setMenuKey(functionEntity.getParentKey());
            functionDTO.setUrl(functionEntity.getUrl());
            functionDTO.setSort(functionEntity.getSort());
            functionList.add(functionDTO);
        }
        return ResponseDTO.succData(functionList);
    }

    @Override
    public ResponseDTO<String> batchSaveFunctionList(ValidateList<PrivilegeFunctionDTO> functionList) {
        String menuKey = functionList.get(0).getMenuKey();
        Privilege privilegeEntity = privilegeMapper.selectByKey(menuKey);
        if (privilegeEntity == null) {
            return ResponseDTO.wrap(PrivilegeResponseCodeConst.MENU_NOT_EXIST);
        }

        List<String> functionKeyList = functionList.stream().map(PrivilegeFunctionDTO::getFunctionKey).collect(Collectors.toList());

        //数据库中存在的数据
        List<Privilege> existFunctionList = privilegeMapper.selectByKeyList(functionKeyList);
        //校验是否parent key重复
        boolean parentKeyExist = existFunctionList.stream().anyMatch(e -> !menuKey.equals(e.getParentKey()));
        if(parentKeyExist){
            return ResponseDTO.wrap(PrivilegeResponseCodeConst.ROUTER_KEY_NO_REPEAT);
        }

        existFunctionList = privilegeMapper.selectByParentKey(menuKey);
        Map<String, Privilege> privilegeEntityMap = existFunctionList.stream().collect(Collectors.toMap(Privilege::getKey, e -> e));
        //如果没有，则保存全部
        if (existFunctionList.isEmpty()) {
            List<Privilege> privilegeEntityList = functionList.stream().map(this::function2Privilege).collect(Collectors.toList());
            privilegeMapper.batchInsert(privilegeEntityList);
            return ResponseDTO.succ();
        }

        Set<String> functionKeySet = functionList.stream().map(PrivilegeFunctionDTO::getFunctionKey).collect(Collectors.toSet());
        //删除的
        List<Long> deleteIdList = existFunctionList.stream().filter(e -> !functionKeySet.contains(e.getKey())).map(Privilege::getId).collect(Collectors.toList());
        List<String> deleteKeyList = existFunctionList.stream().filter(e -> !functionKeySet.contains(e.getKey())).map(Privilege::getKey).collect(Collectors.toList());
        if (!deleteIdList.isEmpty()) {
            privilegeMapper.deleteBatchIds(deleteIdList);
            rolePrivilegeMapper.deleteByPrivilegeKey(deleteKeyList);
        }

        //需要更新的
        ArrayList<Privilege> batchUpdateList = Lists.newArrayList();
        for (PrivilegeFunctionDTO privilegeFunctionDTO : functionList) {
            Privilege existPrivilege = privilegeEntityMap.get(privilegeFunctionDTO.getFunctionKey());
            if (existPrivilege != null) {
                existPrivilege.setSort(privilegeFunctionDTO.getSort());
                existPrivilege.setName(privilegeFunctionDTO.getFunctionName());
                batchUpdateList.add(existPrivilege);
            }
        }

        if (!batchUpdateList.isEmpty()) {
            privilegeMapper.batchUpdate(batchUpdateList);
        }

        //新增的
        List<Privilege> batchInsertList = functionList.stream()
                .filter(e -> !privilegeEntityMap.containsKey(e.getFunctionKey()))
                .map(this::function2Privilege)
                .collect(Collectors.toList());

        if (!batchInsertList.isEmpty()) {
            privilegeMapper.batchInsert(batchInsertList);
        }

        return ResponseDTO.succ();
    }

    private Privilege function2Privilege(PrivilegeFunctionDTO privilegeFunction) {
        Privilege privilegeEntity = new Privilege();
        privilegeEntity.setId(idGeneratorUtil.snowflakeId());
        privilegeEntity.setKey(privilegeFunction.getFunctionKey());
        privilegeEntity.setName(privilegeFunction.getFunctionName());
        privilegeEntity.setParentKey(privilegeFunction.getMenuKey());
        privilegeEntity.setType(PrivilegeTypeEnum.POINTS.getValue());
        privilegeEntity.setSort(privilegeFunction.getSort());
        privilegeEntity.setUrl("");
        return privilegeEntity;
    }
}
