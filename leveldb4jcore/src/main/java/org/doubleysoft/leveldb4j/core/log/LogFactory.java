package org.doubleysoft.leveldb4j.core.log;

public class LogFactory {
    public static Log getLog(Class klass) {
        return new LogConsoleImpl();
    }
}
