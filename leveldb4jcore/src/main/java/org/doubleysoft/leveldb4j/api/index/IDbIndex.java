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
     * @param key
     * @return
     */
    void findData(IData iData, String key);

    /**
     * add a index
     * @param dataIndex
     */
    void addIndex( DataIndex dataIndex);
}
