package org.doubleysoft.leveldb4j.bitcast;

import org.doubleysoft.leveldb4j.api.YaKVDb4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author anguslean
 * @Description
 * @Date 2018/4/25
 */
public class YaKVDb4jBitCastImplTest {
    private YaKVDb4j yaKVDb4j;

    @Before
    public  void init(){
        yaKVDb4j = new YaKVDb4jBitCastImpl();
    }

    @Test
    public void put() {
        basePutAndQuery("test", "1");
    }

    @Test
    public void get() {
        basePutAndQuery("2131", "324234");
        basePutAndQuery("2131s", "32df4234");
        basePutAndQuery("2131d", "324afd234");
        basePutAndQuery("2131d", "324df234");
    }

    private void basePutAndQuery(String key, String val){
        yaKVDb4j.put(key, val);
        Assert.assertEquals(val, yaKVDb4j.get(key));
    }

    @Test
    public void delete() {
    }
}