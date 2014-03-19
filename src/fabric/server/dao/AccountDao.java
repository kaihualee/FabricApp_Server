package fabric.server.dao;

import java.util.List;

import fabric.common.db.LogicDao;
import fabric.server.entity.Account;
import fabric.server.entity.Shop;
import fabric.server.entity.UserRule;

public interface AccountDao extends LogicDao<Account> {

    /**
     * 根据用户名,角色，店家查询账户
     * 
     * @param rule
     * @param name
     * @param shop
     * @return
     */
    public Account getByName(UserRule rule, String name, Shop shop);
    
    /**
     * 根据用户名，角色查询账户
     * @param rule
     * @param name
     * @return
     */
    public Account getByName(UserRule rule, String name);
    
    /**
     * 验证密码
     * 
     * @param accountId
     * @param password
     * @return
     */
    public boolean checkPassword(Long accountId, String password);

    /**
     * 获取所有账户列表(不包括管理员)
     * @param accountId
     * @return
     */
    public List<Account> getAll();
    
    /**
     * 昵称查重
     * true = 重名
     * @param nickname
     * @return
     */
    public boolean checkNickname(UserRule rule, Long userId, String nickname);
    
    /**
     * 根据角色获取所有该角色账户列表
     * @param rule
     * @return
     */
    public List<Account> getAllByRule(Long id, UserRule rule);
    
    
}
