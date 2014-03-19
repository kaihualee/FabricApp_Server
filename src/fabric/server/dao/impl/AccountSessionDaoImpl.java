/**
 * @(#)AccountSessionDaoImpl.java, 2013-7-19. 
 * 
 */
package fabric.server.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import fabric.common.db.DaoException;
import fabric.common.db.LogicDaoImpl;
import fabric.common.db.Status;
import fabric.server.dao.AccountSessionDao;
import fabric.server.entity.Account;
import fabric.server.entity.AccountSession;
import fabric.server.entity.ClientPower;

/**
 * @author likaihua
 */
@Repository
public class AccountSessionDaoImpl extends LogicDaoImpl<AccountSession>
    implements AccountSessionDao {

    @Override
    public AccountSession getByAccountByIPAndPower(Account account, String ip,
        ClientPower power) {
        Criteria critera = super.getSession().createCriteria(entityClass);
        critera.add(Restrictions.eq("account", account));
        critera.add(Restrictions.eq("ip", ip));
        critera.add(Restrictions.eq("power", power));
        critera.add(Restrictions.eq("status", Status.Normal));
        List<AccountSession> list = critera.list();

        if (list == null || list.isEmpty()) {
            return null;
        } else if (list.size() == 1) {
            return list.get(0);
        } else {
            logger
                .info(
                    "AccountSession not only one entity.Account:{}, ip:{}, power:{}",
                    new Object[] { account.getName(), ip, power.name() });
            return list.get(0);

            //throw new DaoException("Select not only one entity.");
        }
    }
}
