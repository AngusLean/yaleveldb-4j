package org.doubleysoft.leveldb4j.bitcast.util;

/**
 * @author anguslean
 * @Description
 * @Date 2018/3/30
 */
public interface IDbFileReader {

    /**
     * avaliable byte to read
     * @return
     */
    int avaliable();

    /**
     * read int val
     * @return
     */
    int readInt();

    /**
     * read long val
     * @return
     */
    long readLong();

    /**
     * read byte[] data
     * @return
     */
    byte[] readBytes(int len);

    /**
     * skip some length
     * @param pos
     */
    void seek(long pos);

    /**
     * close current file channel
     */
    void close();

}
