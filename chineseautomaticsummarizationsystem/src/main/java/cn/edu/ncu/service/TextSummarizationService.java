package cn.edu.ncu.service;

import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.dao.entity.AbstractInfo;
import cn.edu.ncu.pojo.delete.AbstractInfoDelete;
import cn.edu.ncu.pojo.dto.TextSummaryLoadParamDTO;
import cn.edu.ncu.pojo.query.AbstractInfoQuery;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


/**
 * @Author: XiongZhiCong
 * @Description: 自动摘要对应接口
 * @Date: Created in 16:40 2021/4/16
 * @Modified By:
 */
public interface TextSummarizationService {
    /**
     * 加载初始化参数到前端
     * @return
     */
    ResponseDTO<TextSummaryLoadParamDTO> loadParams();
    /**
     * 查询摘要信息
     * @param abstractInfoQuery
     * @return
     */
    ResponseDTO<PageResultDTO<AbstractInfo>> queryAbstractInfo(AbstractInfoQuery abstractInfoQuery);
    /**
     * 彻底删除摘要相关信息
     * @param abstractInfoDelete
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    ResponseDTO<String> deleteAbstractInfo(AbstractInfoDelete abstractInfoDelete);
    /**
     * 根据摘要id得到对应的文摘内容
     * @param id
     * @return
     * @throws IOException
     */
    ResponseDTO<String> querySummaryContent(Long id);
    /**
     * 根据文件得到生成文摘内容并响应操作结果
     * @param file
     * @return
     * @throws IOException
     */
    @Transactional(rollbackFor = Exception.class)
    ResponseDTO getSummaryText(MultipartFile file) throws IOException;

}
