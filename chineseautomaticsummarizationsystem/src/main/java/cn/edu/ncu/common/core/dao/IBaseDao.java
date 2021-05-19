package cn.edu.ncu.common.core.dao;

import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description:
 * @Date: Created in 14:54 2021/4/8
 * @Modified By:
 */
public interface IBaseDao<T, Q> {
    int save(T entity);

    int delete(T entity);

    int update(T entity);

    List<T> queryByCondition(Q query);
}
