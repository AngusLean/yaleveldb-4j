package org.doubleysoft.leveldb4j.core;

public class TestBase {

    protected static final String DB_PATH = "d:\\db\\";

    protected String getDbPath(String dbName){
        return DB_PATH+dbName;
    }
}
