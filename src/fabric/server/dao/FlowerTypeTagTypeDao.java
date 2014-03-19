/**
 * @(#)FlowerTypeTagTypeDao.java, 2013-7-17. 
 * 
 */
package fabric.server.dao;

import fabric.common.db.BaseDao;
import fabric.server.entity.FlowerTypeTagType;

/**
 *
 * @author likaihua
 *
 */
public interface FlowerTypeTagTypeDao extends BaseDao<FlowerTypeTagType> {

    
    /**
     * 根据花型标签类型名称查询
     * @param name
     * @return
     */
    public FlowerTypeTagType getByName(String name);
}
