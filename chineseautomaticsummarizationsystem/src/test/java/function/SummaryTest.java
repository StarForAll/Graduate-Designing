package function;

import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.common.util.basic.FileUtil;
import cn.edu.ncu.dao.entity.AbstractInfo;
import cn.edu.ncu.dao.mapper.AbstractMapper;
import cn.edu.ncu.pojo.delete.AbstractInfoDelete;
import cn.edu.ncu.pojo.query.AbstractInfoQuery;
import cn.edu.ncu.service.TextSummarizationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: 自动摘要功能测试
 * @Date: Created in 19:03 2021/4/20
 * @Modified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = cn.edu.ncu.ChineseAutomaticSummarizationSystem.class)
@EnableCaching
public class SummaryTest {
    @Autowired
    private TextSummarizationService textSummarizationService;
    @Resource
    private AbstractMapper abstractMapper;
    @Test
    public void outSummary() throws IOException {
        String filePath = "D:\\Graduate Designing\\chineseautomaticsummarizationsystem\\src\\main\\resources\\paper_resources\\1.txt";
        File file = new File(filePath);
        MultipartFile multipartFile = FileUtil.fileToMultipartFile(file);
        textSummarizationService.getSummaryText(multipartFile);
    }
    @Test
    public void getInfo() {
        AbstractInfoQuery abstractInfoQuery = new AbstractInfoQuery();
        abstractInfoQuery.setPageNum(1);
        abstractInfoQuery.setPageSize(10);
        ResponseDTO<PageResultDTO<AbstractInfo>> pageResultDTOResponseDTO = textSummarizationService.queryAbstractInfo(abstractInfoQuery);
        List<AbstractInfo> list = pageResultDTOResponseDTO.getData().getList();
        System.out.println(list.get(0).getAnAbstract());
        System.out.println("createTime:"+ list.get(0).getFile().getCreateTime());
    }
    @Test
    public void deleteInfo(){
        AbstractInfoDelete abstractInfoDelete = new AbstractInfoDelete(1384481946750550016L, 1384481940060635136L);
        ResponseDTO<String> stringResponseDTO = textSummarizationService.deleteAbstractInfo(abstractInfoDelete);
        System.out.println(stringResponseDTO);
    }
    @Test
    public void deleteAbstract(){
        abstractMapper.deleteByPrimaryKey(1384481946750550016L);
    }
}
