/**
 * @(#)FlowerTypeTagDao.java, 2013-7-17. 
 * 
 */
package fabric.server.dao;

import java.util.List;

import fabric.common.db.StyleDao;
import fabric.server.entity.FlowerTypeTag;
import fabric.server.entity.FlowerTypeTagType;

/**
 * @author likaihua
 */
public interface FlowerTypeTagDao extends StyleDao<FlowerTypeTag> {

    /**
     * 根据花型标签类型查询所有列表
     * @param tagType
     * @return
     */
    public List<FlowerTypeTag> getAllByTagType(FlowerTypeTagType tagType);

    /**
     * 根据花型标签名称,标签类型查询
     * @param name
     * @param tagType
     * @return
     */
    public FlowerTypeTag getByTagTypeAndTagName(String name,
        FlowerTypeTagType tagType);
    
    
    public FlowerTypeTag get(Long id);
}


