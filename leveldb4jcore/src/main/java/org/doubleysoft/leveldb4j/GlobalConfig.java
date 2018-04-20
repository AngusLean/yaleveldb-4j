package org.doubleysoft.leveldb4j;

import org.doubleysoft.leveldb4j.api.index.IDbIndex;
import org.doubleysoft.leveldb4j.api.storage.IDbFileReader;
import org.doubleysoft.leveldb4j.api.storage.IDbDataReader;
import org.doubleysoft.leveldb4j.bitcast.index.IDbIndexImpl;
import org.doubleysoft.leveldb4j.bitcast.storage.IDbFileReaderLocalImpl;
import org.doubleysoft.leveldb4j.bitcast.storage.IDbDataReaderImpl;

public class GlobalConfig {
    public static String CHART_SET = "utf-8";

    public static long MAX_FILE_SIZE = 1024*2014l;

    public static String DB_ROOT_PATH = "./";

    public static IDbDataReader getDataReader(){
        IDbDataReader iDbDataReader = new IDbDataReaderImpl();
        return iDbDataReader;
    }

    public static IDbFileReader getFileReader(String filePath){
        IDbFileReader iDbFileReader = new IDbFileReaderLocalImpl(filePath);
        return iDbFileReader;
    }

    private static IDbIndex iDbIndex;
    public static IDbIndex getDbIndex(){
        if(iDbIndex == null){
            iDbIndex = new IDbIndexImpl();
        }
        return iDbIndex;
    }
}