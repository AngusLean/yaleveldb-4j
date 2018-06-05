package org.doubleysoft.leveldb4j.bitcast.model;

import lombok.Data;

/**
 * a database storage unit, or a file
 */
@Data
public class DbStorageUnitModel {
    /**
     * current db unit absolute path
     */
    private String absPath;

    /**
     * current db unit file name
     */
    private String name;

    /**
     * current db unit index, for example ,1、2...
     */
    private int index;
}
