package org.doubleysoft.leveldb4j.bitcast;

import org.doubleysoft.leveldb4j.api.index.IDbIndex;
import org.doubleysoft.leveldb4j.api.storage.IDbDataReader;
import org.doubleysoft.leveldb4j.bitcast.index.IDbIndexImpl;
import org.doubleysoft.leveldb4j.bitcast.storage.IDbDataReaderSequnceImpl;

/**
 * @author anguslean
 * @Description
 * @Date 2018/4/20
 */
public class BitCastContext {
    private static IDbIndex iDbIndex;


    public static IDbIndex getDbIndex(){
        if(iDbIndex == null){
            iDbIndex = new IDbIndexImpl();
        }
        return iDbIndex;
    }

    public static IDbDataReader getDataReader(){
        return new IDbDataReaderSequnceImpl();
    }
}
