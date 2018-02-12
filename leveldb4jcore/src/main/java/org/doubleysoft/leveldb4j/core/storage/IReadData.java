/**
 * @author anguslean
 * @date 2018/2/12
 */
package org.doubleysoft.leveldb4j.core.storage;

import org.doubleysoft.leveldb4j.core.domain.IData;

/**
 * read db data from files
 */
public interface IReadData {
    /**
     * read data from db file
     *
     * @param iStorageFile
     * @return
     */
    IData readData(IStorageFile iStorageFile);
}
