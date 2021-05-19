package cn.edu.ncu.service.impl;

import cn.edu.ncu.common.constant.SystemConfigEnum;
import cn.edu.ncu.common.constant.ThesaurusConst;
import cn.edu.ncu.common.redis.service.RedisService;
import cn.edu.ncu.common.util.basic.ListUtil;
import cn.edu.ncu.common.util.basic.StringUtil;
import cn.edu.ncu.common.util.bean.BeanUtil;
import cn.edu.ncu.common.util.dao.IdGeneratorUtil;
import cn.edu.ncu.dao.entity.Thesaurus;
import cn.edu.ncu.dao.mapper.SystemConfigMapper;
import cn.edu.ncu.dao.mapper.ThesaurusMapper;
import cn.edu.ncu.pojo.dto.ThesaurusDTO;
import cn.edu.ncu.service.SystemConfigService;
import cn.edu.ncu.service.ThesaurusService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.util.Assert;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: XiongZhiCong
 * @Description: 增删改查都需要使用到redis
 * @Date: Created in 19:19 2021/4/8
 * @Modified By:
 */
@Service
public class ThesaurusImpl implements ThesaurusService {
    private static final String QUERY_WORD = Thesaurus.WORD_PREFIX + "_TYPE:";
    @Resource
    private ThesaurusMapper thesaurusMapper;
    @Autowired
    private SystemConfigService systemConfigService;
    @Resource
    private IdGeneratorUtil idGeneratorUtil;
    @Resource
    private RedisService redisService;

    @Override
    public Boolean add(@Valid List<ThesaurusDTO> thesaurusDTOs) {
        List<ThesaurusDTO> list = thesaurusMapper.selectDTOsByType(ThesaurusConst.ALL_WORD);
        thesaurusDTOs = thesaurusDTOs.stream().filter(
                thesaurusDTO -> !containsKey(list, thesaurusDTO)).collect(Collectors.toList());
        //去重且去除空格
        thesaurusDTOs = trimList(thesaurusDTOs);
        if (thesaurusDTOs.size() > 0) {
            //根据需要新增的词集种类进行缓存的删除和后续的增加
            List<Integer> types = getTypesByThesaurus(thesaurusDTOs);
            deleteThesaurusByTypes(types);
            List<Thesaurus> thesauruses = BeanUtil.copyList(thesaurusDTOs, Thesaurus.class);
            for (Thesaurus thesaurus : thesauruses) {
                thesaurus.setId(idGeneratorUtil.snowflakeId());
                Date date = new Date(System.currentTimeMillis());
                if (thesaurus.getCreateTime() == null) {
                    thesaurus.setCreateTime(date);
                }
                if (thesaurus.getUpdateTime() == null) {
                    thesaurus.setUpdateTime(date);
                }
                //tkmybatis不能批量
                boolean isTrue = thesaurusMapper.insert(thesaurus) == 1;
                Assert.isTrue(isTrue, "插入词内容为" + thesaurus.getWord() + "失败！");
            }
            loadThesaurusByTypes(types);
        }

        return true;
    }

    /**
     * 去重：type+word验证
     *
     * @param list
     * @return
     */
    private static List<ThesaurusDTO> trimList(List<ThesaurusDTO> list) {
        Map<String, Integer> wordToType = new HashMap<>();
        List<ThesaurusDTO> result = new ArrayList<>();
        for (ThesaurusDTO thesaurusDTO : list) {
            String word = thesaurusDTO.getWord().trim();
            String key = word + "----" + thesaurusDTO.getType();
            if (wordToType.get(key) == null) {
                wordToType.put(key, 1);
                result.add(new ThesaurusDTO(word, thesaurusDTO.getType()));
            }
        }
        return result;
    }

    /**
     * list中根据word+type判断是否包含对应元素thesaurusDTO
     *
     * @param thesaurusDTOS
     * @param thesaurusDTO
     * @return
     */
    private Boolean containsKey(List<ThesaurusDTO> thesaurusDTOS, ThesaurusDTO thesaurusDTO) {
        Iterator<ThesaurusDTO> it = thesaurusDTOS.iterator();
        if (thesaurusDTO != null) {
            while (it.hasNext()) {
                if (thesaurusDTO.getKey().equals(it.next().getKey())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取集合中对象对应的所有种类type
     *
     * @param thesaurusDTOs
     * @return
     */
    private List<Integer> getTypesByThesaurus(List<ThesaurusDTO> thesaurusDTOs) {
        Set<Integer> types = new HashSet<>();
        for (ThesaurusDTO thesaurus : thesaurusDTOs) {
            types.add(thesaurus.getType());
        }
        return new ArrayList<>(types);
    }

    /**
     * 根据词类别进行缓存清除
     *
     * @param types
     */
    private void deleteThesaurusByTypes(List<Integer> types) {
        for (Integer type : types) {
            redisService.del(QUERY_WORD + type);
        }
    }

    private void loadThesaurusByTypes(List<Integer> types) {
        for (Integer type : types) {
            String key = QUERY_WORD + type;
            Boolean exists = redisService.exists(key);
            if (!exists) {
                List<String> thesauruses = thesaurusMapper.selectWordsByType(type);
                redisService.set(key, JSON.toJSONString(thesauruses));
            }
        }
    }

    @Override
    public List<String> getWordsByType(Integer type) {
        String key = QUERY_WORD + type;
        Boolean exists = redisService.exists(key);
        List<String> thesauruses;
        if (exists) {
            thesauruses = JSON.parseArray((String) redisService.get(key)).toJavaList(String.class);
        } else {
            thesauruses = thesaurusMapper.selectWordsByType(type);
            redisService.set(key, JSON.toJSONString(thesauruses));
        }

        return thesauruses;
    }

    /**
     * 根据类别加载词到缓存中
     */
    @Override
    public void loadCache() {
        redisService.deleteByPrefix(QUERY_WORD);
        List<Integer> wordsType = StringUtil.splitConverToIntList(systemConfigService.getCacheByKey(SystemConfigEnum.Key.WORDS_TYPE).getConfigValue(), ",", -2);
        for (Integer type : wordsType) {
            String key = QUERY_WORD + type;
            List<String> thesauruses = thesaurusMapper.selectWordsByType(type);
            redisService.set(key, JSON.toJSONString(thesauruses));
        }
    }
}
