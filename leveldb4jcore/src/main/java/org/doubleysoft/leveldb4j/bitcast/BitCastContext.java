package org.doubleysoft.leveldb4j.bitcast;

import org.doubleysoft.leveldb4j.api.index.IDbIndex;
import org.doubleysoft.leveldb4j.bitcast.index.IDbIndexImpl;

/**
 * @author anguslean
 * current bitcast instance context holder
 */

public class BitCastContext {
    private static IDbIndex iDbIndex;

    /**
     * get current instance's index, index is unique is each bitcast instance
     *
     * @return IDbIndex
     */
    public static IDbIndex getDbIndex(){
        if(iDbIndex == null){
            iDbIndex = new IDbIndexImpl();
        }
        return iDbIndex;
    }

}
