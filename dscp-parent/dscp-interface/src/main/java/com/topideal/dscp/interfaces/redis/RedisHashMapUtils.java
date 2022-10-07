package com.topideal.dscp.interfaces.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Redis hash map 工具类
 * @Author: kongxj
 * @Date: 2022/7/15 11:43
 */
@Slf4j
@Component
public class RedisHashMapUtils {

    @Autowired
    private RedisTemplate redisTemplate;

    /***************************************    HASHMAP ***********************************/
    /**
     * 将map放入缓存
     *
     * @param key        缓存key
     * @param mapValue   map值
     * @return true成功 false 失败
     */
    public void setMap(String key, Map<String, Object> mapValue) {
        redisTemplate.boundHashOps(key).putAll(mapValue);
    }

    /**
     * 将map放入缓存并设置时间
     *
     * @param key        缓存key
     * @param mapValue   map值
     * @return true成功 false 失败
     */
    public void setMap(String key, Map<String, Object> mapValue, long time) {
        redisTemplate.boundHashOps(key).putAll(mapValue);
        if (time > 0) {
            redisTemplate.boundHashOps(key).expire(time, TimeUnit.SECONDS);
        }
    }

    /**
     * 根据 key 获取map
     *
     * @param key        缓存key
     * @return map
     */
    public Map<String, Object> getMap(String key) {
        return redisTemplate.boundHashOps(key).entries();
    }

    /**
     * 根据 缓存key 和 map key 获取 值
     *
     * @param key        缓存key
     * @param mapKey     map key
     * @return 值
     */
    public Object getOneByMapKey(String key, String mapKey) {
        return redisTemplate.boundHashOps(key).get(mapKey);
    }

    /**
     * 缓存key 和 map key 是否存在
     *
     * @param key        缓存key
     * @param mapKey     map key
     * @return 值
     */
    public boolean hasMapKey(String key, String mapKey) {
        return redisTemplate.boundHashOps(key).hasKey(mapKey);
    }

    /**
     * 设置 缓存key 和 map key 对应的值
     *
     * @param key        缓存key
     * @param mapKey     map key
     * @return 值
     */
    public void setOneMapKeyAndValue(String key, String mapKey, Object value) {
        redisTemplate.boundHashOps(key).put(mapKey, value);
    }

    /**
     * 删除 缓存key
     *
     * @param key        缓存key
     * @return 值
     */
    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }


    /**
     * 删除 缓存key 和 map key 对应的值
     *
     * @param key        缓存key
     * @param mapKey     map key
     * @return 值
     */
    public void deleteByMapKey(String key, String mapKey) {
        redisTemplate.boundHashOps(key).delete(mapKey);
    }


}
