package org.doubleysoft.leveldb4j.core.domain;

import org.doubleysoft.leveldb4j.common.ByteUtils;
import org.doubleysoft.leveldb4j.core.GlobalConfig;
import org.doubleysoft.leveldb4j.core.exceptions.DataAccessException;
import org.doubleysoft.leveldb4j.core.log.Log;
import org.doubleysoft.leveldb4j.core.log.LogFactory;

import java.io.UnsupportedEncodingException;

public class IDataKVImpl<Object> implements IData<Object> {
    private static final Log log = LogFactory.getLog(IDataKVImpl.class);

    private String key;
    private String value;

    private byte[] byteArr = new byte[0];
    private int byteArrLen = 0;

    public IDataKVImpl(String key, String val) {
        this.key = key;
        this.value = val;

        try {
            byte[] keybytes = this.key.getBytes(GlobalConfig.CHART_SET);
            byte[] valbytes = this.value.getBytes(GlobalConfig.CHART_SET);
            this.byteArr = ByteUtils.concatByteArr(keybytes, valbytes);
            this.byteArrLen = this.byteArr.length;
        } catch (UnsupportedEncodingException e) {
            log.warn("Unsupported data encoding, try to set other encoding to avoid this error");
            throw new DataAccessException("Unsupported data encoding, try to set other encoding to avoid this error");
        }

    }


    @Override
    public Object getData() {
        return null;
    }

    @Override
    public byte[] getDataBytes() {
        return this.byteArr;
    }

    /**
     * current data size, include key and value
     *
     * @return
     */
    @Override
    public int getDataSize() {
        return this.byteArrLen;
    }


/*    public boolean equals(Object other){
        IDataKVImpl val = (IDataKVImpl)other;
        return this.key.equals(val.getKey()) && this.value.equals(val.getValue());
    }*/

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
