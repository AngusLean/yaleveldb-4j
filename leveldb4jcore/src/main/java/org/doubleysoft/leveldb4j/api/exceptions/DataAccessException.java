package org.doubleysoft.leveldb4j.api.exceptions;

import java.io.Serializable;

public class DataAccessException extends RuntimeException implements Serializable {
    public DataAccessException(String msg) {
        super(msg);
    }

    public DataAccessException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.toString());
    }
}
