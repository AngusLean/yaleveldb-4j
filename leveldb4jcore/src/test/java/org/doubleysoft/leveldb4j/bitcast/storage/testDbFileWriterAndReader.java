package org.doubleysoft.leveldb4j.bitcast.storage;

import org.apache.commons.io.FileUtils;
import org.doubleysoft.leveldb4j.GlobalConfig;
import org.doubleysoft.leveldb4j.TestBase;
import org.doubleysoft.leveldb4j.api.storage.IData;
import org.doubleysoft.leveldb4j.api.storage.IDbDataReader;
import org.doubleysoft.leveldb4j.api.storage.IDbDataWriter;
import org.doubleysoft.leveldb4j.bitcast.IDataKVImpl;
import org.doubleysoft.leveldb4j.bitcast.manager.DbFileStorageManager;
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
 * @Date 2018/3/26
 */
public class testDbFileWriterAndReader extends TestBase {
    private IDbDataWriter iDbDataWriter;
    private static AtomicInteger atomicInteger = new AtomicInteger();
    private String dbPath;
    private IDbDataReader iDbDataReader;

    @Before
    public void init() throws IOException {
        dbPath = getDbPath("testDbFileWriterAndReader" + atomicInteger.getAndIncrement());
        FileUtils.deleteDirectory(new File(dbPath));
        DbFileStorageManager.init(dbPath);
        iDbDataWriter = new IDbDataWriterSequnceImpl();
        iDbDataReader = new IDbDataReaderSequnceImpl();
    }

    @After
    public void clean(){
        cleanDbPath(dbPath);
    }

    @Test
    public void saveData() {
        basicTest("name", "zhagnsan");
    }

    @Test
    public void saveInt(){
        basicTest("age", "123456");
    }

    @Test
    public void saveInt1(){
        basicTest("111", "123456");
    }

    @Test
    public void saveSpecialCha(){
        basicTest("age", "_+3423!@#$%&*(~/\\");
    }

    @Test
    public void saveUnicode() throws UnsupportedEncodingException {
        String uni = "😱🙃Ⱪ";
        Assert.assertEquals(11, uni.getBytes(GlobalConfig.CHART_SET).length);
        byte[] unibytes = uni.getBytes();
        basicTest("emoji", uni);
    }

    private void basicTest(String key, String val){

        IData<String> iData = new IDataKVImpl(key,val);
        iDbDataWriter.saveData(iData);

        IData<String> newData = new IDataKVImpl();
        iDbDataReader.readData(newData, 0, 0);
        Assert.assertEquals(key, newData.getKey());
        Assert.assertEquals(val, newData.getVal());

    }
}