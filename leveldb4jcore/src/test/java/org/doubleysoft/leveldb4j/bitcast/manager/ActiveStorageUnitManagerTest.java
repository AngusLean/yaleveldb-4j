package org.doubleysoft.leveldb4j.bitcast.manager;

import org.apache.commons.io.FileUtils;
import org.doubleysoft.leveldb4j.api.YaKVDb4j;
import org.doubleysoft.leveldb4j.bitcast.BitCastContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("unchecked")
public class ActiveStorageUnitManagerTest {
    private YaKVDb4j<String> yaKVDb4j;
    private String relativePath = "./test/";

    @Before
    public void init() {
        yaKVDb4j = BitCastContext.init(relativePath);
    }

    @After
    public void cleanThis() {
        FileUtils.deleteQuietly(new File(relativePath));
    }

    @Test
    public void getInstance() {
        Assert.assertEquals(ActiveStorageUnitManager.getInstance(), ActiveStorageUnitManager.getInstance());
        assertEquals(ActiveStorageUnitManager.getInstance(), ActiveStorageUnitManager.getInstance());
        assertEquals(ActiveStorageUnitManager.getInstance(), ActiveStorageUnitManager.getInstance());
    }


}