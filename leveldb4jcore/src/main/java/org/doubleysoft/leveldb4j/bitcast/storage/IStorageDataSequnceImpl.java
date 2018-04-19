package org.doubleysoft.leveldb4j.bitcast.storage;

import org.doubleysoft.leveldb4j.api.storage.IDbFileReader;
import org.doubleysoft.leveldb4j.api.storage.IDbFileWriter;
import org.doubleysoft.leveldb4j.api.storage.IStorageData;
import org.doubleysoft.leveldb4j.api.domain.IData;

/**
 * 序列化存储实现
 * 保存数据的结构为 数据长度+数据， 不使用任何分隔符
 */
public class IStorageDataSequnceImpl implements IStorageData {

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
    }

}
