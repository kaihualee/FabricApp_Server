/**
 * @(#)GrantTableDaoImpl.java, 2013-7-5. 
 * 
 */
package fabric.server.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import fabric.common.db.BaseDaoImpl;
import fabric.common.db.BusinessDaoImpl;
import fabric.common.db.DaoException;
import fabric.common.db.Status;
import fabric.common.utils.PageBean;
import fabric.server.dao.GrantTableDao;
import fabric.server.entity.Account;
import fabric.server.entity.GrantTable;
import fabric.server.entity.ProductType;
import fabric.server.entity.Shop;

/**
 * @author likaihua
 */
@Repository
public class GrantTableDaoImpl extends BusinessDaoImpl<GrantTable> implements
    GrantTableDao {

    @Override
    public List<GrantTable> getAllByPA(Long partAId) {
        return getAllByPAPBAndStatus(partAId, null, Status.Normal,
            Status.Disabled);
    }

    @Override
    public List<GrantTable> getAllByPAPB(Long partAId, Long partBId) {
        return getAllByPAPBAndStatus(partAId, partBId, Status.Normal,
            Status.Disabled);
    }

    protected List<GrantTable> getAllByPAPBAndStatus(Long partAId,
        Long partBId, Status... status) {
        Criteria criteria = super.getSession().createCriteria(entityClass);
        if (partBId != null) {
            Account partB = new Account();
            partB.setId(partBId);
            criteria.add(Restrictions.eq("partB", partB));
        }
        Account partA = new Account();
        partA.setId(partAId);
        criteria.add(Restrictions.eq("partA", partA));
        criteria.add(Restrictions.in("status", status));
        List<GrantTable> list = criteria.list();
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    @Override
    public List<GrantTable> getAllByTypeAndStatus(Long partBId,
        ProductType type, Status... status) {
        Criteria criteria = super.getSession().createCriteria(entityClass);
        Account partB = new Account();
        partB.setId(partBId);
        criteria.add(Restrictions.eq("partB", partB));
        criteria.add(Restrictions.eq("type", type));
        criteria.add(Restrictions.in("status", status));
        List<GrantTable> list = criteria.list();
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    @Override
    public List<GrantTable> getAllByPAAndTypeAndStatus(Long partAId,
        ProductType type, Status... status) {
        Criteria criteria = super.getSession().createCriteria(entityClass);
        Account partA = new Account();
        partA.setId(partAId);
        criteria.add(Restrictions.eq("partA", partA));
        criteria.add(Restrictions.eq("type", type));
        criteria.add(Restrictions.in("status", status));
        List<GrantTable> list = criteria.list();
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    @Override
    public GrantTable getByID(Long partBId, Long id, ProductType type) {
        return getByIDAndStatus(null, partBId, type, id, Status.Normal,
            Status.Disabled);
    }

    @Override
    public GrantTable getByIDAndStatus(Long partAId, Long partBId,
        ProductType type, Long productID, Status... status) {
        Criteria criteria = super.getSession().createCriteria(entityClass);
        if (partAId != null) {
            Account partA = new Account();
            partA.setId(partAId);
            criteria.add(Restrictions.eq("partA", partA));
        }
        Account partB = new Account();
        partB.setId(partBId);
        criteria.add(Restrictions.eq("partB", partB));
        criteria.add(Restrictions.eq("type", type));
        criteria.add(Restrictions.eq("product", productID));
        criteria.add(Restrictions.in("status", status));
        List<GrantTable> list = criteria.list();
        if (list == null || list.isEmpty()) {
            return null;
        } else if (list.size() == 1) {
            return list.get(0);
        } else {
            throw new DaoException("Select list not only one.");
        }
    }
    @Override
    public void deleteByPAAndTypeAndID(Long partAId, ProductType type,
        Long productID) {
        Criteria criteria = super.getSession().createCriteria(entityClass);
        Account partA = new Account();
        partA.setId(partAId);
        criteria.add(Restrictions.eq("partA", partA));
        criteria.add(Restrictions.eq("type", type));
        criteria.add(Restrictions.eq("product", productID));
        criteria.add(Restrictions.ne("status", Status.Deleted));
        List<GrantTable> list = criteria.list();
        if (list == null || list.isEmpty()) {
            return;
        }
        for (GrantTable item: list) {
            deleteById(item.getId());
        }

    }
}
