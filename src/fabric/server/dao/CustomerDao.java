/**
 * @(#)CustomerDao.java, 2013-7-5. 
 * 
 */
package fabric.server.dao;

import org.springframework.stereotype.Repository;

import fabric.common.db.BaseDao;
import fabric.server.entity.Customer;

/**
 * @author likaihua
 */
@Repository
public interface CustomerDao extends BaseDao<Customer> {

}
