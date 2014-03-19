/**
 * @(#)CustomerManagerImpl.java, 2013-7-5. 
 * 
 */
package fabric.server.manager.impl;

import org.springframework.stereotype.Service;

import fabric.common.db.BaseManagerImpl;
import fabric.server.dao.CustomerDao;
import fabric.server.entity.Customer;
import fabric.server.manager.CustomerManager;

/**
 * @author likaihua
 */
public class CustomerManagerImpl extends BaseManagerImpl<Customer, CustomerDao>
    implements CustomerManager {

}
