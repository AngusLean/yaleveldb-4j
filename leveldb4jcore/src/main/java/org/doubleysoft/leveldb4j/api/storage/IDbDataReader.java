
package org.doubleysoft.leveldb4j.api.storage;

/**
 * @author anguslean
 * @Description read db data from files
 * @Date 2018/3/30
 */
public interface IDbDataReader {
    /**
     * read data from db file
     * @param data
     * @param iDbFileReader
     * @return
     */
    void readData(IData data, IDbFileReader iDbFileReader);

    /**
     * read data from db file in specified position
     * @param data
     * @param iDbFileReader
     * @param filePos begin position of data in file
     * @return
     */
    void readData(IData data, IDbFileReader iDbFileReader, long filePos);
}
