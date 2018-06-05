package org.doubleysoft.leveldb4j.bitcast.storage;

import org.doubleysoft.leveldb4j.api.domain.DataIndex;
import org.doubleysoft.leveldb4j.api.storage.IData;
import org.doubleysoft.leveldb4j.api.storage.IDbDataWriter;
import org.doubleysoft.leveldb4j.bitcast.BitCastContainer;
import org.doubleysoft.leveldb4j.bitcast.manager.DbFileStorageManager;
import org.doubleysoft.leveldb4j.bitcast.util.IDbFileWriter;

/**
 * @author anguslean
 * @Date 2018/4/19
 */

public class IDbDataWriterSequnceImpl implements IDbDataWriter {


    /**
     * save data to database file, this method's implementation should ensure data is saved correctly.
     * timestamp + opType + key_len + key + value_len + value
     * @param data data that should be saved
     */
    @Override
    public void saveData(IData data) {
        long dataLength = data.getKeyLen() + data.getValLen() + 8;
        //try to get current active file size, if data is too long , it will create a new file
        //call this method before write to db file
        long dataPos = DbFileStorageManager.getAndIncrementCurrentActiviSize(dataLength);

        IDbFileWriter iDbFileWriter = DbFileStorageManager.getDbFileWriter();
        iDbFileWriter.appendInt(data.getKeyLen());
        iDbFileWriter.appendBytes(data.getKeyBytes());
        iDbFileWriter.appendInt(data.getValLen());
        iDbFileWriter.appendBytes(data.getValBytes());
        iDbFileWriter.flush();
        saveIndex(data, dataPos);
    }


    private void saveIndex(IData data, long dataPos) {
        DataIndex dataIndex = new DataIndex();
        //first get file size, if current file is too large to save, it will create a new file
        dataIndex.setDataPos(dataPos);
        dataIndex.setKey(data.getKey());
        //then get newest file id
        dataIndex.setFileId(DbFileStorageManager.getActiveFileId());
        BitCastContainer.getDbIndex().syncIndex(dataIndex);
    }
}
