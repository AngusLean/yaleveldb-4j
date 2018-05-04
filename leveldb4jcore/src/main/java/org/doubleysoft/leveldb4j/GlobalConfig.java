package org.doubleysoft.leveldb4j;


/**
 * @author anguslean
 * @Date 2018/4/19
 */

public class GlobalConfig {

    /**
     * global db file base name
     */
    public static final String DB_FILE_NAME = "bc";

    /**
     * global db index name
     */
    public static final String DB_INDEX_NAME = "bc.hint";

    /**
     * global charset
     */
    public static String CHART_SET = "utf-8";

    /**
     * global default single db file max size
     */
    public static long MAX_FILE_SIZE = 1024 * 2014L;


}