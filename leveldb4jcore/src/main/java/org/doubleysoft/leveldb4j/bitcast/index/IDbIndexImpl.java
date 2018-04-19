package org.doubleysoft.leveldb4j.bitcast.index;

import org.doubleysoft.leveldb4j.api.domain.IData;
import org.doubleysoft.leveldb4j.api.index.IDbIndex;
import org.doubleysoft.leveldb4j.api.storage.IDbFileReader;
import org.doubleysoft.leveldb4j.api.storage.IReadData;
import org.doubleysoft.leveldb4j.bitcast.IDataKVImpl;
import org.doubleysoft.leveldb4j.bitcast.storage.IDbFileReaderLocalImpl;
import org.doubleysoft.leveldb4j.bitcast.storage.IReadDataImpl;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author anguslean
 * @Description
 * @Date 2018/4/19
 */
public class IDbIndexImpl implements IDbIndex {
    private ConcurrentHashMap<String, DataLocationModel> index = new ConcurrentHashMap<>();

    /**
     * find data by key
     *
     * @param key
     * @return
     */
    @Override
    public IData findData(String key) {
        if(!index.contains(key)){
            return null;
        }
        DataLocationModel locationModel = index.get(key);
        return null;
    }

    private IData readDbFile(String fileId, long dataPos){
        IData data = new IDataKVImpl();
        IDbFileReader iDbFileReader = new IDbFileReaderLocalImpl(fileId);
        IReadData iReadData = new IReadDataImpl();
        iReadData.readData(data, iDbFileReader, dataPos);
        return data;
    }

    private class DataLocationModel {
        /**
         * 文件ID
         */
        String fileId;

        /**
         * 文件位置
         */
        long dataPos;
    }
}
