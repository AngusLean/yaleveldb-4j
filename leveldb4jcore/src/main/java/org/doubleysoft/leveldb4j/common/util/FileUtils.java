package org.doubleysoft.leveldb4j.common.util;

import org.doubleysoft.leveldb4j.api.exceptions.DataAccessException;
import org.doubleysoft.leveldb4j.api.exceptions.ExceptionEnum;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * @author anguslean
 * @Date 2018/4/19
 */
public class FileUtils {

    public static void createFileIfNotExists(String filePath) {
        Path path = Paths.get(filePath);

        if(Files.notExists(path,LinkOption.NOFOLLOW_LINKS)){
            try {
                Files.createFile(path);
            } catch (IOException e) {
                throw new DataAccessException(ExceptionEnum.CAN_NOT_CREATE_DB_FILE);
            }
        }
    }

    public static void createDirIfNotExists(String dictPath) {
        Path path = Paths.get(dictPath);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new DataAccessException(ExceptionEnum.CAN_NOT_CREATE_DB_FILE);
            }
        }
    }

    public static String getAbsPath(String path) {
        return Paths.get(path).normalize().toAbsolutePath().toString();
    }

    public static String getAbsPath(File file) {
        return getAbsPath(file.getAbsolutePath());
    }
}
