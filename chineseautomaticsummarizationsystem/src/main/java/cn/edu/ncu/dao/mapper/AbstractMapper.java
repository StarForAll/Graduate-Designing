package cn.edu.ncu.dao.mapper;

import cn.edu.ncu.common.core.dao.CommonMapper;
import cn.edu.ncu.dao.entity.Abstract;
import cn.edu.ncu.dao.entity.AbstractInfo;
import cn.edu.ncu.pojo.dto.NoticeQueryDTO;
import cn.edu.ncu.pojo.query.AbstractInfoQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AbstractMapper extends CommonMapper<Abstract> {
    List<AbstractInfo> selectInfoByQuery(@Param("queryDTO") AbstractInfoQuery abstractInfoQuery);
    Integer queryCount(@Param("queryDTO")  AbstractInfoQuery abstractInfoQuery);
}