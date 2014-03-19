package fabric.server.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import fabric.common.db.BusinessDaoImpl;
import fabric.common.db.Status;
import fabric.common.utils.PageBean;
import fabric.server.dao.SchemeDao;
import fabric.server.entity.Scheme;

@Repository
public class SchemeDaoImpl extends BusinessDaoImpl<Scheme> implements SchemeDao {

    @Override
    public List<Scheme> getAllByFlowerTypeIdAndStatus(Long flowerId,
        PageBean pageBean, Status... status) {
        Criteria criteria = super.getSession().createCriteria(entityClass);
        criteria.createAlias("flowers", "flowers");
        criteria.add(Restrictions.eq("flowers.id", flowerId));
        criteria.add(Restrictions.in("status", status));
        criteria.setFirstResult(pageBean.getFirstResult());
        criteria.setMaxResults(pageBean.getMaxResults());
        return criteria.list();
    }
    
    @Override
    public List<Scheme> getAllByFlowerTypeIdAndStatus(Long flowerId,
        Status... status){
        Criteria criteria = super.getSession().createCriteria(entityClass);
        criteria.createAlias("flowers", "flowers");
        criteria.add(Restrictions.eq("flowers.id", flowerId));
        criteria.add(Restrictions.in("status", status));
        return criteria.list();
    }
}
