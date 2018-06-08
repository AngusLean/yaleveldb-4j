package org.doubleysoft.leveldb4j.bitcast.manager;

import org.doubleysoft.leveldb4j.GlobalConfig;
import org.doubleysoft.leveldb4j.api.exceptions.DataAccessException;
import org.doubleysoft.leveldb4j.api.exceptions.ExceptionEnum;
import org.doubleysoft.leveldb4j.bitcast.model.DbStorageUnitModel;
import org.doubleysoft.leveldb4j.bitcast.util.IDbFileReader;
import org.doubleysoft.leveldb4j.bitcast.util.IDbFileWriter;
import org.doubleysoft.leveldb4j.bitcast.util.impl.IDbFileReaderLocalImpl;
import org.doubleysoft.leveldb4j.bitcast.util.impl.IDbFileWriterLocalImpl;
import org.doubleysoft.leveldb4j.common.util.FileUtils;

/**
 * @author anguslean
 * @Date 2018/4/20
 */
public class DbFileStorageManager1 {
    private static DbStorageUnitModel dbStorageUnitModel;

    /**
     * user custom db storage path
     */
    private static String userDefinedDbPath;


    /**
     * current active db file reader or writer
     */
    private static IDbFileReader iDbFileReader;
    private static IDbFileWriter iDbFileWriter;
    private static IDbFileWriter iDbIndexFileWriter;

    /**
     * current db index path
     */
    private static String currentDbIndexPath;

    /**
     * current active db file size
     */
    private static long activeFileSize;


    public static void init(String dbPath) {
        userDefinedDbPath = dbPath;
        activeFileSize = 0;
        dbStorageUnitModel = ActiveStorageUnitManager.getInstance().getStorageUnit(dbPath);

        FileUtils.createDirIfNotExists(dbPath);
        initCurrentDbFileStream(dbPath);
    }

    private static void initCurrentDbFileStream(String relativePath) {
        String crtPath = dbStorageUnitModel.getAbsPath();
        iDbIndexFileWriter = new IDbFileWriterLocalImpl(currentDbIndexPath);
        iDbFileReader = new IDbFileReaderLocalImpl(crtPath);
        iDbFileWriter = new IDbFileWriterLocalImpl(crtPath);
    }

    public static IDbFileReader getDbFileReader() {
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
        iDbFileReader = new IDbFileReaderLocalImpl(dbStorageUnitModel.getAbsPath());
        iDbFileReader.seek(pos);
        return iDbFileReader;
    }

    public static IDbFileReader getSpecifiedDbFileReader(long pos, int fileId) {
        String dbPath = userDefinedDbPath + GlobalConfig.DB_FILE_NAME + fileId;
        IDbFileReader iDbFileReader = new IDbFileReaderLocalImpl(dbPath);
        if (pos != 0) {
            iDbFileReader.seek(pos);
        }
        return iDbFileReader;
    }

    public static IDbFileWriter getDbFileWriter() {
        return iDbFileWriter;
    }

    public static int getActiveFileId() {
        return ActiveStorageUnitManager.getInstance().getActiveStorageUnit().getIndex();
    }

    public static IDbFileWriter getIDbIndexFileWriter() {
        return iDbIndexFileWriter;
    }


    /**
     * get current active file size. call this method before write to db file
     *
     * @param dataLen data size which will be added to current file
     * @return if current active file size greater than max file size, then a new file will be create.
     */
    public static long getAndIncrementCurrentActiviSize(long dataLen) {
        //if the data is too long to save
        if (dataLen > GlobalConfig.MAX_FILE_SIZE) {
            throw new DataAccessException(ExceptionEnum.DATA_IS_TOO_LONG);
        }
        long val = activeFileSize;
        if (activeFileSize + dataLen > GlobalConfig.MAX_FILE_SIZE) {
            ActiveStorageUnitManager.getInstance().incrementActiveStorageUnit(dbStorageUnitModel);
            // new file current size is datalen
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


}
