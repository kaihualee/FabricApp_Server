/**
 * @(#)TagTypeEnum.java, 2013-7-17. 
 * 
 */
package fabric.server.entity;

/**
 * @author likaihua
 */
public enum TagTypeEnum {

    /**
     * 花型风格
     */
    STYLE(1L, "风格"),
    /**
     * 花型元素
     */
    ElEMENTS(2L, "元素"), 
    /**
     * 花型其他（省略）
     */
    ELSE(3L, "其他"), 
    /**
     * 花型推荐
     */
    RECOMMEND(4L, "推荐"), 
    /**
     * 花型色调
     */
    COLOR(5L, "色调"),
    
    PRODUCT(6L,"产品");

    private Long id;

    private String name;

    /**
     * @param id
     * @param name
     */
    private TagTypeEnum(Long id, String name) {
        this.name = name;
        this.id = id;
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

    /**
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @param id
     * @return
     */
    public static TagTypeEnum valueof(Long id) {
        if (STYLE.getId().equals(id)) {
            return STYLE;
        } else if (ElEMENTS.getId().equals(id)) {
            return ElEMENTS;
        } else if (ELSE.getId().equals(id)) {
            return ELSE;
        } else if (COLOR.getId().equals(id)) {
            return COLOR;
        } else if(RECOMMEND.getId().equals(id)){
            return RECOMMEND;
        }else if(PRODUCT.getId().equals(id)){
            return PRODUCT;
        }else{
            return null;
        }
    }
    /**
     * @param name
     * @return
     */
    public static TagTypeEnum valueof(String name) {
        if (STYLE.getName().equals(name)) {
            return STYLE;
        } else if (ElEMENTS.getName().equals(name)) {
            return ElEMENTS;
        } else if (ELSE.getName().equals(name)) {
            return ELSE;
        } else if (COLOR.getName().equals(name)) {
            return COLOR;
        } else if(RECOMMEND.getName().equals(name)){
            return RECOMMEND;
        }else if(PRODUCT.getName().equals(name)){
            return PRODUCT;
        }else{
            return null;
        }
    }
}
