package org.doubleysoft.leveldb4j.bitcast.storage;

import org.doubleysoft.leveldb4j.api.storage.IData;
import org.doubleysoft.leveldb4j.api.storage.IDbFileReader;
import org.doubleysoft.leveldb4j.api.storage.IDbDataReader;

/**
 * @author anguslean
 * @Description
 * @Date 2018/4/19
 */
public class IDbDataReaderImpl implements IDbDataReader {

    /**
     * read data from db file
     * @param data
     * @param iDbFileReader
     * @return
     */
    @Override
    public void readData(IData data, IDbFileReader iDbFileReader) {
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
    public void readData(IData data, IDbFileReader iDbFileReader, long filePos) {
        iDbFileReader.seek(filePos);
        readData(data, iDbFileReader);
    }
}
