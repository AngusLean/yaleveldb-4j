package org.doubleysoft.leveldb4j.bitcast.util.impl;

import org.doubleysoft.leveldb4j.api.exceptions.DataAccessException;
import org.doubleysoft.leveldb4j.bitcast.util.IDbFileReader;
import org.doubleysoft.leveldb4j.common.log.Log;
import org.doubleysoft.leveldb4j.common.log.LogFactory;
import org.doubleysoft.leveldb4j.common.util.FileUtils;

import java.io.*;

/**
 * @author yd.yu
 * @Date 2018/3/30
 */
public class IDbFileReaderLocalImpl implements IDbFileReader {

    private static Log log = LogFactory.getLog(IDbFileWriterLocalImpl.class);

    private DataInputStream dbInputStream;

    public IDbFileReaderLocalImpl(String filePath) {

        FileUtils.createFileIfNotExists(filePath);

        try {
            File dbFile = new File(filePath);
            dbInputStream = new DataInputStream(new FileInputStream(dbFile));
        } catch (FileNotFoundException e) {
            log.warn("create db op stream fail, db not exist", e);
            throw new DataAccessException("create db op stream fail, db not exist");
        }
    }

    @Override
    public int avaliable() {
        try {
            return dbInputStream.available();
        } catch (IOException e) {
            log.warn("read input stream available byte error", e);
            throw new DataAccessException("read input stream available byte error");
        }
    }

    /**
     * read int val
     *
     * @return
     */
    @Override
    public int readInt() {
        try {
            return dbInputStream.readInt();
        } catch (IOException e) {
            log.warn("read int data wrong!", e);
            throw new DataAccessException("read int data wrong!");
        }
    }

    /**
     * read long val
     *
     * @return
     */
    @Override
    public long readLong() {
        try {
            return dbInputStream.readLong();
        } catch (IOException e) {
            log.warn("read long data wrong!", e);
            throw new DataAccessException("read long data wrong!");
        }
    }

    /**
     * read byte[] data
     *
     * @return
     */
    @Override
    public byte[] readBytes(int len) {
        try {
            byte[] bytes= new byte[len];
            dbInputStream.readFully(bytes, 0, bytes.length);
            return bytes;
        } catch (IOException e) {
            log.warn("read byte[] data wrong!", e);
            throw new DataAccessException("read byte[] data wrong!");
        }

    }



    @Override
    public void seek(long pos) {
        try {
            dbInputStream.skip(pos);
        } catch (IOException e) {
            log.warn("skip data pos wrong!", e);
            throw new DataAccessException("skip data pos wrong!!");
        }
    }

    /**
     * close current file channel
     */
    @Override
    public void close() {
        try {
            dbInputStream.close();
        } catch (IOException e) {
            log.warn("close data outputstream wrong!", e);
            throw new DataAccessException("close data outputstream wrong!");
        }
    }
}
