package org.doubleysoft.leveldb4j.bitcast.manager;

import org.doubleysoft.leveldb4j.GlobalConfig;
import org.doubleysoft.leveldb4j.api.exceptions.DataAccessException;
import org.doubleysoft.leveldb4j.api.exceptions.ExceptionEnum;
import org.doubleysoft.leveldb4j.bitcast.index.IDbIndexBuilder;
import org.doubleysoft.leveldb4j.bitcast.util.IDbFileReader;
import org.doubleysoft.leveldb4j.bitcast.util.IDbFileWriter;
import org.doubleysoft.leveldb4j.bitcast.util.impl.IDbFileReaderLocalImpl;
import org.doubleysoft.leveldb4j.bitcast.util.impl.IDbFileWriterLocalImpl;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author anguslean
 * @Description
 * @Date 2018/4/20
 */
public class DbFileStorageManager {
    private static String currentDbName;
    private static String currentDbPath;
//    private static String currentDbIndexPath;

    private static AtomicInteger fileIndex;

    private static IDbFileReader iDbFileReader;
    private static IDbFileWriter iDbFileWriter;
    private static IDbFileWriter iDbIndexFileWriter;
    /**
     * current active file size
     */
    private static long activeFileSize;

    static {
        fileIndex = new AtomicInteger();
        currentDbName = "bc"+fileIndex.getAndIncrement();
        currentDbPath = GlobalConfig.DB_ROOT_PATH + currentDbName;

        String currentDbIndexPath = GlobalConfig.DB_ROOT_PATH + "bc.hint";
        //init index
        IDbIndexBuilder.buildIndexFromHint(currentDbIndexPath);
        iDbIndexFileWriter = new IDbFileWriterLocalImpl(currentDbIndexPath);
    }

    public static IDbFileReader getDbFileReader(){
        if(iDbFileReader == null){
            iDbFileReader = new IDbFileReaderLocalImpl(currentDbPath);
        }
        return iDbFileReader;
    }

    public static IDbFileWriter getDbFileWriter(){
        if(iDbFileWriter == null){
            iDbFileWriter = new IDbFileWriterLocalImpl(currentDbPath);
        }
        return iDbFileWriter;
    }


    public static int getActiveFileId(){
        return fileIndex.get();
    }

    public static IDbFileWriter getIDbIndexFileWriter(){
        return iDbIndexFileWriter;
    }


    /**
     * get current active file size
     * @param dataLen data size which will be added to current file
     * @return if current active file size greater than max file size, then a new file will be create.
     */
    public static long getActiveFileSize(long dataLen) {
        //if the data is too long to save
        if(dataLen > GlobalConfig.MAX_FILE_SIZE){
            throw new DataAccessException(ExceptionEnum.DATA_IS_TOO_LONG);
        }
        long val = activeFileSize;
        if(activeFileSize + dataLen > GlobalConfig.MAX_FILE_SIZE){
            // change current file path
            refreshCurrent();
            return 0;
        }
        //set new active file size
        activeFileSize = activeFileSize + dataLen;
        return val;
    }

    private static void refreshCurrent(){
        currentDbPath = GlobalConfig.DB_ROOT_PATH + currentDbName + fileIndex.getAndIncrement();
        if(iDbFileReader != null){
            iDbFileReader.close();
            iDbFileReader = new IDbFileReaderLocalImpl(currentDbPath);
        }
        if(iDbFileWriter != null){
            iDbFileWriter.close();
            iDbFileWriter = new IDbFileWriterLocalImpl(currentDbPath);
        }
    }


}
