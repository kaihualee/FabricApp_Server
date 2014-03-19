/**
 * @(#)DataFileManagerTest.java, 2013-7-1. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.server.manager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fabric.server.entity.DataFile;
import fabric.server.entity.FileType;

/**
 * @author nisonghai
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/*.xml" })
public class DataFileManagerTest {

    @Autowired
    private DataFileManager dataFileManager;

    @Before
    public void setUp() throws Exception {}

    @Test
    public void testGetAllByStatus() {}

    @Test
    public void testGetAll() {
        
    }

    @Test
    public void testSave() {
        DataFile dataFile = new DataFile("aaa.jpg", "md5code",
            FileType.Cover_Image);
        dataFileManager.save(dataFile);
        System.out.println(dataFile);
        
        dataFileManager.delete(dataFile);
        dataFile = dataFileManager.get(dataFile.getId());
        System.out.println(dataFile);
    }

}
