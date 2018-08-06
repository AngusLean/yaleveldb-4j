package org.doubleysoft.leveldb4j.bitcast.manager;

import org.apache.commons.io.FileUtils;
import org.doubleysoft.leveldb4j.GlobalConfig;
import org.doubleysoft.leveldb4j.api.YaKVDb4j;
import org.doubleysoft.leveldb4j.bitcast.BitCastContext;
import org.doubleysoft.leveldb4j.bitcast.model.DbStorageUnitModel;
import org.doubleysoft.leveldb4j.bitcast.util.StorageUtil;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("unchecked")
public class ActiveStorageUnitBaseTest {
    private String relativePath = "./test/";

    @AfterClass
    public static void clean() {
        StorageUtil.cleanTestBaseDie();

    }

    @After
    public void cleanThis() {
        FileUtils.deleteQuietly(new File(relativePath));
    }

    @Test
    public void getNotExistDbfile() {
        DbStorageUnitModel model = ActiveStorageUnitManager.getInstance().initPath(relativePath);
        Assert.assertEquals(1, model.getIndex());
        String abspath = Paths.get(relativePath + GlobalConfig.DB_FILE_NAME + "1").normalize().toAbsolutePath().toString();
        Assert.assertEquals(abspath, model.getAbsPath());
        Assert.assertEquals(GlobalConfig.DB_FILE_NAME + "1", model.getName());

        //get init model twice
        DbStorageUnitModel model1 = ActiveStorageUnitManager.getInstance().initPath(relativePath);
        Assert.assertEquals(1, model1.getIndex());
        String abspath1 = Paths.get(relativePath + GlobalConfig.DB_FILE_NAME + "1").normalize().toAbsolutePath().toString();
        Assert.assertEquals(abspath1, model1.getAbsPath());
        Assert.assertEquals(GlobalConfig.DB_FILE_NAME + "1", model1.getName());
        Assert.assertEquals(model, model1);
    }

    @Test
    public void getExistDbfile() {
        DbStorageUnitModel model = ActiveStorageUnitManager.getInstance().initPath(relativePath);
        Assert.assertEquals(1, model.getIndex());
        String abspath = Paths.get(relativePath + GlobalConfig.DB_FILE_NAME + "1").normalize().toAbsolutePath().toString();
        Assert.assertEquals(abspath, model.getAbsPath());
        Assert.assertEquals(GlobalConfig.DB_FILE_NAME + "1", model.getName());

        YaKVDb4j yaKVDb4j = BitCastContext.init(relativePath);
        yaKVDb4j.put("123e", "111");

        //get init model twice
        DbStorageUnitModel model1 = ActiveStorageUnitManager.getInstance().initPath(relativePath);
        Assert.assertEquals(1, model1.getIndex());
        String abspath1 = Paths.get(relativePath + GlobalConfig.DB_FILE_NAME + "1").normalize().toAbsolutePath().toString();
        Assert.assertEquals(abspath1, model1.getAbsPath());
        Assert.assertEquals(GlobalConfig.DB_FILE_NAME + "1", model1.getName());
        Assert.assertEquals(model, model1);
    }

    @Test
    public void haveDbFiles() {
        Random random = new Random();
        String relativePath = "./test1" + random.nextInt() + "/";
        for (int i = 0; i < 5; i++) {
            String tempPath = relativePath + GlobalConfig.DB_FILE_NAME + i;
            try {
                Path path = Paths.get(tempPath).normalize().toAbsolutePath();
                File parent = path.getParent().toFile();
                if (!parent.exists()) {
                    parent.mkdir();
                }
                path.toFile().createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /**
         * init from database directory
         */
        DbStorageUnitModel model = ActiveStorageUnitManager.getInstance().initPath(relativePath);
        Assert.assertEquals(model.getIndex(), 4);
        FileUtils.deleteQuietly(new File(relativePath));
    }

    @Test
    public void getInstance() {
        Assert.assertEquals(ActiveStorageUnitManager.getInstance(), ActiveStorageUnitManager.getInstance());
        assertEquals(ActiveStorageUnitManager.getInstance(), ActiveStorageUnitManager.getInstance());
        assertEquals(ActiveStorageUnitManager.getInstance(), ActiveStorageUnitManager.getInstance());
    }
}
