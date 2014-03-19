/**
 * @(#)FlowerTypeTagType.java, 2013-7-17. 
 * 
 */
package fabric.server.entity;

import fabric.common.db.BaseEntityImpl;

/**
 * 花型标签类型
 * @author likaihua
 */
public class FlowerTypeTagType extends BaseEntityImpl {

    
    /**
     * 类型名称
     */
    private String name;

    public FlowerTypeTagType() {}

    /**
     * @param tagType
     */
    public FlowerTypeTagType(TagTypeEnum tagType){
        this.name = tagType.getName();
    }
    
    /**
     * @param name
     */
    public FlowerTypeTagType(String name) {
        this.name = name;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
}
