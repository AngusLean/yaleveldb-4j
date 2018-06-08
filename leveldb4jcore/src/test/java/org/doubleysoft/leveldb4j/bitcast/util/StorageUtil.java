package org.doubleysoft.leveldb4j.bitcast.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class StorageUtil {

    public static String getTestBaseDir() {
        String path = System.getProperty("java.io.tmpdir") + "/yaleveldb/";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        return path;
    }

    public static void cleanTestBaseDie() {
        String base = getTestBaseDir();
        try {
            FileUtils.cleanDirectory(new File(base));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
