package org.doubleysoft.leveldb4j.core.storage;

/**
 * storage file
 */
public interface IStorageFile {

    /**
     * append byte in specified file
     *
     * @param bytes
     */
    void appendBytes(byte[] bytes);

}
