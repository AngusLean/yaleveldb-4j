package org.doubleysoft.leveldb4j.bitcast.storage;

import org.doubleysoft.leveldb4j.TestBase;
import org.doubleysoft.leveldb4j.GlobalConfig;
import org.doubleysoft.leveldb4j.api.storage.*;
import org.doubleysoft.leveldb4j.bitcast.IDataKVImpl;
import org.doubleysoft.leveldb4j.bitcast.util.IDbFileReader;
import org.doubleysoft.leveldb4j.bitcast.util.IDbFileWriter;
import org.doubleysoft.leveldb4j.bitcast.util.impl.IDbFileReaderLocalImpl;
import org.doubleysoft.leveldb4j.bitcast.util.impl.IDbFileWriterLocalImpl;
import org.junit.*;

import java.io.UnsupportedEncodingException;

/**
 * @author anguslean
 * @Description
 * @Date 2018/3/26
 */
public class IDbDataWriterSequnceImplTest extends TestBase{
    private IDbDataWriter iDbDataWriter;

    private IDbFileWriter iDbFileWriter;
    private String dbPath;

    @Before
    public void init(){
        dbPath = getDbPath("IDbDataWriterSequnceImplTest");
        iDbFileWriter = new IDbFileWriterLocalImpl(dbPath);
        iDbDataWriter = new IDbDataWriterSequnceImpl();
    }

    @After
    public void clean(){
//        cleanDbPath(dbPath);
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

        IData<String> newData = new IDataKVImpl(key,val);

        Assert.assertEquals(newData.getKey(), key);
        Assert.assertEquals(newData.getVal(), val);

    }
}