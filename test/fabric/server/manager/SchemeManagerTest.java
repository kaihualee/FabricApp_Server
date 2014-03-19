/**
 * @(#)SchemeManagerTest.java, 2013-7-1. 
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

import fabric.server.entity.Account;
import fabric.server.entity.DataFile;
import fabric.server.entity.FileType;
import fabric.server.entity.FlowerType;
import fabric.server.entity.Scene;
import fabric.server.entity.Scheme;
import fabric.server.entity.UserRule;

/**
 * @author nisonghai
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class SchemeManagerTest {

    @Autowired
    private SchemeManager schemeManager;
    
    @Autowired
    private AccountManager accountManager;
    
    
    @Autowired
    private SceneManager sceneManager;
    
    private Account admin;

    
    
    @Autowired
    private FlowerTypeManager ftManager;
    
    @Before
    public void setUp() throws Exception {
        admin = accountManager.getAccountByName(UserRule.SUPER_ADMIN, "admin",
            null);
    }


    @Test
    public void testget(){
        Long id = 2L;
        Scheme scheme = schemeManager.get(id);
        System.out.println(schemeManager.get(id).getOwner().getName());
    }
    @Test
    public void testSave() {
        Scheme scheme = new Scheme();
        scheme.setName("方案");
        scheme.setDescription("方案方案方案方案方案方案");
        scheme.setCoverImage(new DataFile("Demo方案.jpg", "md5code", FileType.Cover_Image));
        scheme.setXmlFile(new DataFile("Demo方案.jpg", "md5code", FileType.Cover_Image));
        scheme.setOwner(admin);
        Scene scene = sceneManager.get(1L);
        FlowerType flowerType = ftManager.get(1L);
        scheme.setScene(scene);
        scheme.setFlowerType(flowerType);
        schemeManager.saveOrUpdate(scheme);
        
    }

    @Test
    public void testDeleteAll(){
        schemeManager.removeAll();
    }
    
}
