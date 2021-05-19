package cn.edu.ncu.dao.mapper;


import cn.edu.ncu.dao.entity.IdGenerator;
import cn.edu.ncu.pojo.dto.IdGeneratorLastNumberDTO;
import cn.edu.ncu.pojo.dto.IdGeneratorRecordDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * extends CommonMapper<IdGenerator>
 * zhuo
 */
@Mapper
public interface IdGeneratorMapper {

    IdGeneratorLastNumberDTO selectLastNumber(Long id);

    List<IdGenerator> selectAll();

    void updateLastNumber(@Param("generatorId") Long generatorId, @Param("lastNumber") Long lastNumber);

    int replaceIdGeneratorRecord(@Param("generatorId") Long generatorId,//
                                 @Param("year") int year,//
                                 @Param("month") int month,//
                                 @Param("day") int day,//
                                 @Param("lastNumber") Long lastNumber);

    IdGeneratorRecordDTO selectHistoryLastNumber(@Param("generatorId") Long generatorId,
                                                 @Param("year") int year,
                                                 @Param("month") int month,
                                                 @Param("day") int day);

}
