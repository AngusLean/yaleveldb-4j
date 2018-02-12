package org.doubleysoft.leveldb4j.core.storage;

import org.doubleysoft.leveldb4j.common.FileUtils;
import org.doubleysoft.leveldb4j.core.TestBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class IStorageFileLocalImplTest extends TestBase{
    private IStorageFile iStorageFile;
    private String dbPath;

    @Before
    public void init(){
        dbPath = getDbPath("IStorageFileLocalImplTest");
        iStorageFile = new IStorageFileLocalImpl(dbPath);
    }

    @Test
    public void appendBytes() throws Exception {
        byte[] bts = new byte[]{(byte)0xe0, 0x4f, (byte)0xd0,
                0x20, (byte)0xea, 0x3a, 0x69, 0x10, (byte)0xa2, (byte)0xd8, 0x08, 0x00, 0x2b,
                0x30, 0x30, (byte)0x9d };

        iStorageFile.appendBytes(bts);
        Assert.assertArrayEquals(bts, FileUtils.readByteArray(dbPath));
    }

    @Test
    public void appendString() throws IOException {
        String str = "hello世界";
        byte[] strbyte = str.getBytes("utf-8");
        iStorageFile.appendBytes(strbyte);
        Assert.assertArrayEquals(strbyte, FileUtils.readByteArray(dbPath));
    }

    @Test
    public void appendNullBytes(){
        iStorageFile.appendBytes(null);
    }




}