package org.doubleysoft.leveldb4j.common.log;

public class LogFactory {
    public static Log getLog(Class klass) {
        return new LogConsoleImpl();
    }
}
