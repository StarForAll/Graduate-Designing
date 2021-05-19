package cn.edu.ncu.common.core.dao;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: 使业务层和具体的 mybatis的 mapper 解耦,所有mapper都实现本接口;
 *              注意,不能扫描到本接口.
 * @Date: Created in 14:54 2021/4/8
 * @Modified By:
 */
public interface CommonMapper<T> extends Mapper<T>, MySqlMapper<T> {
}