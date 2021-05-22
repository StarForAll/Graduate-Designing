package cn.edu.ncu.api;

import cn.edu.ncu.common.annotation.OperateLog;
import cn.edu.ncu.common.constant.SwaggerTagConst;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.dao.entity.AbstractInfo;
import cn.edu.ncu.pojo.delete.AbstractInfoDelete;
import cn.edu.ncu.pojo.dto.TextSummaryLoadParamDTO;
import cn.edu.ncu.pojo.query.AbstractInfoQuery;
import io.swagger.annotations.Api;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

/**
 * @Author: XiongZhiCong
 * @Description: 自动摘要API
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Api(tags = {SwaggerTagConst.Summary.TEXT_SUMMARIZATION})
@OperateLog
@RequestMapping(value = "/api/summary")
public interface TextSummarizationApi {
    /**
     * 加载初始化参数到前端
     * @return
     */
    @GetMapping("load")
    ResponseDTO<TextSummaryLoadParamDTO> loadParams();
    /**
     * 查询摘要信息
     * @param abstractInfoQuery
     * @return
     */
    @PostMapping("queryInfo")
    ResponseDTO<PageResultDTO<AbstractInfo>> queryAbstractInfo(@RequestBody  AbstractInfoQuery abstractInfoQuery);
    /**
     * 彻底删除摘要相关信息
     * @param abstractInfoDelete
     * @return
     */
    @PostMapping("delete")
    ResponseDTO<String> deleteAbstractInfo(@RequestBody @Valid AbstractInfoDelete abstractInfoDelete);
    /**
     * 根据文件生成对应的文摘内容并响应操作结果
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("getSummary")
    ResponseDTO getSummaryText(@RequestParam("file")MultipartFile file) throws IOException;
    /**
     * 根据摘要id得到对应的文摘内容
     * @param id
     * @return
     * @throws IOException
     */
    @GetMapping("querySummary/{id}")
    ResponseDTO<String> querySummaryContent(@PathVariable Long id);
}
