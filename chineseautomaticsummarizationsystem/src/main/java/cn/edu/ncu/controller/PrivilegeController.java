package cn.edu.ncu.controller;

import cn.edu.ncu.api.PrivilegeApi;
import cn.edu.ncu.common.core.pojo.ValidateList;
import cn.edu.ncu.service.PrivilegeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.PrivilegeFunctionDTO;
import cn.edu.ncu.pojo.dto.PrivilegeMenuDTO;
import cn.edu.ncu.pojo.vo.PrivilegeFunctionVO;
import cn.edu.ncu.pojo.vo.PrivilegeMenuVO;
import cn.edu.ncu.pojo.vo.PrivilegeRequestUrlVO;
import javax.validation.Valid;
import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: 权限
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@RestController
public class PrivilegeController implements PrivilegeApi {

    @Autowired
    private PrivilegeService privilegeService;
    @Override
    public ResponseDTO<List<PrivilegeRequestUrlVO>> getAllUrl() {
        return privilegeService.getPrivilegeUrlDTOList();
    }
    @Override
    public ResponseDTO<String> menuBatchSave(@Valid @RequestBody ValidateList<PrivilegeMenuDTO> menuList) {
        return privilegeService.menuBatchSave(menuList);
    }

    @Override
    public ResponseDTO<List<PrivilegeMenuVO>> queryAll() {
        return privilegeService.menuQueryAll();
    }

    @Override
    public ResponseDTO<String> functionSaveOrUpdate(@Valid @RequestBody PrivilegeFunctionDTO privilegeFunctionDTO) {
        return privilegeService.functionSaveOrUpdate(privilegeFunctionDTO);
    }
    @Override
    public ResponseDTO<String>  batchSaveFunctionList(@Valid @RequestBody ValidateList<PrivilegeFunctionDTO> functionList) {
        return privilegeService.batchSaveFunctionList(functionList);
    }


    @Override
    public ResponseDTO<List<PrivilegeFunctionVO>> functionQuery(@PathVariable String menuKey) {
        return privilegeService.functionQuery(menuKey);
    }


}
