package cn.edu.ncu.controller;


import cn.edu.ncu.api.PositionApi;
import cn.edu.ncu.service.PositionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.PositionAddDTO;
import cn.edu.ncu.pojo.dto.PositionQueryDTO;
import cn.edu.ncu.pojo.dto.PositionUpdateDTO;
import cn.edu.ncu.pojo.vo.PositionResultVO;
import javax.validation.Valid;

/**
 * @Author: XiongZhiCong
 * @Description: 岗位
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@RestController
public class PositionController implements PositionApi {

    @Autowired
    private PositionService positionService;
    @Override
    public ResponseDTO<PageResultDTO<PositionResultVO>> getJobPage(@RequestBody @Valid PositionQueryDTO queryDTO) {
        return positionService.queryPositionByPage(queryDTO);
    }
    @Override
    public ResponseDTO<String> addJob(@RequestBody @Valid PositionAddDTO addDTO) {
        return positionService.addPosition(addDTO);
    }
    @Override
    public ResponseDTO<String> updateJob(@RequestBody @Valid PositionUpdateDTO updateDTO) {
        return positionService.updatePosition(updateDTO);
    }
    @Override
    public ResponseDTO<PositionResultVO> queryJobById(@PathVariable Long id) {
        return positionService.queryPositionById(id);
    }

    @Override
    public ResponseDTO<String> removeJob(@PathVariable Long id) {
        return positionService.removePosition(id);
    }

}
