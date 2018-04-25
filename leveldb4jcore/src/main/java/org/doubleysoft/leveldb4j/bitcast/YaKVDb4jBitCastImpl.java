package org.doubleysoft.leveldb4j.bitcast;

import org.doubleysoft.leveldb4j.GlobalConfig;
import org.doubleysoft.leveldb4j.api.YaKVDb4j;
import org.doubleysoft.leveldb4j.api.index.IDbIndex;
import org.doubleysoft.leveldb4j.api.storage.IData;
import org.doubleysoft.leveldb4j.api.storage.IDbDataReader;
import org.doubleysoft.leveldb4j.api.storage.IDbDataWriter;
import org.doubleysoft.leveldb4j.bitcast.index.IDbIndexImpl;
import org.doubleysoft.leveldb4j.bitcast.storage.IDbDataReaderSequnceImpl;
import org.doubleysoft.leveldb4j.bitcast.storage.IDbDataWriterSequnceImpl;

/**
 * @author anguslean
 * @Description
 * @Date 2018/4/25
 */
public class YaKVDb4jBitCastImpl implements YaKVDb4j<String> {
    private IDbDataWriter iDbDataWriter;

    public YaKVDb4jBitCastImpl(){
        iDbDataWriter = new IDbDataWriterSequnceImpl();
    }

    /**
     * add a key-value to database
     *
     * @param key
     * @param val
     */
    @Override
    public void put(String key, String val) {
        IData data = new IDataKVImpl(key, val);
        iDbDataWriter.saveData(data);
    }

    /**
     * get a value by key
     *
     * @param key
     * @return
     */
    @Override
    public String get(String key) {
        IData data = new IDataKVImpl();
        BitCastContext.getDbIndex().findData(data, key);
        return ((IDataKVImpl) data).getVal();
    }

    /**
     * delete a storaged data
     *
     * @param key
     */
    @Override
    public void delete(String key) {

    }
}
