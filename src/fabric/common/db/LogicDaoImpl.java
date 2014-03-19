/**
 * @(#)LogicDaoImpl.java, 2013-6-29. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.common.db;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 * LogicEntityImpl对应DAO接口实现
 * 
 * @author nisonghai
 */
public class LogicDaoImpl<T extends LogicEntityImpl> extends BaseDaoImpl<T>
    implements LogicDao<T> {

    @Override
    public void deleteById(Long id) {
        T entity = get(id);
        entity.setStatus(Status.Deleted);

    }

    @Override
    public List<T> getAll() {
        Criteria criteria = super.getSession().createCriteria(entityClass);
        criteria.add(Restrictions.ne("status", Status.Deleted));
        criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> getAllByStatus(Status... status) {
        Criteria criteria = super.getSession().createCriteria(entityClass);
        criteria.add(Restrictions.in("status", status));
        criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    @Override
    public T getById(Long id) {
        Criteria criteria = super.getSession().createCriteria(entityClass);
        criteria.add(Restrictions.eq("id", id));
        criteria.add(Restrictions.or(Restrictions.eq("status", Status.Normal),
            Restrictions.eq("status", Status.Disabled)));
        criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
        List<T> list = criteria.list();
        if (list == null || list.isEmpty()) {
            return null;
        } else if (list.size() == 1) {
            return list.get(0);
        } else {
            throw new DaoException("Select list not only one.");
        }
    }

    @Override
    public T getByStatusAndId(Long id, Status... status) {
        Criteria criteria = super.getSession().createCriteria(entityClass);
        criteria.add(Restrictions.eq("id", id));
        criteria.add(Restrictions.in("status", status));
        criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
        List<T> list = criteria.list();
        if (list == null || list.isEmpty()) {
            return null;
        } else if (list.size() == 1) {
            return list.get(0);
        } else {
            throw new DaoException("Select list not only one.");
        }
    }

    @Override
    public T getNormalById(Long id) {
        return getByStatusAndId(id, Status.Normal);
    }

    @Override
    public List<T> getListByIdAndStatus(List<Long> ids, Status... status) {
        Criteria criteria = super.getSession().createCriteria(entityClass);
        criteria.add(Restrictions.in("id", ids));
        criteria.add(Restrictions.in("status", status));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }
}
