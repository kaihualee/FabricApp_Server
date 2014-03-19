/**
 * @(#)TagVO.java, 2013-7-19. 
 * 
 */
package fabric.server.vo;

import fabric.common.web.BaseVO;
import fabric.server.entity.FlowerTypeTag;

/**
 *
 * @author likaihua
 *
 */
public class TagVO extends BaseVO {
    
    /**
     * ID
     */
    private Long id;
    /**
     * 标签名
     */
    private String name;
    /**
     * 版本号
     */
    private int version;
    /**
     * 标签类型
     */
    private Long typeID;
    
    
    public TagVO(FlowerTypeTag tag){
        this.id = tag.getId();
        this.name = tag.getName();
        this.version = tag.getVersion();
        this.typeID = tag.getTagType().getId();
    }
    
    
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Long getTypeID() {
        return typeID;
    }
    public int getVersion() {
        return version;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setTypeID(Long typeID) {
        this.typeID = typeID;
    }
    public void setVersion(int version) {
        this.version = version;
    }
    
    
    
}
