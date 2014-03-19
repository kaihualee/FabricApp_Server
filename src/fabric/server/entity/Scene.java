package fabric.server.entity;

import fabric.common.db.BusinessEntityImpl;


/**
 * 场景
 *
 * @author likaihua
 *
 */
public class Scene extends BusinessEntityImpl {

    /**
     * CAB
     */
	private DataFile cab;
	
	/**
	 * 封面
	 */
	private DataFile coverImage;
	
	/**
	 * XML
	 */
	private DataFile xmlFile;
	
	/**
	 * 场景位置
	 */
	private ScenePosition scenePos;
	
	/**
	 * 场景风格
	 */
	private SceneStyle sceneStyle;

	/**
	 * 场景索引
	 */
	private Long index;
	
    /**
     * @return the cab
     */
    public DataFile getCab() {
        return cab;
    }

    /**
     * @param cab the cab to set
     */
    public void setCab(DataFile cab) {
        this.cab = cab;
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
     * @return the scenePos
     */
    public ScenePosition getScenePos() {
        return scenePos;
    }

    /**
     * @param scenePos the scenePos to set
     */
    public void setScenePos(ScenePosition scenePos) {
        this.scenePos = scenePos;
    }

    /**
     * @return the sceneStyle
     */
    public SceneStyle getSceneStyle() {
        return sceneStyle;
    }

    /**
     * @param sceneStyle the sceneStyle to set
     */
    public void setSceneStyle(SceneStyle sceneStyle) {
        this.sceneStyle = sceneStyle;
    }

    /**
     * @return the index
     */
    public Long getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(Long index) {
        this.index = index;
    }

    
	
}
