/**
 * @(#)FlowerTypeTagManager.java, 2013-7-17. 
 * 
 */
package fabric.server.manager;

import java.util.List;

import fabric.common.db.StyleManager;
import fabric.server.entity.FlowerTypeTag;

/**
 *
 * @author likaihua
 *
 */
public interface FlowerTypeTagManager extends StyleManager<FlowerTypeTag> {
    
    /**
     * 根据花型标签类型名查找花型标签列表
     * @param name
     * @return
     */
    public List<FlowerTypeTag> getAllByTagTypeName(String name);
    
    /**
     * 根据花型标签类型名和花型标签名查找花型标签
     * @param name
     * @param tagName
     * @return
     */
    public FlowerTypeTag getByTagTypeAndTagName(String tagTypeName, String tagName);
    
    /**
     * 根据花型ID和花型标签类型查找花型标签
     * @param tagTypeName
     * @param id
     * @return
     */
    public FlowerTypeTag getByTagTypeAndTagId(String tagTypeName, Long id);
    
    public void save(FlowerTypeTag entity);
}
