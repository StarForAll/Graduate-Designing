package cn.edu.ncu.common.util.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author: XiongZhiCong
 * @Description: jedis工具类
 * @Date: Created in 14:54 2021/4/8
 * @Modified By:
 */
public class JedisUtil {
    private int port;
    private static JedisPool jedisPool ;
    private static JedisPoolConfig jedisPoolConfig;
    static {
        jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(10000);
        jedisPoolConfig.setMaxWaitMillis(10000);
        jedisPoolConfig.setMaxIdle(1000);
    }

    public static Jedis getResource(String host,int port,int timeout ){
           jedisPool = new JedisPool(jedisPoolConfig,host,port,timeout);
           return  jedisPool.getResource();
    }

}
