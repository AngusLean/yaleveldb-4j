package org.doubleysoft.leveldb4j.api.storage;

/**
 * 存储数据
 */
public interface IDbDataWriter {

     /*
     * save data to database file
     *
     * @param data         data content
     * @param iDbFileWriter  database file writer
     */

    void saveData(IData data, IDbFileWriter iDbFileWriter);

}
