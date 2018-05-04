package org.doubleysoft.leveldb4j.bitcast.storage;

import org.doubleysoft.leveldb4j.api.exceptions.DataAccessException;
import org.doubleysoft.leveldb4j.api.exceptions.ExceptionEnum;
import org.doubleysoft.leveldb4j.api.storage.IData;
import org.doubleysoft.leveldb4j.api.storage.IDbDataReader;
import org.doubleysoft.leveldb4j.bitcast.manager.DbFileStorageManager;
import org.doubleysoft.leveldb4j.bitcast.util.IDbFileReader;
import org.doubleysoft.leveldb4j.common.log.Log;
import org.doubleysoft.leveldb4j.common.log.LogFactory;

/**
 * @author anguslean
 * @Date 2018/4/19
 */
public class IDbDataReaderSequnceImpl implements IDbDataReader {
    private static final Log log = LogFactory.getLog(IDbDataReaderSequnceImpl.class);

    /**
     * read data from db file
     * @param data
     * @return
     */
    @Override
    public void readData(IData data) {
        readData(data, DbFileStorageManager.getDbFileReader());
    }

    @Override
    public void readData(IData data, long seekPos) {
        log.debug("read data: [" + data + "] from db file, seek is " + seekPos);
        IDbFileReader iDbFileReader = DbFileStorageManager.resetDbFileReaderWithSeek(seekPos);
        readData(data, iDbFileReader);
    }

    @Override
    public void readData(IData data, int fileId, long filePos) {
        IDbFileReader iDbFileReader = DbFileStorageManager.getSpecifiedDbFileReader(filePos, fileId);
        try {
            readData(data, iDbFileReader);
        } finally {
            iDbFileReader.close();
        }
    }

    private void readData(IData data, IDbFileReader iDbFileReader) {
        log.debug("read data: [" + data + "] from db file");
        if (iDbFileReader.avaliable() > 0){
            int keyLen = iDbFileReader.readInt();
            //error case
            if (keyLen < 0) {
                log.error("read key length from db file error");
                throw new DataAccessException(ExceptionEnum.READ_KEYLEN_FROM_FILE_ERROR);
            }
            byte[] keyBytes = iDbFileReader.readBytes(keyLen);
            data.readKey(keyLen, keyBytes);
            if (iDbFileReader.avaliable() > 0) {
                int valLen = iDbFileReader.readInt();
                byte[] valBytes = iDbFileReader.readBytes(valLen);
                data.readVal(valLen, valBytes);
            }
        }
    }
}
