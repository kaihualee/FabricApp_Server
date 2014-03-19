package fabric.server.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import fabric.common.db.BusinessDaoImpl;
import fabric.common.db.Status;
import fabric.server.dao.FlowerTypeDao;
import fabric.server.entity.Account;
import fabric.server.entity.FlowerType;

@Repository
public class FlowerTypeDaoImpl extends BusinessDaoImpl<FlowerType> implements
    FlowerTypeDao {

    @Override
    public List<FlowerType> getAllByTagId(Long tagId) {
        Criteria criteria = super.getSession().createCriteria(entityClass);
        criteria.createAlias("tags", "tags");
        criteria.add(Restrictions.eq("tags.id", tagId));
        criteria.add(Restrictions.ne("status", Status.Deleted));
        criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
        List<FlowerType> list = criteria.list();
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    @Override
    public List<FlowerType> getAllByIdsAndOwnerIdAndStatus(List<Long> ids,
        Long ownerID, Status... status) {
        for(Long id : ids){
            System.out.print(String.format("%s,", id));
        }
        Account owner = new Account(ownerID);
        Criteria criteria = super.getSession().createCriteria(entityClass);
        criteria.add(Restrictions.in("status", status));
        //criteria.add(Restrictions.in("id", ids));
        
        criteria.add(
            Restrictions.or(Restrictions.in("id", ids),Restrictions.eq("owner", owner))
            );
        return criteria.list();
    }
    
    /*
    @Override
    public List<FlowerType> getAllByOwnerIdAndStatus(Long ownerId, Status... status){
        String hql = "from flowertype where ownerID=:username and status in=:password";      
        Query query = super.getSession().createQuery(hql);      
        //第1种方式      
        query.setString("username", "name1");      
        query.setString("password", "password1");      
        //第2种方式,第3个参数确定类型      
        query.setParameter("username", "name1",Hibernate.STRING);      
        query.setParameter("password", "password1",Hibernate.STRING);   
    }*/
}
