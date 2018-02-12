package org.doubleysoft.leveldb4j.common;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ByteUtilsTest {
    @Test
    public void convertInt2ByteArr() throws Exception {
        int intVal = 111510;
        byte[] bytes = ByteUtils.convertInt2ByteArr(intVal);

        int rsInt = ByteUtils.convertByteArr2Int(bytes);

        Assert.assertEquals(intVal, rsInt);
    }


}