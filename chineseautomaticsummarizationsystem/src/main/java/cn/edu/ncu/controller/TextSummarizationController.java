package cn.edu.ncu.controller;

import cn.edu.ncu.api.TextSummarizationApi;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.dao.entity.AbstractInfo;
import cn.edu.ncu.pojo.delete.AbstractInfoDelete;
import cn.edu.ncu.pojo.dto.TextSummaryLoadParamDTO;
import cn.edu.ncu.pojo.query.AbstractInfoQuery;
import cn.edu.ncu.service.TextSummarizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

/**
 * @Author: XiongZhiCong
 * @Description: 自动摘要
 * @Date: Created in 10:51 2021/4/21
 * @Modified By:
 */
@RestController
public class TextSummarizationController  implements TextSummarizationApi {
    @Autowired
    private TextSummarizationService textSummarizationService;

    @Override
    public ResponseDTO<TextSummaryLoadParamDTO> loadParams() {
        return textSummarizationService.loadParams();
    }

    @Override
    public ResponseDTO<PageResultDTO<AbstractInfo>> queryAbstractInfo(AbstractInfoQuery abstractInfoQuery) {
        return textSummarizationService.queryAbstractInfo(abstractInfoQuery);
    }

    @Override
    public ResponseDTO<String> deleteAbstractInfo(@Valid AbstractInfoDelete abstractInfoDelete) {
        return textSummarizationService.deleteAbstractInfo(abstractInfoDelete);
    }

    @Override
    public ResponseDTO getSummaryText(MultipartFile file) throws IOException {
        return textSummarizationService.getSummaryText(file);
    }

    @Override
    public ResponseDTO<String> querySummaryContent(Long id) {
        return textSummarizationService.querySummaryContent(id);
    }
}
