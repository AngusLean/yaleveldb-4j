package org.doubleysoft.leveldb4j.common.util;

import org.doubleysoft.leveldb4j.api.exceptions.DataAccessException;
import org.doubleysoft.leveldb4j.api.exceptions.ExceptionEnum;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    public static byte[] readByteArray(String filePath, int begin, int len) throws IOException {
        DataInputStream dataInputStream = null;
        try {
            dataInputStream = new DataInputStream(new FileInputStream(filePath));
            byte[] bytes = new byte[len];
            dataInputStream.readFully(bytes, begin, len);
            return bytes;
        } finally {
            if(dataInputStream != null){
                dataInputStream.close();
            }
        }

    }

    public static void createOnNotExists(String filePath){
        Path path = Paths.get(filePath);
        if(Files.notExists(path,LinkOption.NOFOLLOW_LINKS)){
            try {
                Files.createFile(path);
            } catch (IOException e) {
                throw new DataAccessException(ExceptionEnum.CAN_NOT_CREATE_DB_FILE);
            }
        }
    }



}
