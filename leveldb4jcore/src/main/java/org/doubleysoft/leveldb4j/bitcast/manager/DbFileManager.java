package org.doubleysoft.leveldb4j.bitcast.manager;

import org.doubleysoft.leveldb4j.GlobalConfig;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author anguslean
 * @Description
 * @Date 2018/4/20
 */
public class DbFileManager {
    private static String currentDbName;
    private static String currentDbPath;

    private static AtomicInteger fileIndex;

    static {
        fileIndex = new AtomicInteger();
        currentDbName = "bc"+fileIndex.getAndIncrement();
        currentDbPath = GlobalConfig.DB_ROOT_PATH + currentDbName;
    }

    public static String getDbFilePath(){
        return currentDbPath;
    }

    public static String getDbPathByFileId(String fileId){
        return GlobalConfig.DB_ROOT_PATH + "bc" + fileId;
    }

    public static String getNewDbFilePath(){
        currentDbPath = GlobalConfig.DB_ROOT_PATH + currentDbName + fileIndex.getAndIncrement();
        return currentDbPath;
    }

    public static String getDbFileId(){
        return String.valueOf(fileIndex.get());
    }
}
