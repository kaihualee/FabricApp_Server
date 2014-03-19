/**
 * @(#)GrantManagerTest.java, 2013-7-11. 
 * 
 */
package fabric.server.manager;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fabric.server.entity.Account;
import fabric.server.entity.Scheme;
import fabric.server.entity.Shop;

/**
 *
 * @author likaihua
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/*.xml" })
public class GrantManagerTest {
    
    @Autowired
    private GrantTableManager gtManager;
    
    @Autowired
    private AccountManager accountManager;
    
    @Autowired
    private SchemeManager schemeManager;
    
    
    @Autowired
    private ShopManager shopManager;
    
    @Test
    public void save(){
    }
    
    @Test
    public void remove(){
        Account partA = accountManager.getAccountByName(null, "design");
        Shop partB = shopManager.getByName("博洋家纺");
        
        Long designID = 1L;
        List<Scheme> list = new ArrayList<Scheme>();
        
        int nCount = 1;
        list.add(schemeManager.get(1L));
        list.add(schemeManager.get(2L));
        list.add(schemeManager.get(3L));
        
        //gtManager.deleteByDesign(partA, partA, partB, designID);
    }
    
    @Test
    public void testDeleteAll(){
        gtManager.removeAll();
    }
    
}
