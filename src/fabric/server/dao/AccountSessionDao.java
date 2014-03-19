/**
 * @(#)AccountSessionDao.java, 2013-7-19. 
 * 
 */
package fabric.server.dao;

import fabric.common.db.BaseDao;
import fabric.common.db.LogicDao;
import fabric.server.entity.Account;
import fabric.server.entity.AccountSession;
import fabric.server.entity.ClientPower;

/**
 * @author likaihua
 */
public interface AccountSessionDao extends LogicDao<AccountSession> {

    /**
     * 根据账户和IP和登录界面
     * 
     * @param account
     * @param ip
     * @param power
     * @return
     */
    public AccountSession getByAccountByIPAndPower(Account account, String ip,
        ClientPower power);
}
