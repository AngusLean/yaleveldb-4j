package org.doubleysoft.leveldb4j.bitcast.manager;

import org.apache.commons.io.FileUtils;
import org.doubleysoft.leveldb4j.GlobalConfig;
import org.doubleysoft.leveldb4j.api.exceptions.DataAccessException;
import org.doubleysoft.leveldb4j.api.exceptions.ExceptionEnum;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author anguslean
 * @Date 2018/5/2
 */
public class DbFileStorageManagerBaseTest {
    private static AtomicInteger atomicInteger = new AtomicInteger();
    private static String dbPath;


    @Before
    public void initDbFileStorageManager() {
        dbPath = "./dbmb" + atomicInteger.getAndIncrement() + "/";
        DbFileStorageManager.init(dbPath);
    }

    @After
    public void cleanDbFileStorageManager() throws IOException {
        DbFileStorageManager.closeAllFile();
        FileUtils.deleteDirectory(new File(dbPath));
    }

    @Test
    public void testWriteInt() {
        int[] ints = {12, 4534, 234, Integer.MIN_VALUE, Integer.MAX_VALUE};
        for (int val : ints) {
            DbFileStorageManager.getDbFileWriter().appendInt(val);
            Assert.assertEquals(val, DbFileStorageManager.getDbFileReader().readInt());
        }
    }

    @Test
    public void testWriteLong() {
        long[] ints = {12l, 4534l, 234l, 234234l, 333333l, Long.MIN_VALUE, Long.MAX_VALUE};
        for (long val : ints) {
            DbFileStorageManager.getDbFileWriter().appendLong(val);
            Assert.assertEquals(val, DbFileStorageManager.getDbFileReader().readLong());
        }
    }

    @Test
    public void testWritebyte() throws UnsupportedEncodingException {
        String[] keys = {"dsf", "1111134234", "dsfaa13sd123fs", "11~!@#$%^&*()_+?><M", "aaaaaaaaaaaaaaaaaa"};
        for (String key : keys) {
            byte[] bytes = key.getBytes(GlobalConfig.CHART_SET);
            DbFileStorageManager.getDbFileWriter().appendBytes(bytes);
            Assert.assertArrayEquals(bytes, DbFileStorageManager.getDbFileReader().readBytes(bytes.length));
        }
    }


    @Test
    public void testActiveFileSize() {
        Assert.assertEquals(0, DbFileStorageManager.getAndIncrementCurrentActiveSize(0));
        Assert.assertEquals(0, DbFileStorageManager.getAndIncrementCurrentActiveSize(11));
        Assert.assertEquals(11, DbFileStorageManager.getAndIncrementCurrentActiveSize(12));
        Assert.assertEquals(23, DbFileStorageManager.getAndIncrementCurrentActiveSize(10));
        Assert.assertNotEquals(20, DbFileStorageManager.getAndIncrementCurrentActiveSize(12));
        Assert.assertEquals(45, DbFileStorageManager.getAndIncrementCurrentActiveSize(10));
        Assert.assertEquals(55, DbFileStorageManager.getAndIncrementCurrentActiveSize(100));

        //should create a new file and return 0
        Assert.assertEquals(0, DbFileStorageManager.getAndIncrementCurrentActiveSize(GlobalConfig.MAX_FILE_SIZE));
        Assert.assertEquals(2, DbFileStorageManager.getActiveDbFileId());

        Assert.assertNotEquals(50, DbFileStorageManager.getAndIncrementCurrentActiveSize(0));
        Assert.assertEquals(0, DbFileStorageManager.getAndIncrementCurrentActiveSize(55));
        Assert.assertEquals(55, DbFileStorageManager.getAndIncrementCurrentActiveSize(10));
        Assert.assertNotEquals(55, DbFileStorageManager.getAndIncrementCurrentActiveSize(0));
        Assert.assertEquals(65, DbFileStorageManager.getAndIncrementCurrentActiveSize(0));

        Assert.assertEquals(3, DbFileStorageManager.getActiveDbFileId());
    }


    @Test
    public void testInsertTooMuchData() {
        try {
            DbFileStorageManager.getAndIncrementCurrentActiveSize(GlobalConfig.MAX_FILE_SIZE + 1);
        } catch (DataAccessException e) {
            Assert.assertEquals(e.getCode(), ExceptionEnum.DATA_IS_TOO_LONG.getCode());
        }
    }


}