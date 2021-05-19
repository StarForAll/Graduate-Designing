package mybatis.add;

import cn.edu.ncu.common.constant.ThesaurusConst;
import cn.edu.ncu.common.redis.service.RedisService;
import cn.edu.ncu.common.util.basic.FileUtil;
import cn.edu.ncu.pojo.dto.ThesaurusDTO;
import cn.edu.ncu.service.ThesaurusService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: XiongZhiCong
 * @Description:
 * @Date: Created in 19:10 2021/4/8
 * @Modified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=cn.edu.ncu.ChineseAutomaticSummarizationSystem.class)
@EnableCaching
public class WordTest {
    @Autowired
    private ThesaurusService thesaurusService;
    @Resource
    private RedisService redisService;
    @Test
    public void addTest() throws IOException {
        List<String> list = FileUtil.readLines("C:\\Users\\Administrator\\Desktop\\doubt.txt", StandardCharsets.UTF_8);
        for(int i=0;i<list.size();i++){
            String s=list.get(i).trim();
            list.set(i,s);
        }
        System.out.println("前："+list.size());
//        stream去重
        list=list.stream().distinct().collect(Collectors.toList());
        System.out.println("去重后："+list.size());
        List<ThesaurusDTO> thesaurusDTOS  = new ArrayList<>();
        for(String word:list){
            thesaurusDTOS.add(new ThesaurusDTO(word,ThesaurusConst.INTERROGATIVE_WORD));
        }
        thesaurusService.add(thesaurusDTOS);
    }
    @Test
    public void deleteAllCache(){
        redisService.deleteByPrefix(null);

    }

    /**
     * 无意义的词：结论，引言，总结，结语，结束语,小结,总结语,展望,与,
     * 一~十
     *
     */
    @Test
    public void addRegular(){
        List<ThesaurusDTO> thesaurusDTOS  = new ArrayList<>();
        thesaurusDTOS.add(new ThesaurusDTO("一",ThesaurusConst.MEANINGLESS_WORD));
        thesaurusDTOS.add(new ThesaurusDTO("二",ThesaurusConst.MEANINGLESS_WORD));
        thesaurusDTOS.add(new ThesaurusDTO("三",ThesaurusConst.MEANINGLESS_WORD));
        thesaurusDTOS.add(new ThesaurusDTO("四",ThesaurusConst.MEANINGLESS_WORD));
        thesaurusDTOS.add(new ThesaurusDTO("五",ThesaurusConst.MEANINGLESS_WORD));
        thesaurusDTOS.add(new ThesaurusDTO("六",ThesaurusConst.MEANINGLESS_WORD));
        thesaurusDTOS.add(new ThesaurusDTO("七",ThesaurusConst.MEANINGLESS_WORD));
        thesaurusDTOS.add(new ThesaurusDTO("八",ThesaurusConst.MEANINGLESS_WORD));
        thesaurusDTOS.add(new ThesaurusDTO("九",ThesaurusConst.MEANINGLESS_WORD));
        thesaurusDTOS.add(new ThesaurusDTO("十",ThesaurusConst.MEANINGLESS_WORD));
        thesaurusService.add(thesaurusDTOS);
    }
}
