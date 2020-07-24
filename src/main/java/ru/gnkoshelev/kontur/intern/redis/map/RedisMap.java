package ru.whitebite.redis.map;

import lombok.Getter;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;

public class RedisMap implements Map<String, String> {
 HashMap
    private final JedisPool jedisPool = new JedisPool(new GenericObjectPoolConfig(), "localhost", 6379);
    private Jedis jedisClient = jedisPool.getResource();

    {
        Runtime.getRuntime().addShutdownHook(new Thread(this::clear));
    }

    public RedisMap(String nameSpace) {
        NameSpace = nameSpace;
    }

    public RedisMap() {
        NameSpace = "RedisMap_" + UUID.randomUUID().toString();
    }

    @Getter
    String NameSpace;

    @Override
    public int size() {
        return jedisClient.hlen(NameSpace).intValue();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }


    @Override
    public boolean containsKey(Object key) {
        return jedisClient.hexists(NameSpace, Objects.requireNonNull(key).toString());
    }


    @Override
    public boolean containsValue(Object value) {
        Collection<String> q = values();
        for (String val : q) {
            if (value.toString().equals(val))
                return true;
        }
        return false;
    }

    @Override
    public String get(Object key) {
        return key != null ? jedisClient.hget(NameSpace, key.toString()) : null;
    }

    @Override
    public String put(String key, String value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        String current = get(key);
        jedisClient.hset(NameSpace, key, value);
        return current;
    }


    @Override
    public String remove(Object key) {
        String current = get(key);
        if (key != null) {
            jedisClient.hdel(NameSpace, key.toString());
        }
        return current;
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        jedisClient.hmset(NameSpace, (Map<String, String>) m);
    }

    @Override
    public void clear() {
        jedisClient.del(NameSpace);
    }


    @Override
    public Set<String> keySet() {
        return jedisClient.hkeys(NameSpace);
    }

    @Override
    public Collection<String> values() {
        return jedisClient.hvals(NameSpace);
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        return jedisClient.hgetAll(NameSpace).entrySet();
    }
}
