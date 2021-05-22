package cn.edu.ncu.service;

import cn.edu.ncu.common.core.pojo.ValidateList;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.PrivilegeFunctionDTO;
import cn.edu.ncu.pojo.dto.PrivilegeMenuDTO;
import cn.edu.ncu.pojo.vo.PrivilegeFunctionVO;
import cn.edu.ncu.pojo.vo.PrivilegeMenuVO;
import cn.edu.ncu.pojo.vo.PrivilegeRequestUrlVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author: XiongZhiCong
 * @Description: 后台员工权限
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public interface PrivilegeService {


    /**
     * 获取系统所有请求路径
     *
     * @return
     */
     ResponseDTO<List<PrivilegeRequestUrlVO>> getPrivilegeUrlDTOList() ;

    /**
     * 批量保存权限菜单项
     *
     * @param menuList
     * @return
     */
    @Transactional(rollbackFor = Throwable.class)
     ResponseDTO<String> menuBatchSave(List<PrivilegeMenuDTO> menuList) ;



    /**
     * 查询所有的权限菜单
     *
     * @return
     */
     ResponseDTO<List<PrivilegeMenuVO>> menuQueryAll();


    /**
     * 保存更新功能点
     *
     * @param privilegeFunctionDTO
     * @return
     */
     ResponseDTO<String> functionSaveOrUpdate(PrivilegeFunctionDTO privilegeFunctionDTO) ;

    /**
     * 查询功能点
     *
     * @param menuKey
     * @return
     */
     ResponseDTO<List<PrivilegeFunctionVO>> functionQuery(String menuKey);

    @Transactional(rollbackFor = Exception.class)
    ResponseDTO<String> batchSaveFunctionList(ValidateList<PrivilegeFunctionDTO> functionList) ;

}
