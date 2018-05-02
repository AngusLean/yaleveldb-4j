package org.doubleysoft.leveldb4j;

import org.doubleysoft.leveldb4j.api.index.IDbIndex;
import org.doubleysoft.leveldb4j.bitcast.util.IDbFileReader;
import org.doubleysoft.leveldb4j.api.storage.IDbDataReader;
import org.doubleysoft.leveldb4j.bitcast.index.IDbIndexImpl;
import org.doubleysoft.leveldb4j.bitcast.util.impl.IDbFileReaderLocalImpl;
import org.doubleysoft.leveldb4j.bitcast.storage.IDbDataReaderSequnceImpl;

public class GlobalConfig {
    public static String CHART_SET = "utf-8";

    public static long MAX_FILE_SIZE = 1024*2014l;

    public static String DB_ROOT_PATH = "./";





}