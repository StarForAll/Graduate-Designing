package cn.edu.ncu.controller;

import cn.edu.ncu.api.SystemConfigApi;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.SystemConfigAddDTO;
import cn.edu.ncu.pojo.dto.SystemConfigQueryDTO;
import cn.edu.ncu.pojo.dto.SystemConfigUpdateDTO;
import cn.edu.ncu.pojo.vo.SystemConfigVO;
import cn.edu.ncu.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * [  ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2019 1024lab.netInc. All rights reserved.
 * @date
 * @since JDK1.8
 */
@RestController
public class SystemConfigController implements SystemConfigApi {

    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    public ResponseDTO<PageResultDTO<SystemConfigVO>> getSystemConfigPage(@RequestBody @Valid SystemConfigQueryDTO queryDTO) {
        return systemConfigService.getSystemConfigPage(queryDTO);
    }
    @Override
    public ResponseDTO<String> addSystemConfig(@RequestBody @Valid SystemConfigAddDTO configAddDTO) {
        return systemConfigService.addSystemConfig(configAddDTO);
    }
    @Override
    public ResponseDTO<String> updateSystemConfig(@RequestBody @Valid SystemConfigUpdateDTO updateDTO) {
        return systemConfigService.updateSystemConfig(updateDTO);
    }
    @Override
    public ResponseDTO<List<SystemConfigVO>> getListByGroup(String group) {
        return systemConfigService.getListByGroup(group);
    }
    @Override
    public ResponseDTO<SystemConfigVO> selectByKey(String configKey) {
        return systemConfigService.selectByKey(configKey);
    }

}
