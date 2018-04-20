package org.doubleysoft.leveldb4j.api.exceptions;

/**
 * @author yd.yu
 * @Description
 * @Date 2018/3/26
 */
public enum ExceptionEnum {
    CHAR_ERROR("00001","char set is wrong"),
    CAN_NOT_CREATE_DB_FILE("10001","could not create db file"),
    DATA_IS_TOO_LONG("10002","data is too long to save");

    private String code;
    private String desc;

    ExceptionEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString(){
        return this.code + ":" + this.desc;
    }
}
