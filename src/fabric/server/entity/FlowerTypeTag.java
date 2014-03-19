/**
 * @(#)FlowerTypeTag.java, 2013-7-17. 
 * 
 */
package fabric.server.entity;

import java.util.Set;

import fabric.common.db.StyleEntityImpl;

/**
 * 花型标签
 * 
 * @author likaihua
 */
public class FlowerTypeTag extends StyleEntityImpl {

    /**
     * 标签类型
     */
    private FlowerTypeTagType tagType;

    /**
     * @return
     */
    public FlowerTypeTagType getTagType() {
        return tagType;
    }

    /**
     * @param tagType
     */
    public void setTagType(FlowerTypeTagType tagType) {
        this.tagType = tagType;
    }

}
