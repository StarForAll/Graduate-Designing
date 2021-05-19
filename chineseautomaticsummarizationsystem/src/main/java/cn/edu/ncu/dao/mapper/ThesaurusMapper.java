package cn.edu.ncu.dao.mapper;

import cn.edu.ncu.common.core.dao.CommonMapper;
import cn.edu.ncu.dao.entity.Thesaurus;
import cn.edu.ncu.pojo.dto.ThesaurusDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ThesaurusMapper extends CommonMapper<Thesaurus> {
    List<String> selectWordsByType(@Param("type")Integer type);
    List<ThesaurusDTO> selectDTOsByType(@Param("type")Integer type);
}