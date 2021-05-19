package cn.edu.ncu.dao.mapper;

import cn.edu.ncu.common.core.dao.CommonMapper;
import cn.edu.ncu.dao.entity.UserOperateLog;
import cn.edu.ncu.pojo.dto.UserOperateLogQueryDTO;
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
 * @date 2019-05-15 11:32:14
 * @since JDK1.8
 */
@Mapper
public interface UserOperateLogMapper extends CommonMapper<UserOperateLog> {
    void update(UserOperateLog userOperateLog);
    Integer queryCount(@Param("queryDTO") UserOperateLogQueryDTO queryDTO);
    /**
     * 分页查询
     * @param queryDTO
     * @return UserOperateLog
    */
    List<UserOperateLog> queryByPage(@Param("queryDTO") UserOperateLogQueryDTO queryDTO);

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
