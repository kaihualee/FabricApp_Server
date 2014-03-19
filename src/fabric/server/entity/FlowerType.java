package fabric.server.entity;

import java.util.Set;

import fabric.common.db.BusinessEntityImpl;


/**
 * 花型
 *
 * @author likaihua
 *
 */
public class FlowerType extends BusinessEntityImpl {

    /**
     * 封面
     */
    private DataFile coverImage;

    /**
     * 打印
     */
    private DataFile printImage;

    /**
     * 配置
     */
    private DataFile xmlFile;
    
    /**
     * 花型风格(弃用)
     */
    //private FlowerTypeStyle ftStyle;

    
    /**
     * 花型标签
     */
    private Set<FlowerTypeTag> tags;
    
    /**
     * 花型被应用的次数 
     */
    private Long references = 0L;
    
    /**
     * @return the xmlFile
     */
    public DataFile getXmlFile() {
        return xmlFile;
    }

    /**
     * @param xmlFile the xmlFile to set
     */
    public void setXmlFile(DataFile xmlFile) {
        this.xmlFile = xmlFile;
    }

    /**
     * @return the coverImage
     */
    public DataFile getCoverImage() {
        return coverImage;
    }

    /**
     * @param coverImage the coverImage to set
     */
    public void setCoverImage(DataFile coverImage) {
        this.coverImage = coverImage;
    }

    /**
     * @return the printImage
     */
    public DataFile getPrintImage() {
        return printImage;
    }

    /**
     * @param printImage the printImage to set
     */
    public void setPrintImage(DataFile printImage) {
        this.printImage = printImage;
    }


    /**
     * @return
     */
    public Set<FlowerTypeTag> getTags() {
        return tags;
    }

    /**
     * @param tags
     */
    public void setTags(Set<FlowerTypeTag> tags) {
        this.tags = tags;
    }

    /**
     * @return the references
     */
    public Long getReferences() {
        return references;
    }

    /**
     * @param references the references to set
     */
    public void setReferences(Long references) {
        this.references = references;
    }

}
