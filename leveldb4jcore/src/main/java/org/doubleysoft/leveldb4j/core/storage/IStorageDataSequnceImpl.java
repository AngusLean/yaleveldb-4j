package org.doubleysoft.leveldb4j.core.storage;

import org.doubleysoft.leveldb4j.common.ByteUtils;
import org.doubleysoft.leveldb4j.core.domain.IData;

/**
 * 序列化存储实现
 * 保存数据的结构为 数据长度+数据， 不使用任何分隔符
 */
public class IStorageDataSequnceImpl implements IStorageData {

    /**
     * 保存数据
     *
     * @param data 数据内容
     */
    @Override
    public void saveData(IData data, IStorageFile iStorageFile) {
        //todo 优化保存时多余不必要的字节以及保存效率
        int len = data.getDataSize();
        //append data size in byte
        byte[] val = ByteUtils.convertInt2ByteArr(len);
        iStorageFile.appendBytes(val);
        //append data in bytes
        iStorageFile.appendBytes(data.getDataBytes());
    }
}
