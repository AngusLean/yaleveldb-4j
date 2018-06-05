package org.doubleysoft.leveldb4j.api.exceptions;

public class InitialDataBaseException extends DataAccessException {

    public InitialDataBaseException(String msg) {
        super(msg);
    }


    public InitialDataBaseException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum);
    }
}
