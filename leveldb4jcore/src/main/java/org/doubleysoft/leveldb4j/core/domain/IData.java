package org.doubleysoft.leveldb4j.core.domain;

/**
 * data
 */
public interface IData<T> {

    /**
     * get data
     *
     * @return
     */
    T getData();

    /**
     * get data in bytes
     *
     * @return
     */
    byte[] getDataBytes();

    /**
     * current data size, include key and value
     *
     * @return
     */
    int getDataSize();
}
