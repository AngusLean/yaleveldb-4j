package org.doubleysoft.leveldb4j.bitcast.index;

import org.doubleysoft.leveldb4j.GlobalConfig;
import org.doubleysoft.leveldb4j.api.domain.DataIndex;
import org.doubleysoft.leveldb4j.api.index.IDbIndex;
import org.doubleysoft.leveldb4j.api.storage.IData;
import org.doubleysoft.leveldb4j.api.storage.IDbDataReader;
import org.doubleysoft.leveldb4j.bitcast.BitCastContext;
import org.doubleysoft.leveldb4j.bitcast.manager.DbFileStorageManager;
import org.doubleysoft.leveldb4j.bitcast.util.IDbFileWriter;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author anguslean
 * @Description
 * @Date 2018/4/19
 */
public class IDbIndexImpl implements IDbIndex {
    private Map<String, DataIndex> index = new HashMap<>();
    private IDbDataReader iDbDataReader;

    public IDbIndexImpl() {
        iDbDataReader = BitCastContext.getDataReader();
    }

    /**
     * find data by key
     *
     * @param key
     * @return
     */
    @Override
    public void findData(IData iData, String key) {
        if (index.containsKey(key)) {
            DataIndex locationModel = index.get(key);
            readDbFileByIndex(iData, locationModel.getFileId(), locationModel.getDataPos());
        }
    }

    @Override
    public void addIndex(DataIndex dataIndex) {
        index.put(dataIndex.getKey(), dataIndex);
        saveIndexToIndexFile(dataIndex);
    }

    private void readDbFileByIndex(IData iData, int fileId, long dataPos) {
        iDbDataReader.readData(iData, dataPos);
    }

    private void saveIndexToIndexFile(DataIndex dataIndex) {
        try {
            IDbFileWriter indexFileWriter = DbFileStorageManager.getIDbIndexFileWriter();
            byte[] keyBytes = dataIndex.getKey().getBytes(GlobalConfig.CHART_SET);
            indexFileWriter.appendLong(keyBytes.length);
            indexFileWriter.appendBytes(keyBytes);
            indexFileWriter.appendLong(dataIndex.getDataPos());
            indexFileWriter.appendInt(dataIndex.getFileId());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
