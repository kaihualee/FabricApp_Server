/**
 * @(#)SceneVO.java, 2013-7-3. 
 * 
 */
package fabric.server.vo;

import java.text.SimpleDateFormat;

import org.restlet.representation.Representation;

import fabric.common.utils.AppUtils;
import fabric.common.web.BaseVO;
import fabric.server.entity.Account;
import fabric.server.entity.DataFile;
import fabric.server.entity.Scene;

/**
 *
 * @author likaihua
 *
 */
public class SceneVO extends BaseVO {
    /**
     * 场景ID
     */
    private Long SceneID;
    
    /**
     * 场景名
     */
    private String SceneName;
    
    /**
     * 场景描述
     */
    private String SceneDesc;
    
    /**
     * CAB URL
     */
    private String CABUrl;
    
    /**
     * 封面 URL
     */
    private String CoverUrl;
    
    /**
     * XML URL
     */
    private String XMLUrl;
    
    /**
     * 版本号
     */
    private int Version;
    
    /**
     * 场景风格
     */
    private Long SceneStyleID;
    
    /**
     * 场景位置
     */
    private Long ScenePositionID;
    
    
    /**
     * 创建者
     */
    private String OwnerName;
    
    /**
     * 场景风格名称
     */
    private String SceneStyleName;
    
    /**
     * 场景位置名称
     */
    private String ScenePosName;
    
    /**
     * 创建时间
     */
    private String createTime;
    
    /**
     * 修改时间
     */
    private String modifyTime;
    
    /**
     * 场景索引
     */
    private Long index;
    
    /**
     * @param scene
     */
    public SceneVO(Scene scene){
        Long id = scene.getId();
        this.SceneID = scene.getId();
        this.SceneName = scene.getName();
        this.SceneDesc = scene.getDescription();
        
        DataFile dataFile = scene.getCoverImage();
        this.CoverUrl = AppUtils.fetchScenePath(id, dataFile.getName());
        dataFile = scene.getCab();
        this.CABUrl =  AppUtils.fetchScenePath(id, dataFile.getName());
        dataFile = scene.getXmlFile();
        this.XMLUrl =  AppUtils.fetchScenePath(id, dataFile.getName());
        
        this.Version = scene.getVersion();
        this.SceneStyleID = scene.getSceneStyle().getId();
        this.SceneStyleName = scene.getSceneStyle().getName();
        this.ScenePositionID = scene.getScenePos().getId();
        this.ScenePosName = scene.getScenePos().getName();
        this.OwnerName = scene.getOwner().getName();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createTime = sdf.format(scene.getCreateDate());
        this.modifyTime = sdf.format(scene.getModifyTime());
        
        this.index = scene.getIndex();
    }
    
    /**
     * @return
     */
    public String getCABUrl() {
        return CABUrl;
    }
    /**
     * @return
     */
    public String getCoverUrl() {
        return CoverUrl;
    }
    /**
     * @return
     */
    public String getCreateTime() {
        return createTime;
    }
    /**
     * @return
     */
    public String getModifyTime() {
        return modifyTime;
    }
    /**
     * @return
     */
    public String getOwnerName() {
        return OwnerName;
    }
    /**
     * @return
     */
    public String getSceneDesc() {
        return SceneDesc;
    }
    /**
     * @return
     */
    public Long getSceneID() {
        return SceneID;
    }
    /**
     * @return
     */
    public String getSceneName() {
        return SceneName;
    }
    /**
     * @return
     */
    public Long getScenePositionID() {
        return ScenePositionID;
    }
    /**
     * @return
     */
    public String getScenePosName() {
        return ScenePosName;
    }
    /**
     * @return
     */
    public Long getSceneStyleID() {
        return SceneStyleID;
    }
    /**
     * @return
     */
    public String getSceneStyleName() {
        return SceneStyleName;
    }
    /**
     * @return
     */
    public int getVersion() {
        return Version;
    }
    /**
     * @return
     */
    public String getXMLUrl() {
        return XMLUrl;
    }
    /**
     * @param cABUrl
     */
    public void setCABUrl(String cABUrl) {
        CABUrl = cABUrl;
    }
    /**
     * @param coverUrl
     */
    public void setCoverUrl(String coverUrl) {
        CoverUrl = coverUrl;
    }
    /**
     * @param createTime
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    /**
     * @param modifyTime
     */
    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }
    /**
     * @param ownerName
     */
    public void setOwnerName(String ownerName) {
        OwnerName = ownerName;
    }
    /**
     * @param description
     */
    public void setSceneDesc(String description) {
        SceneDesc = description;
    }

    /**
     * @param sceneID
     */
    public void setSceneID(Long sceneID) {
        SceneID = sceneID;
    }

    /**
     * @param sceneName
     */
    public void setSceneName(String sceneName) {
        SceneName = sceneName;
    }

    /**
     * @param scenePosID
     */
    public void setScenePositionID(Long scenePosID) {
        ScenePositionID = scenePosID;
    }

    /**
     * @param scenePosName
     */
    public void setScenePosName(String scenePosName) {
        ScenePosName = scenePosName;
    }

    /**
     * @param sceneStyleID
     */
    public void setSceneStyleID(Long sceneStyleID) {
        SceneStyleID = sceneStyleID;
    }

    /**
     * @param sceneStyleName
     */
    public void setSceneStyleName(String sceneStyleName) {
        SceneStyleName = sceneStyleName;
    }

    /**
     * @param version
     */
    public void setVersion(int version) {
        Version = version;
    }

    /**
     * @param xMLUrl
     */
    public void setXMLUrl(String xMLUrl) {
        XMLUrl = xMLUrl;
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
