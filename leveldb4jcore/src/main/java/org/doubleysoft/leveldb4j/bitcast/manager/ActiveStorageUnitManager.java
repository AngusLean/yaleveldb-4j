package org.doubleysoft.leveldb4j.bitcast.manager;

import org.doubleysoft.leveldb4j.GlobalConfig;
import org.doubleysoft.leveldb4j.api.exceptions.DataAccessException;
import org.doubleysoft.leveldb4j.api.exceptions.ExceptionEnum;
import org.doubleysoft.leveldb4j.api.exceptions.InitialDataBaseException;
import org.doubleysoft.leveldb4j.bitcast.model.DbStorageUnitModel;
import org.doubleysoft.leveldb4j.common.log.Log;
import org.doubleysoft.leveldb4j.common.log.LogFactory;
import org.doubleysoft.leveldb4j.common.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * current active db file manager, only include the latest db file。
 */
class ActiveStorageUnitManager {

    private static final Log log = LogFactory.getLog(ActiveStorageUnitManager.class);
    /**
     * singleton
     */
    private static volatile ActiveStorageUnitManager instance;

    /**
     * current active file index
     */
    private AtomicInteger activeFileId;

    /**
     * current active storage unit
     */
    private DbStorageUnitModel activeStorageUnit;

    /**
     * db file path
     */
    private String relativePath;

    private ActiveStorageUnitManager() {
        activeFileId = new AtomicInteger(1);
        activeStorageUnit = new DbStorageUnitModel();
    }

    static ActiveStorageUnitManager getInstance() {
        if (instance == null) {
            synchronized (ActiveStorageUnitManager.class) {
                if (instance == null) {
                    instance = new ActiveStorageUnitManager();
                }
            }
        }
        return instance;
    }

    public DbStorageUnitModel initPath(String relativePath) {
        this.relativePath = relativePath;
        return initActiveStorageUnit();
    }

    public DbStorageUnitModel getActiveStorageUnit() {
        return activeStorageUnit;
    }

    /**
     * get a active storage unit from db storage path, this method will
     * iterator all files in this path, and mark the file which have the maxed
     * file index,  and create a model for it.
     * if this path cont't contain file , create a new storage model and new file
     *
     * @return
     */
    private DbStorageUnitModel initActiveStorageUnit() {
        File targetFile = Paths.get(relativePath).toFile();
        if (targetFile.isFile()) {
            throw new DataAccessException(ExceptionEnum.FILE_DIRECTORY_IS_ILLEGAL);
        }

        try {
            FileUtils.createDirIfNotExists(relativePath);
            //get db file last number
            Optional<Path> latestFile = Files.list(Paths.get(relativePath))
                    .filter(file -> file.toFile().getName().contains(GlobalConfig.DB_FILE_NAME)).min((o1, o2) -> {
                        String name1 = o1.toFile().getName();
                        String name2 = o2.toFile().getName();
                        //get db file last number
                        return -name1.substring(GlobalConfig.DB_FILE_NAME.length(), name1.length()).compareTo(
                                name2.substring(GlobalConfig.DB_FILE_NAME.length(), name2.length())
                        );
                    });
            if (!latestFile.isPresent()) {
                return newStorageUnit();
            }
            return getStorageModelByFile(latestFile.get().toFile());
        } catch (IOException e) {
            log.error("error in init database from path: " + relativePath, e);
            throw new InitialDataBaseException(ExceptionEnum.CAN_NOT_INIT_DB_FROM_PATH);
        }
    }

    /**
     * called when current unit is full, this method will create a auto increment
     * storage model
     *
     * @param oldUnit the old fulled unit
     * @return
     */
    public DbStorageUnitModel incActiveStorageUnit(DbStorageUnitModel oldUnit) {
        if (!activeStorageUnit.equals(oldUnit)) {
            log.error(" illegality db storage unit instance, not latest actived.old: " + activeStorageUnit + " new :" + oldUnit);
            throw new InitialDataBaseException("illegality db storage unit instance");
        }
        int activeNum = activeFileId.incrementAndGet();
        String dbName = getDbName(activeNum);

        activeStorageUnit.setAbsPath(getAbsPathByDbIndex(activeNum));
        activeStorageUnit.setName(dbName);
        activeStorageUnit.setIndex(activeNum);
        return activeStorageUnit;
    }

    public DbStorageUnitModel getDbStorageModel(int fileId) {
        ActiveStorageUnitManager model = new ActiveStorageUnitManager();
        String dbName = getDbName(fileId);
        activeStorageUnit.setAbsPath(getAbsPathByDbIndex(fileId));
        activeStorageUnit.setName(dbName);
        activeStorageUnit.setIndex(fileId);
        return activeStorageUnit;
    }

    public String getDbIndexPath() {
        return relativePath + GlobalConfig.DB_INDEX_FILE_NAME;
    }

    private DbStorageUnitModel newStorageUnit() {
        String name = getDbName(activeFileId.get());
        String absPath = getAbsPathByDbIndex(activeFileId.get());
        int activeNum = activeFileId.get();

        try {
            Files.createFile(Paths.get(absPath));
        } catch (IOException e) {
            log.error("error in create a new db storage file in path : " + relativePath);
            throw new InitialDataBaseException(ExceptionEnum.CAN_NOT_CREATE_DB_FILE);
        }

        activeStorageUnit.setIndex(activeNum);
        activeStorageUnit.setName(name);
        activeStorageUnit.setAbsPath(FileUtils.getAbsPath(absPath));
        return activeStorageUnit;
    }

    private DbStorageUnitModel getStorageModelByFile(File dbFile) {
        String name = dbFile.getName();
        activeStorageUnit = new DbStorageUnitModel();
        String lastNumStr = name.substring(GlobalConfig.DB_FILE_NAME.length(), name.length());
        Integer lastNum = Integer.valueOf(lastNumStr);

        activeFileId.set(lastNum);

        activeStorageUnit.setAbsPath(FileUtils.getAbsPath(dbFile));
        activeStorageUnit.setName(name);
        activeStorageUnit.setIndex(lastNum);
        return activeStorageUnit;
    }

    private String getAbsPathByDbIndex(Integer index) {
        return relativePath + getDbName(index);
    }

    private String getDbName(Integer index) {
        return GlobalConfig.DB_FILE_NAME + index;
    }

}
