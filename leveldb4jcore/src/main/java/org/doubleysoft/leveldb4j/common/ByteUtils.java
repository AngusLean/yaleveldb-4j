package org.doubleysoft.leveldb4j.common;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ByteUtils {
    /**
     * concat multi byte array
     * @param bytes
     * @return
     */
    public static byte[] concatByteArr(byte[]... bytes){
        int len = 0;
        for(byte[] bt : bytes){
            len += bt.length;
        }
        byte[] rs = new byte[len];

        len = 0;
        for(byte[] bt : bytes){
            int i=0;
            for(byte b : bt){
                rs[len++] = bt[i++];
            }
        }
        return rs;
    }

    /**
     * convert int to byte array
     * @param intVal
     * @return
     */
    public static  byte[] convertInt2ByteArr(int intVal){
        return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(intVal).array();
    }

    /**
     * convert byte array to int
     * @param byteVal
     * @return
     */
    public static int convertByteArr2Int(byte [] byteVal){
        return ByteBuffer.wrap(byteVal).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }
}
