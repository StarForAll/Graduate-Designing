package cn.edu.ncu.service;

import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.pojo.dto.ReloadItemUpdateDTO;
import cn.edu.ncu.pojo.vo.ReloadItemVO;
import cn.edu.ncu.pojo.vo.ReloadResultVO;

import java.util.List;

/**
 *  initDefines 业务
 *
 * @author listen
 * @date 2018/02/10 13:49
 */
public interface ReloadService {



    /**
     * 注册到Reload里
     */
    void register(Object reload);

    /**
     * 获取所有 initDefines 项
     *
     * @return
     */
     ResponseDTO<List<ReloadItemVO>> listAllReloadItem();

    /**
     * 根据 tag
     * 获取所有 initDefines 运行结果
     * @return ResponseDTO
     */
     ResponseDTO<List<ReloadResultVO>> listReloadItemResult(String tag) ;

    /**
     * 通过标签更新标识符
     *
     * @param updateDTO
     * @return
     */
     ResponseDTO<String> updateByTag(ReloadItemUpdateDTO updateDTO);
}
