package org.doubleysoft.leveldb4j.api.exceptions;

/**
 * @author yd.yu
 * @Date 2018/3/26
 */
public enum ExceptionEnum {
    /**
     * char encoding error
     */
    CHAR_ERROR("00001","char set is wrong"),

    /**
     * can't create db file
     */
    CAN_NOT_CREATE_DB_FILE("10001","could not create db file"),

    /**
     * can't init db from path
     */
    CAN_NOT_INIT_DB_FROM_PATH("10000", "could not create db file"),

    /**
     * data is too long to save
     */
    DATA_IS_TOO_LONG("10002", "data is too long to save"),

    /**
     * read key length from file error
     */
    READ_KEYLEN_FROM_FILE_ERROR("20003", "read key length from db file error");

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
