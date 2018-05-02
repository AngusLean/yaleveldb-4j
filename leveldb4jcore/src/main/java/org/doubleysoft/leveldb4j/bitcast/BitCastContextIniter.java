package org.doubleysoft.leveldb4j.bitcast;

import org.doubleysoft.leveldb4j.bitcast.manager.DbFileStorageManager;

/**
 * @author anguslean
 * @Description
 * @Date 2018/4/26
 */
public class BitCastContextIniter {
    public static void init(String dbPath) {
        DbFileStorageManager.init(dbPath);
    }
}
