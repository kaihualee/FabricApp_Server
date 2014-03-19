/**
 * @(#)CustomerDaoImpl.java, 2013-7-5. 
 * 
 */
package fabric.server.dao.impl;

import org.springframework.stereotype.Repository;

import fabric.common.db.BaseDaoImpl;
import fabric.server.dao.CustomerDao;
import fabric.server.entity.Customer;

/**
 *
 * @author likaihua
 *
 */
@Repository
public class CustomerDaoImpl extends BaseDaoImpl<Customer> implements CustomerDao {

}
