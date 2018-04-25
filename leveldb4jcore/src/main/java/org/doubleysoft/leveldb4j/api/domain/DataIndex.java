package org.doubleysoft.leveldb4j.api.domain;

/**
 * @author anguslean
 * @Description
 * @Date 2018/4/20
 */
public class DataIndex {
    /**
     * database file key position(seekg param in phosix system)
     */
    private long dataPos;

    /**
     * database file id
     */
    private int fileId;

    /**
     * data key
     */
    private String key;

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public long getDataPos() {
        return dataPos;
    }

    public void setDataPos(long dataPos) {
        this.dataPos = dataPos;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
