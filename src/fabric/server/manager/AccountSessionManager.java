/**
 * @(#)AccountSessionManager.java, 2013-7-19. 
 * 
 */
package fabric.server.manager;

import fabric.common.db.LogicManager;
import fabric.server.entity.Account;
import fabric.server.entity.AccountSession;
import fabric.server.entity.ClientPower;

/**
 *
 * @author likaihua
 *
 */
public interface AccountSessionManager extends LogicManager<AccountSession> {

    
    /**
     * 根据账户和IP和登录界面查询
     * @param account
     * @param ip
     * @param power
     * @return
     */
    public AccountSession getByAccountByIPAndPower(Account account, String ip, ClientPower power);
}
