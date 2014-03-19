/**
 * @(#)SessionPoolTest.java, 2013-7-19. 
 * 
 */
package fabric.server.web.session;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fabric.common.db.Status;
import fabric.common.utils.MD5Util;
import fabric.server.entity.Account;
import fabric.server.entity.ClientPower;
import fabric.server.entity.UserRule;
import fabric.server.manager.AccountManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/*.xml" })
public class SessionPoolTest {

    @Autowired
    private SessionPool sessionPool;
    
    @Autowired
    private AccountManager accountManager;
    
    @Test
    public void testSave(){
        Account account = accountManager.getAccountByName(null, "admin");
        sessionPool.insert(account, "ip", ClientPower.ADMIN_BUSINESS);
    }
}
