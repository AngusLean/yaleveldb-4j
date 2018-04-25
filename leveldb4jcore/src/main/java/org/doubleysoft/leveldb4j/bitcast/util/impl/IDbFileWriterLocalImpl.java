package org.doubleysoft.leveldb4j.bitcast.util.impl;

import org.doubleysoft.leveldb4j.api.exceptions.DataAccessException;
import org.doubleysoft.leveldb4j.bitcast.util.IDbFileWriter;
import org.doubleysoft.leveldb4j.common.log.Log;
import org.doubleysoft.leveldb4j.common.log.LogFactory;
import org.doubleysoft.leveldb4j.common.util.FileUtils;

import java.io.*;

/**
 * local file storage
 */
public class IDbFileWriterLocalImpl implements IDbFileWriter {

    private static Log log = LogFactory.getLog(IDbFileWriterLocalImpl.class);

    private DataOutputStream dbOutPutStream;

    public IDbFileWriterLocalImpl(String filePath) {
        FileUtils.createOnNotExists(filePath);

        File dbFile = new File(filePath);

        try {
            dbOutPutStream = new DataOutputStream(new FileOutputStream(dbFile, true));
        } catch (FileNotFoundException e) {
            log.warn("create db op stream fail, db not exist");
            throw new DataAccessException("create db op stream fail, db not exist");
        }
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
        try {
            dbOutPutStream.write(bytes);
        } catch (IOException e) {
            log.warn("write data wrong!",e);
            throw new DataAccessException("write data wrong!");
        }
    }

    /**
     * append int value to specified file
     *
     * @param len
     */
    @Override
    public void appendInt(int len) {
        try {
            dbOutPutStream.writeInt(len);
        } catch (IOException e) {
            log.warn("write data wrong!");
            throw new DataAccessException("write data wrong!");
        }
    }

    @Override
    public void appendLong(long time){

        try {
            dbOutPutStream.writeLong(time);
        }  catch (IOException e) {
            log.warn("write data wrong!");
            throw new DataAccessException("write data wrong!");
        }
    }

    @Override
    public void close() {
        try {
            dbOutPutStream.close();
        } catch (IOException e) {
            log.warn("close data outputstream wrong!");
            throw new DataAccessException("close data outputstream wrong!");
        }
    }


}
