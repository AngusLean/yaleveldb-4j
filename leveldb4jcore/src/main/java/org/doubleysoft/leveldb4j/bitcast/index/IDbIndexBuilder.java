package org.doubleysoft.leveldb4j.bitcast.index;

import org.doubleysoft.leveldb4j.GlobalConfig;
import org.doubleysoft.leveldb4j.api.domain.DataIndex;
import org.doubleysoft.leveldb4j.bitcast.BitCastContainer;
import org.doubleysoft.leveldb4j.bitcast.util.IDbFileReader;
import org.doubleysoft.leveldb4j.bitcast.util.impl.IDbFileReaderLocalImpl;
import org.doubleysoft.leveldb4j.common.log.Log;
import org.doubleysoft.leveldb4j.common.log.LogFactory;

import java.io.UnsupportedEncodingException;

/**
 * @author anguslean
 * @Date 2018/4/25
 */
public class IDbIndexBuilder {
    private static Log log = LogFactory.getLog(IDbIndexBuilder.class);

    public static void buildIndexFromHint(String hintPath){
        IDbFileReader iDbFileReader = new IDbFileReaderLocalImpl(hintPath);
        while (iDbFileReader.avaliable() > 0){
            DataIndex dataIndex = new DataIndex();
            long keyLen = iDbFileReader.readLong();
            //todo fixme
            byte[] keyBytes = iDbFileReader.readBytes((int)keyLen);
            long pos = iDbFileReader.readLong();
            int fileId = iDbFileReader.readInt();

            try {
                dataIndex.setKey(new String(keyBytes, GlobalConfig.CHART_SET));
            } catch (UnsupportedEncodingException e) {
                log.error("error read index key from file " + hintPath);
                //ignore error index key
                continue;
            }
            dataIndex.setDataPos(pos);
            dataIndex.setFileId(fileId);
            BitCastContainer.getDbIndex().addIndex(dataIndex);
        }
        iDbFileReader.close();
    }
}
