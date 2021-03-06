package org.doubleysoft.leveldb4j.bitcast;

import org.apache.commons.io.FileUtils;
import org.doubleysoft.leveldb4j.GlobalConfig;
import org.doubleysoft.leveldb4j.api.exceptions.DataAccessException;
import org.doubleysoft.leveldb4j.bitcast.manager.DbFileStorageManager;
import org.junit.*;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dongyang.yu
 * @email dongyang.yu@anxincloud.com
 */
public class KvDbBitCastImplTest {
    private static AtomicInteger atomicInteger = new AtomicInteger();
    private KvDbBitCastImpl yaKVDb4j;
    private String path = "./dbBitCastImplTest/";

    @Before
    public void init() {
        path = "./dbBitCastImplTest" + atomicInteger.getAndIncrement() + "/";
        DbFileStorageManager.init(path);
        yaKVDb4j = new KvDbBitCastImpl();
//        yaKVDb4j = BitCastContext.init(path);
    }

    @After
    public void resetEnv() {
        DbFileStorageManager.closeAllFile();
        FileUtils.deleteQuietly(new File(path));
    }


    @Test
    public void put1() {
        basePutAndQuery("建制", "fsa234sdfsd458412@#%￥#！@#￥#");
    }

    @Test
    public void put2() {
        basePutAndQuery("test1", "dsdfsdsf测试文字测试文字测试文字测试文测试文字测试文测试文字测试文测试文字测试文");
        basePutAndQuery("fd", "~!@#$%^&*()_+\\\\\\///..,,");
    }

    @Test
    public void put3() {
        basePutAndQuery("key", "ds324fsdsf测试文字测试文字测试文字测试文测试文字测试文测试文字测试文测试文字测试文");
        basePutAndQuery("key1", "fsdf测试文字测试文sdfsda测试文字测试文23423");
        basePutAndQuery("key2", "33&^&$%$#!)()(++__||||||~@(@))&&^^^");
    }

    @Test
    public void put4AndUnicode() {
        basePutAndQuery("2131", "324234");
        basePutAndQuery("2131s", "32df4234");
        basePutAndQuery("!key", "324afd234");
        basePutAndQuery("___key", "\uE076\uE07F\uE09A\uA910\uFA910");
    }

    @Ignore
    public void putLongData() {
        //try to insert long data one by one, it will create new db file
        StringBuilder sb = new StringBuilder();
        for (long i = 0; i < GlobalConfig.MAX_FILE_SIZE - 40; i++) {
            sb.append("a");
        }
        basePutAndQuery("longval", sb.toString());
        basePutAndQuery("aaa", sb.toString());

        sb = new StringBuilder();
        for (long i = 0; i < GlobalConfig.MAX_FILE_SIZE - 40; i++) {
            sb.append("b");
        }
        basePutAndQuery("longval1", sb.toString());

        basePutAndQuery("newkey", sb.toString());
    }

    @Test(expected = DataAccessException.class)
    public void putTooLongData() {
        StringBuilder sb = new StringBuilder();
        for (long i = 0; i < GlobalConfig.MAX_FILE_SIZE; i++) {
            sb.append("a");
        }
        basePutAndQuery("longval", sb.toString());
    }

    private void basePutAndQuery(String key, String val) {
        yaKVDb4j.put(key, val);
        Assert.assertEquals(val, yaKVDb4j.get(key));
        System.out.println();
    }
}