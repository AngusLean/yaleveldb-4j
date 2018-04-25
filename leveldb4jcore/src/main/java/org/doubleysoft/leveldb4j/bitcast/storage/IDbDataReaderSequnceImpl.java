package org.doubleysoft.leveldb4j.bitcast.storage;

import org.doubleysoft.leveldb4j.api.storage.IData;
import org.doubleysoft.leveldb4j.bitcast.util.IDbFileReader;
import org.doubleysoft.leveldb4j.api.storage.IDbDataReader;
import org.doubleysoft.leveldb4j.bitcast.manager.DbFileStorageManager;

/**
 * @author anguslean
 * @Description
 * @Date 2018/4/19
 */
public class IDbDataReaderSequnceImpl implements IDbDataReader {

    /**
     * read data from db file
     * @param data
     * @return
     */
    @Override
    public void readData(IData data) {
        IDbFileReader iDbFileReader = DbFileStorageManager.getDbFileReader();
        if (iDbFileReader.avaliable() > 0){
            int keyLen = iDbFileReader.readInt();
            byte[] keyBytes = iDbFileReader.readBytes(keyLen);
            data.readKey(keyLen, keyBytes);
        }
        if (iDbFileReader.avaliable() > 0){
            int valLen = iDbFileReader.readInt();
            byte[] valBytes = iDbFileReader.readBytes(valLen);
            data.readVal(valLen, valBytes);
        }
    }

    @Override
    public void readData(IData data, long filePos) {
        IDbFileReader iDbFileReader = DbFileStorageManager.getDbFileReader();
        iDbFileReader.seek(filePos);
        readData(data);
    }
}
