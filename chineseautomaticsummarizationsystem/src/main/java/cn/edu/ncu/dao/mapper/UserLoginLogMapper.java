package cn.edu.ncu.dao.mapper;

import cn.edu.ncu.common.core.dao.CommonMapper;
import cn.edu.ncu.dao.entity.UserLoginLog;
import cn.edu.ncu.pojo.dto.UserLoginLogQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: 用户登录日志Mapper
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Mapper
public interface UserLoginLogMapper extends CommonMapper<UserLoginLog> {
    Integer queryCount(@Param("queryDTO") UserLoginLogQueryDTO queryDTO);
    /**
     * 分页查询
     * @param queryDTO
     * @return UserLoginLog
    */
    List<UserLoginLog> queryByPage(@Param("queryDTO") UserLoginLogQueryDTO queryDTO);

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
