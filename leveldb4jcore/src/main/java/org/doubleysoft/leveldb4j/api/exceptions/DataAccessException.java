package org.doubleysoft.leveldb4j.api.exceptions;

import java.io.Serializable;

/**
 * @author anguslean
 * @Date 2018/4/19
 */
public class DataAccessException extends RuntimeException implements Serializable {
    private String code;
    private String msg;

    public DataAccessException(String msg) {
        super(msg);
    }

    public DataAccessException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.toString());
        this.code = exceptionEnum.getCode();
        this.msg = exceptionEnum.getDesc();
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
