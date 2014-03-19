/**
 * @(#)StyleDaoImpl.java, 2013-6-29. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.common.db;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 * @author nisonghai
 */
public class StyleDaoImpl<T extends StyleEntityImpl> extends LogicDaoImpl<T>
    implements StyleDao<T> {

    @SuppressWarnings("unchecked")
    @Override
    public T getByName(String name) {
        Criteria criteria = super.getSession().createCriteria(entityClass);
        criteria.add(Restrictions.eq("name", name));
        criteria.add(Restrictions.ne("status", Status.Deleted));
        List<T> list = criteria.list();
        if (list == null || list.isEmpty()) {
            return null;
        } else if (list.size() == 1) {
            return list.get(0);
        } else {
            throw new DaoException("Select list not only one.");
        }
    }

}
