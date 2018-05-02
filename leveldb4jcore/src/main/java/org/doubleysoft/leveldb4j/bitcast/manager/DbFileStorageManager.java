package org.doubleysoft.leveldb4j.bitcast.manager;

import org.doubleysoft.leveldb4j.GlobalConfig;
import org.doubleysoft.leveldb4j.api.exceptions.DataAccessException;
import org.doubleysoft.leveldb4j.api.exceptions.ExceptionEnum;
import org.doubleysoft.leveldb4j.bitcast.index.IDbIndexBuilder;
import org.doubleysoft.leveldb4j.bitcast.util.IDbFileReader;
import org.doubleysoft.leveldb4j.bitcast.util.IDbFileWriter;
import org.doubleysoft.leveldb4j.bitcast.util.impl.IDbFileReaderLocalImpl;
import org.doubleysoft.leveldb4j.bitcast.util.impl.IDbFileWriterLocalImpl;
import org.doubleysoft.leveldb4j.common.util.FileUtils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author anguslean
 * @Description
 * @Date 2018/4/20
 */
public class DbFileStorageManager {

    /**
     * current active file index
     */
    private static AtomicInteger fileIndex;

    private static String userDefinedDbPath;
    /**
     * current active db file name
     */
    private static String currentDbName;

    /**
     * current active db file path
     */
    private static String currentDbPath;

    /**
     * current db file reader or writer
     */
    private static IDbFileReader iDbFileReader;
    private static IDbFileWriter iDbFileWriter;
    private static IDbFileWriter iDbIndexFileWriter;

    /**
     * current db index path
     */
    private static String currentDbIndexPath;

    /**
     * current active file size
     */
    private static long activeFileSize;


    public static void init(String dbPath) {
        fileIndex = new AtomicInteger();
        userDefinedDbPath = dbPath;

        activeFileSize = 0;

        FileUtils.createDicIfNotExists(dbPath);
        refreshCurrentDbFilePath();
        initCurrentDbIndexFile();
        initCurrentDbFileStream();
    }

    private static void initCurrentDbFileStream() {
        iDbIndexFileWriter = new IDbFileWriterLocalImpl(currentDbIndexPath);
        iDbFileReader = new IDbFileReaderLocalImpl(currentDbPath);
        iDbFileWriter = new IDbFileWriterLocalImpl(currentDbPath);
    }

    public static IDbFileReader getDbFileReader(){
        return iDbFileReader;
    }

    /**
     * for seek op, recreate a read stream to avoid file point over
     *
     * @param pos
     * @return
     */
    public static IDbFileReader resetDbFileReaderWithSeek(long pos) {
        if (iDbFileReader != null) {
            iDbFileReader.close();
        }
        iDbFileReader = new IDbFileReaderLocalImpl(currentDbPath);
        iDbFileReader.seek(pos);
        return iDbFileReader;
    }

    public static IDbFileReader getSpecfiedDbFileRead(long pos, int fileId) {
       /* if(fileId == fileIndex.get()){
            return iDbFileReader;
        }*/
        String dbPath = userDefinedDbPath + "bc" + fileId;
        IDbFileReader iDbFileReader = new IDbFileReaderLocalImpl(dbPath);
        if (pos != 0) {
            iDbFileReader.seek(pos);
        }
        return iDbFileReader;
    }

    public static IDbFileWriter getDbFileWriter(){
        return iDbFileWriter;
    }

    public static int getActiveFileId(){
        return fileIndex.get();
    }

    public static IDbFileWriter getIDbIndexFileWriter(){
        return iDbIndexFileWriter;
    }


    /**
     * get current active file size. call this method before write to db file
     * @param dataLen data size which will be added to current file
     * @return if current active file size greater than max file size, then a new file will be create.
     */
    public static long getCurrentActiveFileSizeAndAddNewLen(long dataLen) {
        //if the data is too long to save
        if(dataLen > GlobalConfig.MAX_FILE_SIZE){
            throw new DataAccessException(ExceptionEnum.DATA_IS_TOO_LONG);
        }
        long val = activeFileSize;
        if(activeFileSize + dataLen > GlobalConfig.MAX_FILE_SIZE){
            // change current file path
            refreshCurrentDbFileStream();
            activeFileSize = dataLen;
            return 0;
        }
        //set new active file size
        activeFileSize = activeFileSize + dataLen;
        return val;
    }

    public static void closeAllFile() {
        getDbFileReader().close();
        getDbFileWriter().close();
        getIDbIndexFileWriter().close();
    }

    private static void refreshCurrentDbFilePath() {
        currentDbName = "bc" + fileIndex.incrementAndGet();
        currentDbPath = userDefinedDbPath + currentDbName;
    }

    private static void initCurrentDbIndexFile() {
        currentDbIndexPath = userDefinedDbPath + "bc.hint";
        IDbIndexBuilder.buildIndexFromHint(currentDbIndexPath);
    }

    private static void refreshCurrentDbFileStream() {
        refreshCurrentDbFilePath();
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
