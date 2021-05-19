package cn.edu.ncu.service;

import cn.edu.ncu.pojo.dto.ThesaurusDTO;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: 词库业务层接口
 * @Date: Created in 19:12 2021/4/8
 * @Modified By:
 */
public interface ThesaurusService {
    /**
     * 添加词到词库中
     * @param thesaurusDTOs 词集合
     * @return
     */
    @Transactional
    Boolean add(@Valid List<ThesaurusDTO> thesaurusDTOs);

    /**
     * 根据种类获取获取对应类别的所有的词集
     * @return
     */
    List<String> getWordsByType(Integer type);

    /**
     * 初始化加载词库
     */
    void loadCache();
}
