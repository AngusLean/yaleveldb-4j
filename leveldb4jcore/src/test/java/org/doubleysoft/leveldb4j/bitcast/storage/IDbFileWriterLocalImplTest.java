package org.doubleysoft.leveldb4j.bitcast.storage;

import org.doubleysoft.leveldb4j.TestBase;
import org.doubleysoft.leveldb4j.bitcast.util.IDbFileWriter;
import org.doubleysoft.leveldb4j.bitcast.util.impl.IDbFileWriterLocalImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author anguslean
 * @Date 2018/3/26
 */
@SuppressWarnings("unchecked")
public class IDbFileWriterLocalImplTest extends TestBase{
    private IDbFileWriter iDbFileWriter;
    private String dbPath;

    @Before
    public void init(){
        dbPath = overrideDbPath("IDbFileWriterLocalImplTest");
        iDbFileWriter = new IDbFileWriterLocalImpl(dbPath);
    }

    @After
    public void clean(){
        cleanDbPath(dbPath);
    }


    @Test
    public void appendSimpleNum() {
        testByteArrStorage("123".getBytes());
    }

    @Test
    public void appendNumber(){
        testByteArrStorage("4878972321894712314789421658".getBytes());
    }

    @Test
    public void appendCha(){
        testByteArrStorage("assdfdfsdgzcverwehgfhgfz".getBytes());
    }

    @Test
    public void appendSpecialCha(){
        testByteArrStorage("----==++__+1213(()(&&*:-_".getBytes());
    }

    @Test
    public void appendKeyValue(){
        testByteArrStorage(("{\n" +
                "    \"name\": \"zhangsan\", \n" +
                "    \"age\": 20\n" +
                "}").getBytes());
    }

    @Test
    public void appendMap(){
        Map test = new HashMap(){{
            put("name","wagnwu");
            put("age", 12);
            put("salary", 12133.23);
        }};
        testByteArrStorage(test.toString().getBytes());
    }

    private static byte[] readByteArray(String filePath, int begin, int len) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(filePath))) {
            byte[] bytes = new byte[len];
            dataInputStream.readFully(bytes, begin, len);
            return bytes;
        }
    }

    private void testByteArrStorage(byte[] bytes){
        iDbFileWriter.appendBytes(bytes);
        try {
            byte[] readBytes = readByteArray(dbPath, 0, bytes.length);
            Assert.assertArrayEquals(bytes, readBytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            iDbFileWriter.close();
        }
    }
}