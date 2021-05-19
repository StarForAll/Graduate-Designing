package cn.edu.ncu.service.impl;

import cn.edu.ncu.common.constant.RoleResponseCodeConst;
import cn.edu.ncu.common.util.bean.BeanUtil;
import cn.edu.ncu.common.util.dao.IdGeneratorUtil;
import cn.edu.ncu.dao.entity.Role;
import cn.edu.ncu.dao.mapper.RoleEmployeeMapper;
import cn.edu.ncu.dao.mapper.RoleMapper;
import cn.edu.ncu.dao.mapper.RolePrivilegeMapper;
import cn.edu.ncu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.RoleAddDTO;
import cn.edu.ncu.pojo.dto.RoleUpdateDTO;
import cn.edu.ncu.pojo.vo.RoleVO;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 角色管理业务
 *
 * @author listen
 * @date 2017/12/28 09:37
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private IdGeneratorUtil idGeneratorUtil;
    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RolePrivilegeMapper rolePrivilegeDao;

    @Resource
    private RoleEmployeeMapper roleEmployeeDao;

    /**
     * 新增添加角色
     *
     * @param roleAddDTO
     * @return ResponseDTO
     */
    @Override
    public ResponseDTO addRole(RoleAddDTO roleAddDTO) {
        Role employeeRoleEntity = roleMapper.getByRoleName(roleAddDTO.getRoleName());
        if (null != employeeRoleEntity) {
            return ResponseDTO.wrap(RoleResponseCodeConst.ROLE_NAME_EXISTS);
        }
        Role roleEntity = BeanUtil.copy(roleAddDTO, Role.class);
        if(roleEntity.getId()==null){
            roleEntity.setId(idGeneratorUtil.snowflakeId());
        }
        Date date=new Date(System.currentTimeMillis());
        if(roleEntity.getUpdateTime()==null){
            roleEntity.setUpdateTime(date);
        }
        if(roleEntity.getCreateTime()==null){
            roleEntity.setCreateTime(date);
        }
        roleMapper.insert(roleEntity);
        return ResponseDTO.succ();
    }

    /**
     * 根据角色id 删除
     *
     * @param roleId
     * @return ResponseDTO
     */
    @Override
    public ResponseDTO deleteRole(Long roleId) {
        Role roleEntity = roleMapper.selectByPrimaryKey(roleId);
        if (null == roleEntity) {
            return ResponseDTO.wrap(RoleResponseCodeConst.ROLE_NOT_EXISTS);
        }
        roleMapper.deleteByPrimaryKey(roleId);
        rolePrivilegeDao.deleteByRoleId(roleId);
        roleEmployeeDao.deleteByRoleId(roleId);
        return ResponseDTO.succ();
    }

    /**
     * 更新角色
     *
     * @param roleUpdateDTO
     * @return ResponseDTO
     */
    @Override
    public ResponseDTO<String> updateRole(RoleUpdateDTO roleUpdateDTO) {
        if (null == roleMapper.selectByPrimaryKey(roleUpdateDTO.getId())) {
            return ResponseDTO.wrap(RoleResponseCodeConst.ROLE_NOT_EXISTS);
        }
        Role employeeRoleEntity = roleMapper.getByRoleName(roleUpdateDTO.getRoleName());
        if (null != employeeRoleEntity && ! employeeRoleEntity.getId().equals(roleUpdateDTO.getId())) {
            return ResponseDTO.wrap(RoleResponseCodeConst.ROLE_NAME_EXISTS);
        }
        Role roleEntity = BeanUtil.copy(roleUpdateDTO, Role.class);
        roleMapper.update(roleEntity);
        return ResponseDTO.succ();
    }

    /**
     * 根据id获取角色数据
     *
     * @param roleId
     * @return ResponseDTO<RoleDTO>
     */
    @Override
    public ResponseDTO<RoleVO> getRoleById(Long roleId) {
        Role roleEntity = roleMapper.selectByPrimaryKey(roleId);
        if (null == roleEntity) {
            return ResponseDTO.wrap(RoleResponseCodeConst.ROLE_NOT_EXISTS);
        }
        RoleVO role = BeanUtil.copy(roleEntity, RoleVO.class);
        return ResponseDTO.succData(role);
    }

    /**
     * 获取所有角色列表
     *
     * @return ResponseDTO
     */
    @Override
    public ResponseDTO<List<RoleVO>> getAllRole() {
        List<Role> roleEntityList = roleMapper.selectAll();
        List<RoleVO> roleList = BeanUtil.copyList(roleEntityList, RoleVO.class);
        return ResponseDTO.succData(roleList);
    }
}
