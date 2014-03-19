/**
 * @(#)FlowerTypeTagTypeManager.java, 2013-7-17. 
 * 
 */
package fabric.server.manager;

import fabric.common.db.BaseManager;
import fabric.server.entity.FlowerTypeTagType;

/**
 *
 * @author likaihua
 *
 */
public interface FlowerTypeTagTypeManager extends BaseManager<FlowerTypeTagType> {
    
    public FlowerTypeTagType getByName(String name);
}
