/**
 * @(#)AccountSessionManagerImpl.java, 2013-7-19. 
 * 
 */
package fabric.server.manager.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fabric.common.db.BaseManagerImpl;
import fabric.common.db.LogicManagerImpl;
import fabric.server.dao.AccountSessionDao;
import fabric.server.dao.CustomerDao;
import fabric.server.entity.Account;
import fabric.server.entity.AccountSession;
import fabric.server.entity.ClientPower;
import fabric.server.entity.Customer;
import fabric.server.manager.AccountManager;
import fabric.server.manager.AccountSessionManager;
import fabric.server.manager.CustomerManager;

/**
 * @author likaihua
 */
@Service
public class AccountSessionManagerImpl extends
    LogicManagerImpl<AccountSession, AccountSessionDao> implements
    AccountSessionManager {

    @Autowired
    private AccountSessionDao dao;


    @Override
    public AccountSession getByAccountByIPAndPower(Account account, String ip,
        ClientPower power) {
        if (account == null || ip == null || StringUtils.isEmpty(ip)
            || power == null) {
            return null;
        }
        return dao.getByAccountByIPAndPower(account, ip, power);
    }
}
