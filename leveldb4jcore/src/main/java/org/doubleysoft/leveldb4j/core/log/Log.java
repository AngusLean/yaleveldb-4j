package org.doubleysoft.leveldb4j.core.log;

/**
 * leveldb4j inner log interface
 */
public interface Log {

    /**
     * warn log
     *
     * @param msg
     */
    void warn(String msg);

    /**
     * warn log with multi param
     *
     * @param msg
     * @param params
     */
    void warn(String msg, String... params);

    /**
     * private log
     *
     * @param msg
     * @param params
     */
    void log(String msg, String... params);
}
