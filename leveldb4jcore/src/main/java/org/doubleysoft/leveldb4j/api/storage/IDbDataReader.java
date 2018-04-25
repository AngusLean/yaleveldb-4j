
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
     * @return
     */
    void readData(IData data);

    /**
     * read data from db file in specified position
     * @param data
     * @param filePos begin position of data in file
     * @return
     */
    void readData(IData data, long filePos);
}
