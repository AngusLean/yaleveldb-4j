package org.doubleysoft.leveldb4j.api.storage;

import org.doubleysoft.leveldb4j.api.domain.IData;

/**
 * 存储数据
 */
public interface IStorageData {

    /**
     * save data to database file
     *
     * @param data         data content
     * @param iDbFileWriter  database file writer
     */
    void saveData(IData data, IDbFileWriter iDbFileWriter);

}
