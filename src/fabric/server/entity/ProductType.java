/**
 * @(#)ProductType.java, 2013-7-5. 
 * 
 */
package fabric.server.entity;

/**
 *
 * @author likaihua
 *
 */
public enum ProductType {
    
    /**
     * 花型
     */
    FlowerType(0L),
    /**
     * 方案
     */
    Scheme(2L),
    /**
     * 设计
     */
    Design(1L),
    /**
     * 场景
     */
    Scene(3L);
    
    private Long type;

    /**
     * @param type
     */
    private ProductType(Long type){
        this.type = type;
    }
    
    /**
     * @return
     */
    public Long getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(Long type) {
        this.type = type;
    }
    

    /**
     * @param type
     * @return
     */
    public static ProductType valueOf(Long type) {
        if (type == Design.type) {
            return Design;
        }else if(type == FlowerType.type){
            return FlowerType;
        }else if(type == Scheme.type){
            return Scheme;
        }else if(type == Scene.type){
            return Scene;
        }else{
            return null;
        }
    }
    
}
