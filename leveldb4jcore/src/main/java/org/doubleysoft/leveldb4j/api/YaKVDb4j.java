package org.doubleysoft.leveldb4j.api;

/**
 * @author anguslean
 * @Description
 * @Date 2018/4/25
 */
public interface YaKVDb4j<T> {
    /**
     * add a key-value to database
     * @param key
     * @param val
     */
    void put(String key, T val);

    /**
     * get a value by key
     * @param key
     * @return
     */
    T get(String key);

    /**
     * delete a storaged data
     * @param key
     */
    void delete(String key);
}
