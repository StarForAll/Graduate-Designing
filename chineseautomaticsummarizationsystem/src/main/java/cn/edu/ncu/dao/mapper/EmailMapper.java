package cn.edu.ncu.dao.mapper;

import cn.edu.ncu.common.core.dao.CommonMapper;
import cn.edu.ncu.dao.entity.Email;
import cn.edu.ncu.pojo.dto.EmailQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * [  ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2018 1024lab.netInc. All rights reserved.
 * @date 2019-05-13 17:10:16
 * @since JDK1.8
 */
@Mapper
public interface EmailMapper extends CommonMapper<Email> {
    void update(Email email);
    /**
     * 获取queryByPage对应的总条数
     * @param queryDTO
     * @return
     */
    Integer queryCount(@Param("queryDTO") EmailQueryDTO queryDTO);
    /**
     * 分页查询
     * @param queryDTO
     * @return Email
    */
    List<Email> queryByPage(@Param("queryDTO") EmailQueryDTO queryDTO);

    /**
     * 根据id删除
     * @param id
     * @return
    */
    void deleteById(@Param("id") Long id);

    /**
     * 批量删除
     * @param idList
     * @return
    */
    void deleteByIds(@Param("idList") List<Long> idList);
}
