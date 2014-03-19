package fabric.server.entity;

import java.util.Set;

import fabric.common.db.BusinessEntityImpl;


/**
 * 方案
 * @author likaihua
 *
 */
public class Scheme extends BusinessEntityImpl {

    /**
     * 封面
     */
    private DataFile coverImage;

    /**
     * 配置
     */
    private DataFile xmlFile;

    /**
     * 场景
     */
    private Scene scene;

    /**
     * 方案 主花型
     */
    private FlowerType flowerType;

    /**
     * 方案 的花型
     */
    private Set<FlowerType> flowers;
    
    /**
     * @return the coverImage
     */
    public DataFile getCoverImage() {
        return coverImage;
    }

    /**
     * @param coverImage
     *            the coverImage to set
     */
    public void setCoverImage(DataFile coverImage) {
        this.coverImage = coverImage;
    }

    /**
     * @return the xmlFile
     */
    public DataFile getXmlFile() {
        return xmlFile;
    }

    /**
     * @param xmlFile
     *            the xmlFile to set
     */
    public void setXmlFile(DataFile xmlFile) {
        this.xmlFile = xmlFile;
    }

    /**
     * @return
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * @param scene
     */
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * @return
     */
    public FlowerType getFlowerType() {
        return flowerType;
    }

    /**
     * @param flowerType
     */
    public void setFlowerType(FlowerType flowerType) {
        this.flowerType = flowerType;
    }

    /**
     * @return the flowers
     */
    public Set<FlowerType> getFlowers() {
        return flowers;
    }

    /**
     * @param flowers the flowers to set
     */
    public void setFlowers(Set<FlowerType> flowers) {
        this.flowers = flowers;
    }


}
