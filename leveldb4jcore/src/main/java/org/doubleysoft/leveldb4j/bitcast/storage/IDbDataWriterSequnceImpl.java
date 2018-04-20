package org.doubleysoft.leveldb4j.bitcast.storage;

import org.doubleysoft.leveldb4j.GlobalConfig;
import org.doubleysoft.leveldb4j.api.domain.DataIndex;
import org.doubleysoft.leveldb4j.api.storage.IDbDataWriter;
import org.doubleysoft.leveldb4j.api.storage.IDbFileWriter;
import org.doubleysoft.leveldb4j.api.storage.IData;
import org.doubleysoft.leveldb4j.bitcast.BitCastContext;

/**
 * 序列化存储实现
 * 保存数据的结构为 数据长度+数据， 不使用任何分隔符
 */
public class IDbDataWriterSequnceImpl implements IDbDataWriter {

    /**
     * 保存数据
     *
     * @param data 数据格式:
     *             timestamp + opType + key_len + key + value_len + value
     */
    @Override
    public void saveData(IData data, IDbFileWriter iDbFileWriter) {
        iDbFileWriter.appendInt(data.getKeyLen());
        iDbFileWriter.appendBytes(data.getKeyBytes());
        iDbFileWriter.appendInt(data.getValLen());
        iDbFileWriter.appendBytes(data.getValBytes());

        saveIndex(data);
    }


    private void saveIndex(IData data){
        DataIndex dataIndex = new DataIndex();
        long dataLength = data.getKeyLen()+data.getValLen()+8;
        //first get file size, if current file is too large to save, it will create a new file
        dataIndex.setDataPos(BitCastContext.getINSTANCE().getActiveFileSize(dataLength));
        dataIndex.setKey(data.getKey());
        //then get newest file id
        dataIndex.setFileId(BitCastContext.getINSTANCE().getActiveFileId());
        GlobalConfig.getDbIndex().addIndex(dataIndex);
    }
}
