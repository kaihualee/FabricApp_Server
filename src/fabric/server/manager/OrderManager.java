/**
 * @(#)OrderManager.java, 2013-7-5. 
 * 
 */
package fabric.server.manager;

import java.util.List;

import fabric.common.db.BusinessManager;
import fabric.server.entity.Account;
import fabric.server.entity.Order;

/**
 *
 * @author likaihua
 *
 */
public interface OrderManager extends BusinessManager<Order> {
    
    /**
     * 根据店家商户和顾客电话查询订单
     * 只有超级管理员以及店家可以查看订单
     * @param account
     * @param phone
     * @return
     */
    public List<Order>  getAllByPhone(Account account, String phone);
}
