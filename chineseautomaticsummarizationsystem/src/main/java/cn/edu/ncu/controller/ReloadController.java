package cn.edu.ncu.controller;

import cn.edu.ncu.api.ReloadApi;
import cn.edu.ncu.service.ReloadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.ReloadItemUpdateDTO;
import cn.edu.ncu.pojo.vo.ReloadItemVO;
import cn.edu.ncu.pojo.vo.ReloadResultVO;
import javax.validation.Valid;
import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: reload重启
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@RestController
public class ReloadController implements ReloadApi {

    @Autowired
    private ReloadService reloadService;

    @Override
    public ResponseDTO<List<ReloadItemVO>> listAllReloadItem() {
        return reloadService.listAllReloadItem();
    }
    @Override
    public ResponseDTO<List<ReloadResultVO>> queryReloadResult(@PathVariable("tag") String tag) {
        return reloadService.listReloadItemResult(tag);
    }
    @Override
    public ResponseDTO<String> updateByTag(@RequestBody @Valid ReloadItemUpdateDTO updateDTO) {
        return reloadService.updateByTag(updateDTO);
    }
}
