package org.doubleysoft.leveldb4j.api.index;

import org.doubleysoft.leveldb4j.api.domain.DataIndex;
import org.doubleysoft.leveldb4j.api.storage.IData;

/**
 * @author anguslean
 * @Description database index。 the instance of this class should be only one
 * @Date 2018/4/19
 */
public interface IDbIndex {

    /**
     * find data by key
     * @param iData data object which storage finded data, if not exist, data is null
     * @param key find key
     * @return void
     */
    void findData(IData iData, String key);

    /**
     * add a index
     * @param dataIndex
     */
    void addIndex( DataIndex dataIndex);

    /**
     * add a index and sync to file
     *
     * @param dataIndex
     */
    void syncIndex(DataIndex dataIndex);
}
