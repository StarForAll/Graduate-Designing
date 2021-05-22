package cn.edu.ncu.service.impl;

import cn.edu.ncu.common.constant.RoleResponseCodeConst;
import cn.edu.ncu.common.util.bean.BeanUtil;
import cn.edu.ncu.common.util.dao.IdGeneratorUtil;
import cn.edu.ncu.dao.entity.Privilege;
import cn.edu.ncu.dao.entity.Role;
import cn.edu.ncu.dao.entity.RolePrivilege;
import cn.edu.ncu.dao.mapper.PrivilegeMapper;
import cn.edu.ncu.dao.mapper.RoleMapper;
import cn.edu.ncu.dao.mapper.RolePrivilegeMapper;
import cn.edu.ncu.pojo.dto.RolePrivilegeSimpleDTO;
import cn.edu.ncu.service.PrivilegeEmployeeService;
import cn.edu.ncu.service.RolePrivilegeService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.RolePrivilegeDTO;
import cn.edu.ncu.pojo.vo.RolePrivilegeTreeVO;
import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: XiongZhiCong
 * @Description: [ 后台员工权限 ]
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Service
public class RolePrivilegeServiceImpl implements RolePrivilegeService {
    @Autowired
    private IdGeneratorUtil idGeneratorUtil;
    @Resource
    private PrivilegeMapper privilegeMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RolePrivilegeMapper rolePrivilegeMapper;

    @Autowired
    private PrivilegeEmployeeService privilegeEmployeeService;

    /**
     * 更新角色权限
     *
     * @param updateDTO
     * @return ResponseDTO
     */
    @Override
    public ResponseDTO<String> updateRolePrivilege(RolePrivilegeDTO updateDTO) {
        Long roleId = updateDTO.getRoleId();
        Role roleEntity = roleMapper.selectByPrimaryKey(roleId);
        if (null == roleEntity) {
            return ResponseDTO.wrap(RoleResponseCodeConst.ROLE_NOT_EXISTS);
        }
        rolePrivilegeMapper.deleteByRoleId(roleId);
        List<RolePrivilege> rolePrivilegeList = Lists.newArrayList();
        RolePrivilege rolePrivilegeEntity;
        for (String privilegeKey : updateDTO.getPrivilegeKeyList()) {
            rolePrivilegeEntity = new RolePrivilege();
            rolePrivilegeEntity.setId(idGeneratorUtil.snowflakeId());
            rolePrivilegeEntity.setRoleId(roleId);
            rolePrivilegeEntity.setPrivilegeKey(privilegeKey);
            rolePrivilegeList.add(rolePrivilegeEntity);
        }
        rolePrivilegeMapper.batchInsert(rolePrivilegeList);
        privilegeEmployeeService.updateOnlineEmployeePrivilegeByRoleId(roleId);
        return ResponseDTO.succ();
    }

    @Override
    public ResponseDTO<RolePrivilegeTreeVO> listPrivilegeByRoleId(Long roleId) {
        RolePrivilegeTreeVO rolePrivilegeTreeDTO = new RolePrivilegeTreeVO();
        rolePrivilegeTreeDTO.setRoleId(roleId);

        List<Privilege> privilegeDTOList = privilegeMapper.selectAllPrivilege();
        if (CollectionUtils.isEmpty(privilegeDTOList)) {
            rolePrivilegeTreeDTO.setPrivilege(Lists.newArrayList());
            rolePrivilegeTreeDTO.setSelectedKey(Lists.newArrayList());
            return ResponseDTO.succData(rolePrivilegeTreeDTO);
        }
        //构造权限树
        List<RolePrivilegeSimpleDTO> privilegeList = this.buildPrivilegeTree(privilegeDTOList);
        //设置选中状态
        List<Privilege> rolePrivilegeEntityList = rolePrivilegeMapper.listByRoleId(roleId);
        List<String> privilegeKeyList = rolePrivilegeEntityList.stream().map(e -> e.getKey()).collect(Collectors.toList());
        rolePrivilegeTreeDTO.setPrivilege(privilegeList);
        rolePrivilegeTreeDTO.setSelectedKey(privilegeKeyList);
        return ResponseDTO.succData(rolePrivilegeTreeDTO);
    }

    private List<RolePrivilegeSimpleDTO> buildPrivilegeTree(List<Privilege> privilegeEntityList) {
        List<RolePrivilegeSimpleDTO> privilegeTree = Lists.newArrayList();
        List<Privilege> rootPrivilege = privilegeEntityList.stream().filter(e -> e.getParentKey() == null).collect(Collectors.toList());
        rootPrivilege.sort(Comparator.comparing(Privilege::getSort));
        if (CollectionUtils.isEmpty(rootPrivilege)) {
            return privilegeTree;
        }
        privilegeTree = BeanUtil.copyList(rootPrivilege, RolePrivilegeSimpleDTO.class);
        privilegeTree.forEach(e -> e.setChildren(Lists.newArrayList()));
        this.buildChildPrivilegeList(privilegeEntityList, privilegeTree);
        return privilegeTree;
    }

    private void buildChildPrivilegeList(List<Privilege> privilegeEntityList, List<RolePrivilegeSimpleDTO> parentMenuList) {
        List<String> parentKeyList = parentMenuList.stream().map(RolePrivilegeSimpleDTO :: getKey).collect(Collectors.toList());
        List<Privilege> childEntityList = privilegeEntityList.stream().filter(e -> parentKeyList.contains(e.getParentKey())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(childEntityList)) {
            return;
        }
        Map<String, List<Privilege>> listMap = childEntityList.stream().collect(Collectors.groupingBy(Privilege :: getParentKey));
        for (RolePrivilegeSimpleDTO rolePrivilegeSimpleDTO : parentMenuList) {
            String key = rolePrivilegeSimpleDTO.getKey();
            List<Privilege> privilegeEntities = listMap.get(key);
            if (CollectionUtils.isEmpty(privilegeEntities)) {
                continue;
            }
            privilegeEntities.sort(Comparator.comparing(Privilege::getSort));
            List<RolePrivilegeSimpleDTO> privilegeList = BeanUtil.copyList(privilegeEntities, RolePrivilegeSimpleDTO.class);
            privilegeList.forEach(e -> e.setChildren(Lists.newArrayList()));
            rolePrivilegeSimpleDTO.setChildren(privilegeList);
            this.buildChildPrivilegeList(privilegeEntityList, privilegeList);
        }
    }
}
