/**
 * @(#)OrderManagerImpl.java, 2013-7-5. 
 * 
 */
package fabric.server.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fabric.common.db.BaseManagerImpl;
import fabric.common.db.BusinessManagerImpl;
import fabric.server.dao.OrderDao;
import fabric.server.entity.Account;
import fabric.server.entity.Order;
import fabric.server.entity.UserRule;
import fabric.server.manager.OrderManager;

/**
 * @author likaihua
 */
@Service
public class OrderManagerImpl extends BusinessManagerImpl<Order, OrderDao>
    implements OrderManager {
    
    @Autowired
    private OrderDao orderDao;
    
    @Override
    public List<Order>  getAllByPhone(Account account, String phone){
        UserRule rule = account.getRule();
        if( rule == UserRule.SUPER_ADMIN){ 
            return orderDao.getAll(); 
        }else if(rule == UserRule.BUSINESS){
            return orderDao.getAllByPhone(account, phone); 
        }else{
            return null;
        }
    }
}
