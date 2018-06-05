package org.doubleysoft.leveldb4j.bitcast;

import org.doubleysoft.leveldb4j.api.YaKVDb4j;
import org.doubleysoft.leveldb4j.bitcast.manager.DbFileStorageManager;

/**
 * @author anguslean
 * @Date 2018/4/26
 */
public class BitCastContext {
    public static YaKVDb4j init(String dbPath) {
        DbFileStorageManager.init(dbPath);
        return new YaKVDb4JBitCastImpl();
    }
}
