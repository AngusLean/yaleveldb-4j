package org.doubleysoft.leveldb4j.bitcast;

import org.doubleysoft.leveldb4j.GlobalConfig;
import org.doubleysoft.leveldb4j.api.exceptions.DataAccessException;
import org.doubleysoft.leveldb4j.api.exceptions.ExceptionEnum;
import org.doubleysoft.leveldb4j.bitcast.manager.DbFileManager;

/**
 * @author anguslean
 * @Description
 * @Date 2018/4/20
 */
public class BitCastContext {
    private static volatile BitCastContext INSTANCE;

    public static BitCastContext getINSTANCE(){
        if(INSTANCE == null){
            synchronized (BitCastContext.class){
                if(INSTANCE == null){
                    INSTANCE = new BitCastContext();
                }
            }
        }
        return INSTANCE;
    }


    /**
     * current index file
     */
    private String indexFilePath;

    /**
     * current active file size
     */
    private long activeFileSize;

    public String getIndexFilePath() {
        return indexFilePath;
    }

    /**
     * get current active file size
     * @param dataLen data size which will be added to current file
     * @return if current active file size greater than max file size, then a new file will be create.
     */
    public long getActiveFileSize(long dataLen) {
        //if the data is too long to save
        if(dataLen > GlobalConfig.MAX_FILE_SIZE){
            throw new DataAccessException(ExceptionEnum.DATA_IS_TOO_LONG);
        }
        if(activeFileSize + dataLen > GlobalConfig.MAX_FILE_SIZE){
            // change current file path
            DbFileManager.getNewDbFilePath();
            return 0;
        }
        return activeFileSize;
    }

    public String getActiveFilePathById(String fileId){
        return DbFileManager.getDbPathByFileId(fileId);
    }

    public String getActiveFileId(){
        return DbFileManager.getDbFileId();
    }
}
