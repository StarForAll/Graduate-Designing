package cn.edu.ncu.controller;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.DataScopeBatchSetRoleDTO;
import cn.edu.ncu.pojo.vo.DataScopeAndViewTypeVO;
import cn.edu.ncu.pojo.vo.DataScopeSelectVO;
import cn.edu.ncu.service.DataScopeService;
import cn.edu.ncu.api.DataScopeApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: 数据范围
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@RestController
public class DataScopeController implements DataScopeApi {

    @Autowired
    private DataScopeService dataScopeService;

    @Override
    public ResponseDTO<List<DataScopeAndViewTypeVO>> dataScopeList() {
        return dataScopeService.dataScopeList();
    }

    @Override
    public ResponseDTO<List<DataScopeSelectVO>> dataScopeListByRole(@PathVariable Long roleId) {
        return dataScopeService.dataScopeListByRole(roleId);
    }

    @Override
    public ResponseDTO<String> dataScopeBatchSet(@RequestBody @Valid DataScopeBatchSetRoleDTO batchSetRoleDTO) {
        return dataScopeService.dataScopeBatchSet(batchSetRoleDTO);
    }


}
