package org.doubleysoft.leveldb4j.core.storage;

import org.doubleysoft.leveldb4j.core.TestBase;
import org.doubleysoft.leveldb4j.core.domain.IData;
import org.doubleysoft.leveldb4j.core.domain.IDataKVImpl;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class IStorageDataSequnceImplTest extends TestBase {
    private IStorageData iStorageData;
    private IStorageFile iStorageFile;
    private String dbPath;

    @Before
    public void init() {
        dbPath = getDbPath("IStorageDataSequnceImpl");
        try {
            File file = new File(dbPath);
            file.delete();
        } catch (Exception ignore) {
        }
        iStorageData = new IStorageDataSequnceImpl();

        iStorageFile = new IStorageFileLocalImpl(dbPath);
    }

    @Test
    public void saveData() throws Exception {
        IData iData = new IDataKVImpl("hello", "世界");
        iStorageData.saveData(iData, iStorageFile);
    }

}