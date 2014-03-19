package fabric.server.manager;

import java.util.List;

import fabric.common.db.LogicManager;
import fabric.server.entity.Account;
import fabric.server.entity.Shop;
import fabric.server.entity.UserRule;

/**
 *
 * @author likaihua
 *
 */
public interface AccountManager extends LogicManager<Account> {

    
    /**
     * 验证密码
     * @param accountId
     * @param password
     * @return
     */
    public boolean verifyAccount(Long accountId, String password);

    /**
     * 根据用户名,角色,商家查询账户
     * @param rule (可缺省)
     * @param name
     * @param shop
     * @return
     */
    public Account getAccountByName(UserRule rule, String name, Shop shop);

    
    /**
     * 根据用户名,角色,查询账户
     * @param rule
     * @param name
     * @return
     */
    public Account getAccountByName(UserRule rule, String name);

    /**
     * 获取所有账户列表
     * 
     * @param id
     * @return
     */
    public List<Account> getAll(Long id);
    
    /**
     * 验证昵称是否重名
     * true 重名
     * @param rule
     * @param shop
     * @param nickname
     * @return
     */
    public boolean checkNickname(UserRule rule, Account account, String nickname);
    
    /**
     * 根据角色来获取账户列表
     * @param rule
     * @return
     */
    public List<Account> getAllByRule(Account account, UserRule rule);
    
    
    /**
     * 删除账户
     * 如账户是商家账户则级联删除
     */
    @Override
    public void delete(Account account);
}
