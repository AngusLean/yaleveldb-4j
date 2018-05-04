package org.doubleysoft.leveldb4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author anguslean
 * @Date 2018/3/26
 */
public class TestBase {

    private static final String DB_PATH = "./db/";

    protected String getDbPath(String dbName){
        File file = new File(DB_PATH);
        if(!file.exists()){
            file.mkdir();
        }
        return DB_PATH+dbName;
    }

    protected String overrideDbPath(String dbName){
        String dbPath = getDbPath(dbName);
        File file = new File(dbPath);

        try {
            Files.deleteIfExists(Paths.get(dbPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dbPath;
    }

    protected void cleanDbPath(String dbPath){
        File file = new File(dbPath);

        try {
            Files.deleteIfExists(Paths.get(dbPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
