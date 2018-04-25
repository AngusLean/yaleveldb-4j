package org.doubleysoft.leveldb4j.bitcast.index;

import org.doubleysoft.leveldb4j.api.domain.DataIndex;
import org.doubleysoft.leveldb4j.api.index.IDbIndex;
import org.doubleysoft.leveldb4j.api.storage.IData;
import org.doubleysoft.leveldb4j.bitcast.IDataKVImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author anguslean
 * @Description
 * @Date 2018/4/20
 */
public class IDbIndexImplTest {
    private IDbIndex iDbIndex;

    @Before
    public void setUp() {
        iDbIndex = new IDbIndexImpl();
    }

    @Test
    public void findData() {
        DataIndex dataIndex = new DataIndex();
        dataIndex.setFileId(1);
        dataIndex.setKey("hh");
        dataIndex.setDataPos(0);
        iDbIndex.addIndex(dataIndex);
    }

    @Test
    public void addIndex() {
    }
}