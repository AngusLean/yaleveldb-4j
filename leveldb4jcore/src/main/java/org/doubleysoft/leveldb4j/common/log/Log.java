package org.doubleysoft.leveldb4j.common.log;

/**
 * @author anguslean
 * @Date 2018/4/19
 */
public interface Log {

    /**
     * warn log
     *
     * @param msg
     */
    void warn(String msg);

    /**
     * error log
     *
     * @param msg error msg
     */
    void error(String msg);

    /**
     * warn log
     *
     * @param msg warn msg
     * @param e   warn exception
     */
    void warn(String msg, Exception e);

    /**
     * debug msg
     *
     * @param msg debug msg
     */
    void debug(String msg);


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
