package org.doubleysoft.leveldb4j.bitcast.storage;

import org.doubleysoft.leveldb4j.TestBase;
import org.doubleysoft.leveldb4j.api.GlobalConfig;
import org.doubleysoft.leveldb4j.api.domain.IData;
import org.doubleysoft.leveldb4j.api.storage.IDbFileReader;
import org.doubleysoft.leveldb4j.api.storage.IStorageData;
import org.doubleysoft.leveldb4j.api.storage.IDbFileWriter;
import org.doubleysoft.leveldb4j.bitcast.IDataKVImpl;
import org.junit.*;

import java.io.UnsupportedEncodingException;

/**
 * @author anguslean
 * @Description
 * @Date 2018/3/26
 */
public class IStorageDataSequnceImplTest extends TestBase{
    private IStorageData iStorageData;
    private IDbFileWriter iDbFileWriter;
    private IDbFileReader iDbFileReader;
    private String dbPath;

    @Before
    public void init(){
        dbPath = getDbPath("IStorageDataSequnceImplTest");
        iDbFileWriter = new IDbFileWriterLocalImpl(dbPath);
        iStorageData = new IStorageDataSequnceImpl();
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
        iStorageData.saveData(iData, iDbFileWriter);
        //after write data, close stream
        iDbFileWriter.close();


        iDbFileReader = new IDbFileReaderLocalImpl(dbPath);
        iStorageData.readData(iData, iDbFileReader);
        //after read, close stream
        iDbFileReader.close();


        Assert.assertEquals(iData.getKey(), key);
        Assert.assertEquals(iData.getVal(), val);

    }
}