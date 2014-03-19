/**
 * @(#)AccountSessionManagerTest.java, 2013-7-19. 
 * 
 */
package fabric.server.manager;
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
import fabric.server.entity.AccountSession;
import fabric.server.entity.ClientPower;
import fabric.server.entity.UserRule;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/*.xml" })
/**
 *
 * @author likaihua
 *
 */
public class AccountSessionManagerTest {

    @Autowired
    private AccountSessionManager asManager;
    
    @Test
    public void testSave(){
        AccountSession as = new AccountSession();
        as.setName("11");
        as.setSid("11");
        as.setIp("da");
        as.setPower(ClientPower.ADMIN_BUSINESS);
        asManager.save(as);
    }
    
    @Test
    public void testGetAll(){
        List<AccountSession> list = asManager.getAll();
    }
}
