package org.doubleysoft.leveldb4j.bitcast.manager;

import org.apache.commons.io.FileUtils;
import org.doubleysoft.leveldb4j.GlobalConfig;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class DbFileStorageManagerMultiTest {
    private static AtomicInteger atomicInteger = new AtomicInteger();
    private static String dbPath;


    @Before
    public void initDbFileStorageManager() {
        dbPath = "./dbmm" + atomicInteger.getAndIncrement() + "/";
        DbFileStorageManager.init(dbPath);
    }

    @After
    public void cleanDbFileStorageManager() throws IOException {
        DbFileStorageManager.closeAllFile();
        FileUtils.deleteDirectory(new File(dbPath));
    }

    /**
     * add many data
     */
    @Test
    public void testRefreshDbFileActiceId() {
        Assert.assertEquals(1, DbFileStorageManager.getActiveDbFileId());

        Assert.assertEquals(0, DbFileStorageManager.getAndIncrementCurrentActiveSize(11));
        Assert.assertEquals(11, DbFileStorageManager.getAndIncrementCurrentActiveSize(11));
        Assert.assertEquals(0, DbFileStorageManager.getAndIncrementCurrentActiveSize(GlobalConfig.MAX_FILE_SIZE));
        Assert.assertEquals(2, DbFileStorageManager.getActiveDbFileId());

        //should create a new file and return new id
        Assert.assertEquals(0, DbFileStorageManager.getAndIncrementCurrentActiveSize(GlobalConfig.MAX_FILE_SIZE));
        Assert.assertEquals(3, DbFileStorageManager.getActiveDbFileId());
        //add 1 byte to data, it will create a new file id
        DbFileStorageManager.getAndIncrementCurrentActiveSize(1);
        Assert.assertEquals(4, DbFileStorageManager.getActiveDbFileId());

        //now insert max length data, but it should not create a new file id
        Assert.assertEquals(0, DbFileStorageManager.getAndIncrementCurrentActiveSize(GlobalConfig.MAX_FILE_SIZE));
        Assert.assertEquals(5, DbFileStorageManager.getActiveDbFileId());

        //add 1 byte to data, it should create a new file id
        Assert.assertEquals(0, DbFileStorageManager.getAndIncrementCurrentActiveSize(1));
        Assert.assertEquals(6, DbFileStorageManager.getActiveDbFileId());
    }
}
