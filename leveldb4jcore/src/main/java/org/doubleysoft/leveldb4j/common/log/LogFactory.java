package org.doubleysoft.leveldb4j.common.log;

/**
 * @author anguslean
 * @Date 2018/4/19
 */
public class LogFactory {
    public static Log getLog(Class klass) {
        return new LogConsoleImpl();
    }
}
