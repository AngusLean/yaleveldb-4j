package org.doubleysoft.leveldb4j.api.storage;

/**
 * @author anguslean
 * storage database data interface
 */
public interface IDbDataWriter {

     /*
     * save data to database file
     *
     * @param data         data content
     * @param iDbFileWriter  database file writer
     */

    /**
     * save data to database file, this method's implementation should ensure data is saved correctly.
     *
     * @param data data that should be saved
     */
    void saveData(IData data);

}
