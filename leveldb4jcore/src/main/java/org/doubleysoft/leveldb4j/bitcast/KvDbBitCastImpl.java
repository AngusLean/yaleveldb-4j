package org.doubleysoft.leveldb4j.bitcast;

import org.doubleysoft.leveldb4j.api.YaKVDb4j;
import org.doubleysoft.leveldb4j.api.storage.IData;
import org.doubleysoft.leveldb4j.api.storage.IDbDataWriter;
import org.doubleysoft.leveldb4j.bitcast.storage.IDbDataWriterSequnceImpl;

/**
 * @author anguslean
 * @Date 2018/4/25
 */
public class KvDbBitCastImpl implements YaKVDb4j<String> {
    private IDbDataWriter iDbDataWriter;

    public KvDbBitCastImpl() {
        iDbDataWriter = new IDbDataWriterSequnceImpl();
    }

    /**
     * add a key-value to database
     *
     * @param key string key
     * @param val string value
     */
    @Override
    public void put(String key, String val) {
        IData data = new IDataKVImpl(key, val);
        iDbDataWriter.saveData(data);
    }

    /**
     * get a value by key
     *
     * @param key string key
     * @return
     */
    @Override
    public String get(String key) {
        IData data = new IDataKVImpl();
        BitCastContainer.getDbIndex().findData(data, key);
        return ((IDataKVImpl) data).getVal();
    }

    /**
     * delete a storage data
     *
     * @param key string key
     */
    @Override
    public void delete(String key) {

    }
}
