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
import fabric.server.entity.UserRule;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/*.xml" })
public class AccountManagerTest {

    @Autowired
    private AccountManager accountManager;


    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    /**
     * @throws Exception
     */

    @Test
    public void testSave() throws Exception {
        Long id = 1L;
        List<Account> accounts = accountManager.getAll(id);
        for(Account item : accounts){
            System.out.println("Name: "+item.getName());
        }
    }
    @Test
    public void testList(){
        Long id = 1L;
        List<Account> accounts = accountManager.getAll(id);
        for(Account item : accounts){
            System.out.println("Name: "+item.getName());
        }
    }
    @Test
    public void testAddAccount() {
        Account account = new Account();
        String username;
        String password;
        String nickname;
        String realname;
        username = "admin";
        password = MD5Util.md5(username);
        nickname = String.format("Nick_%s", username);
        realname = username;
        account.setName(username);
        account.setPassword(password);
        account.setInfo("无");
        account.setNickname(nickname);
        account.setRule(UserRule.SUPER_ADMIN);
        account.setRealname(realname);
        accountManager.saveOrUpdate(account);
    }
    


}
