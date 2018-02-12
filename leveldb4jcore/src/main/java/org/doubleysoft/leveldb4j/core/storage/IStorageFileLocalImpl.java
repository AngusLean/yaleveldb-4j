package org.doubleysoft.leveldb4j.core.storage;

import org.doubleysoft.leveldb4j.core.exceptions.DataAccessException;
import org.doubleysoft.leveldb4j.core.log.Log;
import org.doubleysoft.leveldb4j.core.log.LogFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * local file storage
 */
public class IStorageFileLocalImpl implements IStorageFile {

    private static Log log = LogFactory.getLog(IStorageFileLocalImpl.class);

    private File localFile;

    public IStorageFileLocalImpl(String filePath) {
        File tmpFile = new File(filePath);
        if (!tmpFile.exists()) {
            try {
                boolean createRs = tmpFile.createNewFile();
                if (!createRs) {
                    log.warn("create db file fail!");
                    throw new DataAccessException("file path not exists!");
                }
            } catch (IOException e) {
                log.warn("create db file fail, try to restart");
                throw new DataAccessException("create db file fail, try to restart");
            }
        }
        this.localFile = tmpFile;
    }

    /**
     * append byte in specified file
     *
     * @param bytes
     */
    @Override
    public void appendBytes(byte[] bytes) {
        if (bytes == null) {
            return;
        }
        if (!this.localFile.canWrite()) {
            log.warn("can't write data to file");
            throw new DataAccessException("can't write data to file");
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(this.localFile, true)) {
            fileOutputStream.write(bytes);
        } catch (FileNotFoundException e) {
            log.warn("file not exists");
            throw new DataAccessException("file not exists");
        } catch (IOException e) {
            log.warn("write data wrong!");
            throw new DataAccessException("write data wrong!");
        }
    }
}
