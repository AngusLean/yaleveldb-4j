package org.doubleysoft.leveldb4j.common.log;

/**
 * @author anguslean
 */
public class LogConsoleImpl implements Log {
    @Override
    public void warn(String msg) {
        System.out.println(msg);
    }

    @Override
    public void error(String msg) {
        System.out.println(msg);
    }

    @Override
    public void warn(String msg, Exception e) {
        System.out.println(msg);
        e.printStackTrace();
    }

    @Override
    public void debug(String msg) {
        System.out.println("DEBUG--- " + msg);
    }

    @Override
    public void warn(String msg, String... params) {

    }

    @Override
    public void log(String msg, String... params) {

    }

    private String formartMsg(String msg, String... params) {
        return "";
    }
}
