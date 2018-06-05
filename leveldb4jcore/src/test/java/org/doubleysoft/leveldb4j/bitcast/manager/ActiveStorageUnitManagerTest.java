package org.doubleysoft.leveldb4j.bitcast.manager;

import junit.framework.TestSuite;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ActiveStorageUnitManagerTest {

    @Test
    public void getStorageUnit() {
    }

    @Test
    public void incrementActiveStorageUnit() {
        TestSuite increment = new TestSuite();
    }

    @Test
    public void getInstance() {
        assertEquals(ActiveStorageUnitManager.getInstance(), ActiveStorageUnitManager.getInstance());
        assertEquals(ActiveStorageUnitManager.getInstance(), ActiveStorageUnitManager.getInstance());
        assertEquals(ActiveStorageUnitManager.getInstance(), ActiveStorageUnitManager.getInstance());
    }
}