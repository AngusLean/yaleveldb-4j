
package org.doubleysoft.leveldb4j.api.storage;

import org.doubleysoft.leveldb4j.api.domain.IData;

/**
 * @author anguslean
 * @Description read db data from files
 * @Date 2018/3/30
 */
public interface IReadData {
    /**
     * read data from db file
     * @param data
     * @param iDbFileReader
     * @return
     */
    void readData(IData data, IDbFileReader iDbFileReader);

    /**
     * read data from db file in filepos
     * @param data
     * @param iDbFileReader
     * @param filePos begin position of data in file
     * @return
     */
    void readData(IData data, IDbFileReader iDbFileReader, long filePos);
}
