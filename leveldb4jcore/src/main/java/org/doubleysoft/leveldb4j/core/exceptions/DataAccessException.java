package org.doubleysoft.leveldb4j.core.exceptions;

import java.io.Serializable;

public class DataAccessException extends RuntimeException implements Serializable {
    public DataAccessException(String msg) {
        super(msg);
    }
}
