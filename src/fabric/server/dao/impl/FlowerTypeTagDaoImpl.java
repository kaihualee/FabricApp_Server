/**
 * @(#)FlowerTypeTagDaoImpl.java, 2013-7-17. 
 * 
 */
package fabric.server.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import fabric.common.db.Status;
import fabric.common.db.StyleDaoImpl;
import fabric.server.dao.FlowerTypeTagDao;
import fabric.server.entity.FlowerTypeTag;
import fabric.server.entity.FlowerTypeTagType;

/**
 * @author likaihua
 */
@Repository
public class FlowerTypeTagDaoImpl extends StyleDaoImpl<FlowerTypeTag> implements
    FlowerTypeTagDao {

    @Override
    public List<FlowerTypeTag> getAllByTagType(FlowerTypeTagType tagType) {
        Criteria criteria = super.getSession().createCriteria(entityClass);
        criteria.add(Restrictions.eq("tagType", tagType));
        criteria.add(Restrictions.ne("status", Status.Deleted));
        List<FlowerTypeTag> list = criteria.list();
        if (list == null || list.isEmpty())
            return null;
        return list;
    }

    @Override
    public FlowerTypeTag getByTagTypeAndTagName(String name,
        FlowerTypeTagType tagType) {
        Criteria criteria = super.getSession().createCriteria(entityClass);
        criteria.add(Restrictions.eq("tagType", tagType));
        criteria.add(Restrictions.eq("name", name));
        criteria.add(Restrictions.ne("status", Status.Deleted));
        List<FlowerTypeTag> list = criteria.list();
        if (list == null || list.isEmpty()) {
            return null;
        } else if (list.size() == 1) {
            return list.get(0);
        } else {
            return null;
        }
    }
    
    @Override
    public FlowerTypeTag get(Long id){
        Criteria criteria = super.getSession().createCriteria(entityClass);
        criteria.add(Restrictions.eq("id", id));
        criteria.add(Restrictions.ne("status", Status.Deleted));
        List<FlowerTypeTag> list = criteria.list();
        if (list == null || list.isEmpty()) {
            return null;
        } else if (list.size() == 1) {
            return list.get(0);
        } else {
            return null;
        }
    }

}
