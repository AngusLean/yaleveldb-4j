package org.doubleysoft.leveldb4j.bitcast.index;

import org.doubleysoft.leveldb4j.GlobalConfig;
import org.doubleysoft.leveldb4j.api.domain.DataIndex;
import org.doubleysoft.leveldb4j.api.exceptions.DataAccessException;
import org.doubleysoft.leveldb4j.api.exceptions.ExceptionEnum;
import org.doubleysoft.leveldb4j.api.index.IDbIndex;
import org.doubleysoft.leveldb4j.api.storage.IData;
import org.doubleysoft.leveldb4j.api.storage.IDbDataReader;
import org.doubleysoft.leveldb4j.bitcast.manager.DbFileStorageManager;
import org.doubleysoft.leveldb4j.bitcast.storage.IDbDataReaderSequnceImpl;
import org.doubleysoft.leveldb4j.bitcast.util.IDbFileWriter;
import org.doubleysoft.leveldb4j.common.log.Log;
import org.doubleysoft.leveldb4j.common.log.LogFactory;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author anguslean
 * @Date 2018/4/19
 */
public class IDbIndexImpl implements IDbIndex {
    private static final Log log = LogFactory.getLog(IDbIndexImpl.class);

    private Map<String, DataIndex> index;

    private IDbDataReader iDbDataReader;

    public IDbIndexImpl() {
        index = new HashMap<>();
        iDbDataReader = new IDbDataReaderSequnceImpl();
    }

    /**
     * find data by key
     * @param iData data object which storage finded data, if not exist, data is null
     * @param key find key
     * @return void
     */
    @Override
    public void findData(IData iData, String key) {
        if (index.containsKey(key)) {
            log.debug("query indexed key: " + key);
            DataIndex locationModel = index.get(key);
            log.debug("index key [" + key + "] , val: " + locationModel);
            readDbFileByIndex(iData, locationModel.getFileId(), locationModel.getDataPos());
        } else {
            log.debug(key + " does not been indexed");
        }
    }

    /**
     * add a index
     *
     * @param dataIndex
     */
    @Override
    public void addIndex(DataIndex dataIndex) {
        index.put(dataIndex.getKey(), dataIndex);
    }

    /**
     * add a index and sync to file
     *
     * @param dataIndex
     */
    @Override
    public void syncIndex(DataIndex dataIndex) {
        addIndex(dataIndex);
        saveIndex2IndexFile(dataIndex);
    }

    private void readDbFileByIndex(IData iData, int fileId, long dataPos) {
        iDbDataReader.readData(iData, fileId, dataPos);
    }

    private void saveIndex2IndexFile(DataIndex dataIndex) {
        try {
            IDbFileWriter indexFileWriter = DbFileStorageManager.getIDbIndexFileWriter();
            byte[] keyBytes = dataIndex.getKey().getBytes(GlobalConfig.CHART_SET);
            indexFileWriter.appendLong(keyBytes.length);
            indexFileWriter.appendBytes(keyBytes);
            indexFileWriter.appendLong(dataIndex.getDataPos());
            indexFileWriter.appendInt(dataIndex.getFileId());
            indexFileWriter.flush();
        } catch (UnsupportedEncodingException e) {
            log.error("save index to index file error, unsupported encoding, index is " + dataIndex);
            throw new DataAccessException(ExceptionEnum.CHAR_ERROR);
        }
    }

}
