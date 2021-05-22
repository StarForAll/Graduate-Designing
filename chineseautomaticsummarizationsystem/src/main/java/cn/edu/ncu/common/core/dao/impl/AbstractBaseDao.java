package cn.edu.ncu.common.core.dao.impl;


import cn.edu.ncu.common.core.dao.CommonMapper;
import cn.edu.ncu.common.core.dao.IBaseDao;

import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description:
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public abstract class AbstractBaseDao<T, M extends CommonMapper<T>, Q> implements IBaseDao<T, Q> {

    /**
     * 注入框架隔离的 Mapper, 后面的数据操作依赖此 mapper
     */
    protected M myMapper;

    @Override
    public int save(T entity) {
        return myMapper.insert(entity);
    }

    @Override
    public int delete(T entity) {
        return myMapper.delete(entity);
    }

    @Override
    public int update(T entity) {
        return myMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public List<T> queryByCondition(Q query) {
        return null;
    }
}
