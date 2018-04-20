package org.doubleysoft.leveldb4j.bitcast.index;

import org.doubleysoft.leveldb4j.GlobalConfig;
import org.doubleysoft.leveldb4j.api.domain.DataIndex;
import org.doubleysoft.leveldb4j.api.storage.IData;
import org.doubleysoft.leveldb4j.api.index.IDbIndex;
import org.doubleysoft.leveldb4j.api.storage.IDbDataReader;
import org.doubleysoft.leveldb4j.bitcast.BitCastContext;
import org.doubleysoft.leveldb4j.bitcast.IDataKVImpl;
import org.doubleysoft.leveldb4j.common.TaskManager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author anguslean
 * @Description
 * @Date 2018/4/19
 */
public class IDbIndexImpl implements IDbIndex {
    private Map<String, DataIndex> index = new HashMap<>();
    private IDbDataReader iDbDataReader;

    public IDbIndexImpl(){
        iDbDataReader = GlobalConfig.getDataReader();
    }

    /**
     * find data by key
     *
     * @param key
     * @return
     */
    @Override
    public void findData(IData iData, String key) {
        if(index.containsKey(key)){
            DataIndex locationModel = index.get(key);
            readDbFileByIndex(iData, locationModel.getFileId(), locationModel.getDataPos());
        }
    }

    @Override
    public void addIndex(DataIndex dataIndex) {
        index.put(dataIndex.getKey(), dataIndex);
    }

    private void readDbFileByIndex(IData iData, String fileId, long dataPos){
        String dbPath = BitCastContext.getINSTANCE().getActiveFilePathById(fileId);
        iDbDataReader.readData(iData, GlobalConfig.getFileReader(dbPath), dataPos);
    }

    private void saveIndexToIndexFile(DataIndex dataIndex){
        TaskManager.addTask(new Runnable() {
            @Override
            public void run() {
                BitCastContext.getINSTANCE().getIndexFilePath();
            }
        });
    }

}
