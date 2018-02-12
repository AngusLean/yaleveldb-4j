package org.doubleysoft.leveldb4j.core.storage;

import org.doubleysoft.leveldb4j.core.domain.IData;

/**
 * 存储数据
 */
public interface IStorageData {

    /**
     * 保存数据
     *
     * @param data         数据内容
     * @param iStorageFile 数据库文件
     */
    void saveData(IData data, IStorageFile iStorageFile);
}
