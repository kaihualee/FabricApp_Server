package fabric.server.manager.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fabric.common.db.LogicManagerImpl;
import fabric.common.db.Status;
import fabric.server.dao.AccountDao;
import fabric.server.entity.Account;
import fabric.server.entity.Shop;
import fabric.server.entity.UserRule;
import fabric.server.manager.AccountManager;
import fabric.server.manager.ShopManager;

@Service
public class AccountManagerImpl extends LogicManagerImpl<Account, AccountDao>
    implements AccountManager {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private ShopManager shopManager;

    @Override
    public void saveOrUpdate(Account account) {
        if (account.getRule() == UserRule.BUSINESS
            || account.getRule() == UserRule.GENERAL) {
            Shop shop = account.getShop();
            if (shop == null) {
                // TODO throw exception
            } else {
                shop = shopManager.saveByName(shop);
                account.setShop(shop);
            }
        }

        if (account.getId() == null) {
            accountDao.save(account);
        } else {
            accountDao.update(account);
        }
    }

    /*
     * 检查用户名和密码是否正确
     */
    @Override
    public boolean verifyAccount(Long accountId, String password) {
        if (accountId == null) {
            return false;
        }
        if (StringUtils.isEmpty(password)) {
            return false;
        }

        return accountDao.checkPassword(accountId, password);
    }

    @Override
    public Account getAccountByName(UserRule rule, String name, Shop shop) {
        return accountDao.getByName(rule, name, shop);
    }

    /*
     * 检查用户名和密码是否正确
     */
    @Override
    public Account getAccountByName(UserRule rule, String name) {
        return accountDao.getByName(rule, name);
    }

    @Override
    public List<Account> getAll(Long id) {
        Account account = accountDao.get(id);
        if (account == null || account.getStatus() != Status.Normal
            || account.getRule() != UserRule.SUPER_ADMIN) {
            return null;
        } else {
            return accountDao.getAll();
        }
    }

    @Override
    public boolean checkNickname(UserRule rule, Account account, String nickname) {
        return accountDao.checkNickname(rule, account.getId(), nickname);
    }

    @Override
    public List<Account> getAllByRule(Account account, UserRule rule) {
        if (account == null || rule == null || rule == UserRule.SUPER_ADMIN) {
            return null;
        }
        return accountDao.getAllByRule(account.getId(), rule);
    }

    @Override
    public void delete(Account account) {
        if (account == null) {
            return;
        }
        if (account.getRule() == UserRule.BUSINESS) {
            Shop shop = account.getShop();
            shopManager.deleteById(shop.getId());
        }
        super.deleteById(account.getId());
    }

}
