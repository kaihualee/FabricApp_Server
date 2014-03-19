/**
 * @(#)SchemeVO.java, 2013-7-3. 
 * 
 */
package fabric.server.vo;

import java.text.SimpleDateFormat;
import java.util.Set;

import fabric.common.db.Status;
import fabric.common.utils.AppUtils;
import fabric.common.web.BaseVO;
import fabric.server.entity.Scheme;

/**
 * @author likaihua
 */
public class SchemeVO extends BaseVO {
    /**
     * id
     */
    private Long ID;

    /**
     * 方案名
     */
    private String Name;

    /**
     * 方案描述
     */
    private String Desc;

    /**
     * 方案创建者
     */
    private String OwnerName;

    /**
     * 封面图片
     */
    private String CoverImagePath;

    /**
     * XML URL
     */
    private String XMLPath;

    /**
     * 版本号
     */
    private int Version;

    /**
     * 场景ID
     */
    private Long sceneID;

    /**
     * 主花型
     */
    private Long mainFlowerTypeID;

    /**
     * 封面图片md5
     */
    private String coverMd5Code;

    /**
     * XML md5
     */
    private String XMLMd5Code;

    /**
     * 创建时间
     */
    private String createTime;
    
    /**
     * 修改时间
     */
    private String modifyTime;
    
    /**
     * 启用/禁用（未使用）
     */
    private boolean enable;
    
    /**
     * @param scheme
     */
    public SchemeVO(Scheme scheme) {
        this.ID = scheme.getId();
        this.Name = scheme.getName();
        this.Desc = scheme.getDescription();
        this.OwnerName = scheme.getOwner().getName();

        this.CoverImagePath = AppUtils.fetchSchemePath(scheme.getId(), scheme
            .getCoverImage().getName());
        this.coverMd5Code = scheme.getCoverImage().getMd5Code();

        this.XMLPath = AppUtils.fetchSchemePath(scheme.getId(), scheme
            .getXmlFile().getName());
        this.XMLMd5Code = scheme.getXmlFile().getMd5Code();
        this.Version = scheme.getVersion();

        this.sceneID = scheme.getScene().getId();
        this.mainFlowerTypeID = scheme.getFlowerType().getId();
        
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        this.createTime = sdf.format(scheme.getCreateDate());
        this.modifyTime = sdf.format(scheme.getModifyTime());
        if(scheme.getStatus() == Status.Normal){
            this.enable = true;
        }else{
            this.enable = false;
        }
        
    }

    /**
     * @return
     */
    public String getCoverImagePath() {
        return CoverImagePath;
    }

    /**
     * @return
     */
    public String getCoverMd5Code() {
        return coverMd5Code;
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
    public String getDesc() {
        return Desc;
    }

    /**
     * @return
     */
    public Long getID() {
        return ID;
    }

    /**
     * @return
     */
    public Long getMainFlowerTypeID() {
        return mainFlowerTypeID;
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
    public String getName() {
        return Name;
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
    public Long getSceneID() {
        return sceneID;
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
    public String getXMLMd5Code() {
        return XMLMd5Code;
    }

    /**
     * @return
     */
    public String getXMLPath() {
        return XMLPath;
    }

    /**
     * @return
     */
    public boolean isEnable() {
        return enable;
    }

    /**
     * @param coverImagePath
     */
    public void setCoverImagePath(String coverImagePath) {
        CoverImagePath = coverImagePath;
    }

    /**
     * @param coverMd5Code
     */
    public void setCoverMd5Code(String coverMd5Code) {
        this.coverMd5Code = coverMd5Code;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * @param desc
     */
    public void setDesc(String desc) {
        Desc = desc;
    }

    /**
     * @param enable
     */
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    /**
     * @param iD
     */
    public void setID(Long iD) {
        ID = iD;
    }

    /**
     * @param mainFlowerTypeID
     */
    public void setMainFlowerTypeID(Long mainFlowerTypeID) {
        this.mainFlowerTypeID = mainFlowerTypeID;
    }

    /**
     * @param modifyTime
     */
    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        Name = name;
    }

    /**
     * @param ownerName
     */
    public void setOwnerName(String ownerName) {
        OwnerName = ownerName;
    }

    /**
     * @param sceneID
     */
    public void setSceneID(Long sceneID) {
        this.sceneID = sceneID;
    }

    /**
     * @param version
     */
    public void setVersion(int version) {
        Version = version;
    }

    /**
     * @param xMLMd5Code
     */
    public void setXMLMd5Code(String xMLMd5Code) {
        XMLMd5Code = xMLMd5Code;
    }

    /**
     * @param xMLPath
     */
    public void setXMLPath(String xMLPath) {
        XMLPath = xMLPath;
    }

}
