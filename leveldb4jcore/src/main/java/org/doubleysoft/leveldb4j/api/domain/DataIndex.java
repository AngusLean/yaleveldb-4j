package org.doubleysoft.leveldb4j.api.domain;

import lombok.Data;

/**
 * @author anguslean
 * @Date 2018/4/20
 */
@Data
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

}
