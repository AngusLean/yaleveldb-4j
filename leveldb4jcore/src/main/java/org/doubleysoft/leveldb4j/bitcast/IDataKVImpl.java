package org.doubleysoft.leveldb4j.bitcast;

import lombok.ToString;
import org.doubleysoft.leveldb4j.GlobalConfig;
import org.doubleysoft.leveldb4j.api.exceptions.DataAccessException;
import org.doubleysoft.leveldb4j.api.exceptions.ExceptionEnum;
import org.doubleysoft.leveldb4j.api.storage.IData;
import org.doubleysoft.leveldb4j.common.log.Log;
import org.doubleysoft.leveldb4j.common.log.LogFactory;

import java.io.UnsupportedEncodingException;

/**
 * @author anguslean
 * @Description the key-value model
 * @Date 2018/4/19
 */

@ToString
public class IDataKVImpl implements IData<String> {
    private static final Log log = LogFactory.getLog(IDataKVImpl.class);

    private String key;
    private String value;

    private byte[] keyBytes;
    private byte[] valBytes;

    public IDataKVImpl(String key, String val) {
        this.key = key;
        this.value = val;
    }

    public IDataKVImpl() {
        this.value = this.key = "";
    }

    @Override
    public int getKeyLen() {
        return getKeyBytes().length;
    }

    @Override
    public byte[] getKeyBytes() {
        if(this.keyBytes == null){
            try {
                this.keyBytes = key.getBytes(GlobalConfig.CHART_SET);
            } catch (UnsupportedEncodingException e) {
                log.warn("get data key byte[] char set error");
                throw new DataAccessException(ExceptionEnum.CHAR_ERROR);
            }
        }
        return this.keyBytes;
    }

    @Override
    public int getValLen() {
        return getValBytes().length;
    }

    @Override
    public byte[] getValBytes() {
        if(this.valBytes == null){
            try {
                this.valBytes = value.getBytes(GlobalConfig.CHART_SET);
            } catch (UnsupportedEncodingException e) {
                log.warn("get data value byte[] char set error");
                throw new DataAccessException(ExceptionEnum.CHAR_ERROR);
            }
        }

        return this.valBytes;
    }

    /**
     * read key to current data
     *
     * @param keyLen
     * @param key
     */
    @Override
    public void readKey(int keyLen, byte[] key) {
        if(keyLen > 0){
            try {
                this.key = new String(key, 0, keyLen, GlobalConfig.CHART_SET);
            } catch (UnsupportedEncodingException e) {
                log.warn("read data key byte[] char set error");
                throw new DataAccessException(ExceptionEnum.CHAR_ERROR);
            }
        }
    }

    /**
     * read value to current data
     *
     * @param valLen
     * @param val
     */
    @Override
    public void readVal(int valLen, byte[] val) {
        if(valLen > 0){
            try {
                this.value = new String(val, 0, valLen, GlobalConfig.CHART_SET);
            } catch (UnsupportedEncodingException e) {
                log.warn("read data value byte[] char set error");
                throw new DataAccessException(ExceptionEnum.CHAR_ERROR);
            }
        }
    }

    @Override
    public String getKey() {
        return key;
    }

    /**
     * get value
     *
     * @return
     */
    @Override
    public String getVal() {
        return value;
    }
}
