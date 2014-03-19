/**
 * @(#)FlowerTypeTagTypeImpl.java, 2013-7-17. 
 * 
 */
package fabric.server.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import fabric.common.db.BaseDaoImpl;
import fabric.common.db.DaoException;
import fabric.common.db.Status;
import fabric.server.dao.FlowerTypeTagTypeDao;
import fabric.server.entity.FlowerTypeTagType;

/**
 * @author likaihua
 */
@Repository
public class FlowerTypeTagTypeDaoImpl extends BaseDaoImpl<FlowerTypeTagType>
    implements FlowerTypeTagTypeDao {
    public FlowerTypeTagType getByName(String name) {
        Criteria criteria = super.getSession().createCriteria(entityClass);
        criteria.add(Restrictions.eq("name", name));
        List<FlowerTypeTagType> list = criteria.list();
        if (list == null || list.isEmpty()) {
            return null;
        } else if (list.size() == 1) {
            return list.get(0);
        } else {
            throw new DaoException("Select list not only one!");
        }
    }
}
