package fabric.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import fabric.common.db.BaseDaoImpl;
import fabric.common.db.BusinessDaoImpl;
import fabric.server.dao.OrderDao;
import fabric.server.entity.Account;
import fabric.server.entity.Customer;
import fabric.server.entity.Order;

/**
 * 
 *
 * @author likaihua
 *
 */
@Repository
public class OrderDaoImpl extends BusinessDaoImpl<Order> implements OrderDao {

    /**
     *
     */
    @Override
    public List<Order> getAllByPhone(Account account, String phone) {
        
        List<Order> list = getAllByOwnerId(account.getId());
        if(list == null || list.isEmpty()){
            return null;
        }
        List<Order> result = new ArrayList<Order>();
        for(Order item : list){
            Customer customer = item.getCustomer();
            if(customer.getPhone().equals(phone)){
                result.add(item);
            }
        }
        return result;
    }
}
