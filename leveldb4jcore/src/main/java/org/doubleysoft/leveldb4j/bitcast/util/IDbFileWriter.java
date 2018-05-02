package org.doubleysoft.leveldb4j.bitcast.util;


/**
 * @author anguslean
 * @Description
 * @Date 2018/3/30
 */
public interface IDbFileWriter {

    /**
     * append byte to specified file
     *
     * @param bytes
     */
    void appendBytes(byte[] bytes);

    /**
     * append int value to specified file
     * @param len
     */
    void appendInt(int len);

    /**
     * append a long data to specified file
     * @param time
     */
    void appendLong(long time);

    /**
     * close current file channel
     */
    void close();

    /**
     * flush to file , call this after a save process complete
     */
    void flush();
}
