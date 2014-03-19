package fabric.server.dao;

import java.util.List;

import fabric.common.db.BaseDao;
import fabric.common.db.BusinessDao;
import fabric.server.entity.Account;
import fabric.server.entity.Order;

/**
 * 
 *
 * @author likaihua
 *
 */
public interface OrderDao extends BusinessDao<Order> {
    /**
     * 根据店家账户和顾客电话来查询订单
     * @param account
     * @param phone
     * @return
     */
    public List<Order> getAllByPhone(Account account, String phone);
}
