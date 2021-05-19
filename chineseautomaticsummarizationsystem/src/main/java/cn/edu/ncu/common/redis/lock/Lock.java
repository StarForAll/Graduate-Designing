package cn.edu.ncu.common.redis.lock;

/**
 * @Author: XiongZhiCong
 * @Description: 锁接口
 * @Date: Created in 14:54 2021/4/8
 * @Modified By:
 */
public interface Lock {

    boolean getLock(String key, String id, int expireTime);

    boolean releaseLock(String key, String id);
}
