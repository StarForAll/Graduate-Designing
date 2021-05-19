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
 *  Reload 路由
 *
 * @author listen
 * @date 2018/02/10 09:18
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
