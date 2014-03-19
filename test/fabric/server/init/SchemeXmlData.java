/**
 * @(#)SchemeXmlData.java, 2013-7-22. 
 * 
 */
package fabric.server.init;

/**
 * @author likaihua
 */
public class SchemeXmlData {
    /**
     * 方案的ID
     */
    private String id;

    /**
     * 方案封面
     */
    private String localPath;

    /**
     * 方案名
     */
    private String name;

    /**
     * 场景ID
     */
    private String sceneID;

    /**
     * 主花型ID
     */
    private String mainFlowerID;

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSceneID() {
        return sceneID;
    }

    public void setSceneID(String sceneID) {
        this.sceneID = sceneID;
    }

    public String getMainFlowerID() {
        return mainFlowerID;
    }

    public void setMainFlowerID(String mainFlowerID) {
        this.mainFlowerID = mainFlowerID;
    }

}
