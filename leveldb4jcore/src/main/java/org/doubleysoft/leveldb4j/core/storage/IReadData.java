package org.doubleysoft.leveldb4j.core.storage;

import org.doubleysoft.leveldb4j.core.domain.IData;

/**
 * read db data from files
 */
public interface IReadData {
    IData readData(IStorageFile iStorageFile);
}
