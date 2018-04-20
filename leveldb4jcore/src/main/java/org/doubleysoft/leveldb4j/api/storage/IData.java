package org.doubleysoft.leveldb4j.api.storage;

/**
 * data inteface
 * @author anguslean
 */
public interface IData<T> {

    /**
     * read key length in byte
     * @return key length
     */
    int getKeyLen();

    /**
     * get key byte[]
     * @return key bytes
     */
    byte[] getKeyBytes();

    /**
     * get value len in byte
     * @return value length
     */
    int getValLen();

    /**
     * get value byte[]
     * @return value bytes
     */
    byte[] getValBytes();

    /**
     * read key to current data object
     * @param keyLen key length
     * @param key key bytes
     */
    void readKey(int keyLen, byte[] key);

    /**
     * read value to current data object
     * @param valLen value length
     * @param val value bytes
     */
    void readVal(int valLen, byte[] val);

    /**
     * get key
     * @return key
     */
    String getKey();

    /**
     * get value
     * @return
     */
    T getVal();
}
