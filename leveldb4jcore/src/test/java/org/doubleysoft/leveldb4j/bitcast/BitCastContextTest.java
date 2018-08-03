package org.doubleysoft.leveldb4j.bitcast;

import org.apache.commons.io.FileUtils;
import org.doubleysoft.leveldb4j.GlobalConfig;
import org.doubleysoft.leveldb4j.api.YaKVDb4j;
import org.doubleysoft.leveldb4j.bitcast.manager.DbFileStorageManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dongyang.yu
 * @email dongyang.yu@anxincloud.com
 */
public class BitCastContextTest {
    private static AtomicInteger atomicInteger = new AtomicInteger();
    private YaKVDb4j yaKVDb4j;
    private String path;

    @Before
    public void init() {
        path = "./dbBitCastImplTest" + atomicInteger.getAndIncrement() + "/";
        yaKVDb4j = BitCastContext.init(path);
    }

    @After
    public void resetEnv() {
        DbFileStorageManager.closeAllFile();
        FileUtils.deleteQuietly(new File(path));
    }

    @Test
    public void pathFileTest() {
        boolean hasIndexFile = false;
        for (File file : new File(path).listFiles()) {
            Assert.assertTrue(file.getName().contains(GlobalConfig.DB_FILE_NAME));
            if (file.getName().equals(GlobalConfig.DB_INDEX_FILE_NAME)) {
                hasIndexFile = true;
            }
        }
        Assert.assertTrue(hasIndexFile);
    }

    /**
     * simple test, the core logical test located in target implementation
     */
    @Test
    public void basicPutAndGet() {
        String key = "testkey";
        String val = "testval";

        yaKVDb4j.put(key, val);
        Assert.assertEquals(val, yaKVDb4j.get(key));
    }
}