/**
 * @(#)BusinessDaoImpl.java, 2013-6-29. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.common.db;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import fabric.common.utils.PageBean;
import fabric.server.entity.Account;

/**
 * 业务对象DAO接口实现
 * 
 * @see BusinessDao
 * @author nisonghai
 */
public class BusinessDaoImpl<T extends BusinessEntityImpl> extends
    LogicDaoImpl<T> implements BusinessDao<T> {

    @Override
    public boolean checkName(String name) {
        return checkName(name, null);
    }

    @Override
    public boolean checkName(String name, Long id) {
        Criteria criteria = super.getSession().createCriteria(entityClass);
        criteria.add(Restrictions.eq("name", name));
        if (id != null && id.longValue() > 0) {
            criteria.add(Restrictions.ne("id", id));
        }
        criteria.add(Restrictions.ne("status", Status.Deleted));
        criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
        List<T> list = criteria.list();
        if (list == null || list.isEmpty()) {
            return false;
        }
        return true;
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
    public List<T> getAllByOwnerId(Long ownerId) {
        Criteria criteria = super.getSession().createCriteria(entityClass);
        Account owner = new Account(ownerId);
        criteria.add(Restrictions.eq("owner", owner));
        criteria.add(Restrictions.ne("status", Status.Deleted));
        criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
        //criteria.add(Restrictions.or(
        //    Restrictions.eq("status", Status.Normal), Restrictions.eq("status", Status.Disabled))
        //    );
        List<T> list = criteria.list();
        if(list == null || list.isEmpty()){
            return null;
        }else{
            return list;
        }
    }

    @Override
    public List<T> getAllByOwnerIdAndStatus(Long ownerId, Status... status){
        Criteria criteria = super.getSession().createCriteria(entityClass);
        Account owner = new Account(ownerId);
        criteria.add(Restrictions.eq("owner", owner));
        criteria.add(Restrictions.in("status", status));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<T> list = criteria.list();
        if(list == null || list.isEmpty()){
            return null;
        }else{
            return list;
        }
    }

    @Override
    public List<T> getAllByOwnerIdAndStatus(Long ownerId, PageBean pageBean, Status...status){
        Criteria criteria = super.getSession().createCriteria(entityClass);
        Account owner = new Account(ownerId);
        criteria.add(Restrictions.eq("owner", owner));
        criteria.add(Restrictions.in("status", status));
        criteria.setFirstResult(pageBean.getFirstResult());
        criteria.setMaxResults(pageBean.getMaxResults());
        criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
        List<T> list = criteria.list();
        if(list == null || list.isEmpty()){
            return null;
        }else{
            return list;
        }
    }
    
    @Override
    public T getByIdUseOwner(Long ownerId, Long id) {
        Criteria criteria = super.getSession().createCriteria(entityClass);
        Account owner = new Account(ownerId);
        criteria.add(Restrictions.eq("id", id));
        criteria.add(Restrictions.eq("owner", owner));
        criteria.add(Restrictions.ne("status", Status.Deleted));
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
    
    
    @SuppressWarnings("unchecked")
    @Override
    public T getByName(Long ownerId, String name) {
        Criteria criteria = super.getSession().createCriteria(entityClass);
        Account owner = new Account(ownerId);
        criteria.add(Restrictions.eq("owner", owner));
        criteria.add(Restrictions.eq("name", name));
        criteria.add(Restrictions.ne("status", Status.Deleted));
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
}
