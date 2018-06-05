package org.doubleysoft.leveldb4j.bitcast.manager;

import org.doubleysoft.leveldb4j.GlobalConfig;
import org.doubleysoft.leveldb4j.api.exceptions.ExceptionEnum;
import org.doubleysoft.leveldb4j.api.exceptions.InitialDataBaseException;
import org.doubleysoft.leveldb4j.bitcast.model.DbStorageUnitModel;
import org.doubleysoft.leveldb4j.common.log.Log;
import org.doubleysoft.leveldb4j.common.log.LogFactory;

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

    private ActiveStorageUnitManager() {
        activeFileId = new AtomicInteger(1);
        activeStorageUnit = new DbStorageUnitModel();
    }

    public static ActiveStorageUnitManager getInstance() {
        if (instance == null) {
            synchronized (ActiveStorageUnitManager.class) {
                if (instance == null) {
                    instance = new ActiveStorageUnitManager();
                }
            }
        }
        return instance;
    }

    /**
     * get a active storage unit from db storage path, this method will
     * iterator all files in this path, and mark the file which last number
     * is max as latest db file, and create a model for it.
     * if this path has't file exist, create a new storage model and new file
     *
     * @param relativePath
     * @return
     */
    public DbStorageUnitModel getStorageUnit(String relativePath) {
        try {
            Optional<Path> latestFile = Files.list(Paths.get(relativePath))
                    .filter(file -> GlobalConfig.DB_FILE_NAME.contains(file.toFile().getName()))
                    .sorted((o1, o2) -> {
                        String name1 = o1.toFile().getName();
                        String name2 = o2.toFile().getName();
                        //get db file last number
                        return -name1.substring(GlobalConfig.DB_FILE_NAME.length(), name1.length()).compareTo(
                                name2.substring(GlobalConfig.DB_FILE_NAME.length(), name2.length())
                        );
                    })
                    .findFirst();
            if (!latestFile.isPresent()) {
                return newStorageUnit(relativePath);
            }
            return getStorageModelByFile(latestFile.get().toFile());
        } catch (IOException e) {
            log.error("error in init database from path: " + relativePath);
            throw new InitialDataBaseException(ExceptionEnum.CAN_NOT_INIT_DB_FROM_PATH);
        }
    }

    /**
     * called only if current unit is full, this method will create a auto increment
     * storage model
     *
     * @param oldUnit the old fulled unit
     * @return
     */
    public DbStorageUnitModel incrementActiveStorageUnit(DbStorageUnitModel oldUnit) {
        if (!activeStorageUnit.equals(oldUnit)) {
            log.error(" illegality db storage unit instance, not latest actived.old: " + activeStorageUnit + " new :" + oldUnit);
            throw new InitialDataBaseException("illegality db storage unit instance");
        }
        String relativePath = oldUnit.getAbsPath().substring(0, oldUnit.getName().length());
        int activeNum = activeFileId.incrementAndGet();
        String dbName = getDbName(activeNum);

        activeStorageUnit.setAbsPath(relativePath + dbName);
        activeStorageUnit.setName(dbName);
        activeStorageUnit.setIndex(activeNum);
        return activeStorageUnit;
    }

    private DbStorageUnitModel newStorageUnit(String relativePath) {
        String name = getDbName(activeFileId.get());
        String absPath = relativePath + name;
        int activeNum = activeFileId.get();

        try {
            Files.createFile(Paths.get(absPath));
        } catch (IOException e) {
            log.error("error in create a new db storage file in path : " + relativePath);
            throw new InitialDataBaseException(ExceptionEnum.CAN_NOT_CREATE_DB_FILE);
        }

        activeStorageUnit.setIndex(activeNum);
        activeStorageUnit.setName(name);
        activeStorageUnit.setAbsPath(absPath);
        return activeStorageUnit;
    }

    private DbStorageUnitModel getStorageModelByFile(File dbFile) {
        String name = dbFile.getName();
        activeStorageUnit = new DbStorageUnitModel();
        String lastNumStr = name.substring(GlobalConfig.DB_FILE_NAME.length(), name.length());
        Integer lastNum = Integer.valueOf(lastNumStr);

        activeFileId.set(lastNum);

        activeStorageUnit.setAbsPath(dbFile.getAbsolutePath());
        activeStorageUnit.setName(name);
        activeStorageUnit.setIndex(lastNum);
        return activeStorageUnit;
    }

    private String getDbName(Integer index) {
        return GlobalConfig.DB_FILE_NAME + index;
    }

}
