/**
 * @(#)ShopManagerTest.java, 2013-7-1. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.server.manager;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import fabric.server.entity.Shop;

/**
 * @author nisonghai
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/*.xml" })
public class ShopManagerTest {

    @Autowired
    private ShopManager shopManager;

    @Before
    public void setUp() throws Exception {}

    @Test
    public void testSaveByName() {
        Shop shop = new Shop();
        shop.setName("富安娜家纺");
        shop.setAddress("北京xxx路xxx号");
        shop.setPhone("010-444444");
        shop = shopManager.saveByName(shop);
        System.out.println(shop);

        shop = new Shop();
        shop.setName("罗莱家纺");
        shop.setAddress("北京xxx路xxx号");
        shop.setPhone("010-232423322");
        shop = shopManager.saveByName(shop);
        System.out.println(shop);

        shop = new Shop();
        shop.setName("富安娜家纺");
        shop.setAddress("北京xxx路xxx号");
        shop.setPhone("010-555555555");
        shop = shopManager.saveByName(shop);
        System.out.println(shop);
    }
    
    @Test
    public void testList(){
        List<Shop> list = shopManager.getAll();
        if(list == null || list.isEmpty()){
            return;
        }
        
        for(Shop item : list){
            System.out.println(item.getName());
        }
        
    }
    
    
    @Test
    public void testGetByName() {
        Shop shop = shopManager.getByName("富安娜家纺");
        System.out.println(shop);
    }

}
