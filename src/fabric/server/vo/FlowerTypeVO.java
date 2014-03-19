package fabric.server.vo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;

import fabric.common.db.Status;
import fabric.common.utils.BeanUtils;
import fabric.common.utils.AppUtils;
import fabric.common.web.ErrorCode;
import fabric.common.web.ErrorVO;
import fabric.server.entity.Account;
import fabric.server.entity.DataFile;
import fabric.server.entity.FileType;
import fabric.server.entity.FlowerType;
import fabric.server.entity.FlowerTypeTag;
import fabric.server.entity.TagTypeEnum;

public class FlowerTypeVO extends MapVO {

    /**
     * 花型ID
     */
    private Long flowerTypeID;

    /**
     * 花型名称
     */
    private String flowerTypeName;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 封面图片URL
     */
    private String coverUrl;

    /**
     * 封面图片Md5
     */
    private String coverMd5Code;

    /**
     * 打印图URL
     */
    private String printUrl;

    /**
     * 打印图Md5
     */
    private String printMd5Code;

    /**
     * XML URL
     */
    private String xmlUrl;

    /**
     * XML Md5
     */
    private String xmlMd5Code;

    /**
     * 版本号
     */
    private int version;

    /**
     * 创建者
     */
    private String ownerName;

    /**
     * 禁用/启用
     */
    private boolean enable;

    /**
     * 花型风格
     */
    private List<Long> styleIDs;

    /**
     * 推荐
     */
    private List<Long> recommendIDs;

    /**
     * 颜色
     */
    private List<Long> colorIDs;

    /**
     * 元素
     */
    private List<Long> elementIDs;

    /**
     * 产品
     */
    private List<Long> productIDs;

    /**
     * 被应用次数
     */
    private Long refTimes;

    /**
     * 花型标签
     */
    private Set<FlowerTypeTag> tags;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String modifyTime;

    /**
     * @param flowerType
     */
    public FlowerTypeVO(FlowerType flowerType) {
        this.flowerTypeID = flowerType.getId();
        this.flowerTypeName = flowerType.getName();
        this.description = flowerType.getDescription();
        if (flowerType.getCoverImage() != null) {
            this.coverUrl = AppUtils.fetchFlowerTypePath(flowerType.getId(),
                flowerType.getCoverImage().getName());
            this.coverMd5Code = flowerType.getCoverImage().getMd5Code();
        }

       // if (flowerType.getPrintImage() != null) {
       //     this.printUrl = AppUtils.fetchFlowerTypePath(flowerType.getId(),
       //         flowerType.getPrintImage().getName());
       //     this.printMd5Code = flowerType.getPrintImage().getMd5Code();

        //} else {
            this.printMd5Code = this.printUrl = null;
        //}
        if (flowerType.getXmlFile() != null) {
            this.xmlUrl = AppUtils.fetchFlowerTypePath(flowerType.getId(),
                flowerType.getXmlFile().getName());
            this.xmlMd5Code = flowerType.getXmlFile().getMd5Code();
        }
        this.ownerName = flowerType.getOwner().getName();
        this.version = flowerType.getVersion();
        if (flowerType.getStatus() == Status.Normal) {
            this.enable = true;
        } else {
            this.enable = false;
        }

        this.tags = flowerType.getTags();
        styleIDs = new ArrayList();
        colorIDs = new ArrayList();
        elementIDs = new ArrayList();
        recommendIDs = new ArrayList();
        productIDs = new ArrayList();
        
        Long startTime = System.currentTimeMillis();
        listByTagTypename(styleIDs,recommendIDs, colorIDs, elementIDs, productIDs);
        
        //listByTagTypeName(styleIDs, TagTypeEnum.STYLE);
        //listByTagTypeName(colorIDs, TagTypeEnum.COLOR);
        //listByTagTypeName(elementIDs, TagTypeEnum.ElEMENTS);
        //listByTagTypeName(recommendIDs, TagTypeEnum.RECOMMEND);
        //listByTagTypeName(productIDs, TagTypeEnum.PRODUCT);
        Long endTime = System.currentTimeMillis();
        //System.out.println("CostTags: " + String.valueOf(endTime-startTime) + "ms.");
        this.refTimes = flowerType.getReferences();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        this.createTime = sdf.format(flowerType.getCreateDate());
        this.modifyTime = sdf.format(flowerType.getModifyTime());
    }

    /**
     * @return the coverMd5Code
     */
    public String getCoverMd5Code() {
        return coverMd5Code;
    }

    /**
     * @return the coverUrl
     */
    public String getCoverUrl() {
        return coverUrl;
    }

    /**
     * @return
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the flowerTypeID
     */
    public Long getFlowerTypeID() {
        return flowerTypeID;
    }

    /**
     * @return the flowerTypeName
     */
    public String getFlowerTypeName() {
        return flowerTypeName;
    }

    /**
     * @return
     */
    public String getModifyTime() {
        return modifyTime;
    }

    /**
     * @return the ownerName
     */
    public String getOwnerName() {
        return ownerName;
    }

    /**
     * @return the printMd5Code
     */
    public String getPrintMd5Code() {
        return printMd5Code;
    }

    /**
     * @return the printUrl
     */
    public String getPrintUrl() {
        return printUrl;
    }

    /**
     * @return the version
     */
    public int getVersion() {
        return version;
    }

    /**
     * @return the xMLMd5Code
     */
    public String getXMLMd5Code() {
        return xmlMd5Code;
    }

    /**
     * @return the xMLUrl
     */
    public String getXMLUrl() {
        return xmlUrl;
    }

    /**
     * @return
     */
    public boolean isEnable() {
        return enable;
    }

    /**
     * 根据花型标签类型名提取标签,FlowerTypeTag
     * 
     * @param list
     * @param tagType
     */
    private void listByTagTypeName(List<Long> list, TagTypeEnum tagType) {
        String tagTypeName = tagType.getName();
        for (FlowerTypeTag item: tags) {
            if (item.getTagType().getName().equals(tagTypeName)) {
                list.add(item.getId());
            }
        }
    }

    private void listByTagTypename(List<Long> styleIDs,
        List<Long> recommendIDs, List<Long> colorIDs, List<Long> elementIDs,
        List<Long> productIDs) {
        for (FlowerTypeTag item: tags) {
            if (item.getTagType().getName().equals(TagTypeEnum.STYLE.getName())) {
                styleIDs.add(item.getId());
            } else if (item.getTagType().getName().equals(TagTypeEnum.COLOR.getName())) {
                colorIDs.add(item.getId());
            } else if (item.getTagType().getName().equals(TagTypeEnum.ElEMENTS.getName())) {
                elementIDs.add(item.getId());
            } else if (item.getTagType().getName().equals(TagTypeEnum.RECOMMEND.getName())) {
                recommendIDs.add(item.getId());
            } else if (item.getTagType().getName().equals(TagTypeEnum.PRODUCT.getName())) {
                productIDs.add(item.getId());
            }
        }
    }

    /**
     * 根据花型标签类型读取标签
     * 
     * @param tagType
     * @return
     */
    public List<Long> listTag(TagTypeEnum tagType) {
        if (tagType == null) {
            return null;
        }
        if (tagType == TagTypeEnum.COLOR) {
            return colorIDs;
        } else if (tagType == TagTypeEnum.STYLE) {
            return styleIDs;
        } else if (tagType == TagTypeEnum.ElEMENTS) {
            return elementIDs;
        } else if (tagType == TagTypeEnum.RECOMMEND) {
            return recommendIDs;
        } else if (tagType == TagTypeEnum.PRODUCT) {
            return productIDs;
        } else {
            return null;
        }
    }

    @Override
    public Representation representation() {
        JSONObject jObj = new JSONObject(this);
        try {
            jObj.accumulate("styleIDs", listTag(TagTypeEnum.STYLE));
            jObj.accumulate("colorIDs", listTag(TagTypeEnum.COLOR));
            jObj.accumulate("elementIDs", listTag(TagTypeEnum.ElEMENTS));
            jObj.accumulate("recommendIDs", listTag(TagTypeEnum.RECOMMEND));
            jObj.accumulate("productIDs", listTag(TagTypeEnum.PRODUCT));
        } catch (JSONException e) {
            logger.info("添加花型标签列表出错");
            e.printStackTrace();
        }
        return new JsonRepresentation(jObj);
    }

    /**
     * @param coverMd5Code
     *            the coverMd5Code to set
     */
    public void setCoverMd5Code(String coverMd5Code) {
        this.coverMd5Code = coverMd5Code;
    }

    /**
     * @param coverUrl
     *            the coverUrl to set
     */
    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param enable
     */
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    /**
     * @param flowerTypeID
     *            the flowerTypeID to set
     */
    public void setFlowerTypeID(Long flowerTypeID) {
        this.flowerTypeID = flowerTypeID;
    }

    /**
     * @param flowerTypeName
     *            the flowerTypeName to set
     */
    public void setFlowerTypeName(String flowerTypeName) {
        this.flowerTypeName = flowerTypeName;
    }

    /**
     * @param modifyTime
     */
    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * @param ownerName
     *            the ownerName to set
     */
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    /**
     * @param printMd5Code
     *            the printMd5Code to set
     */
    public void setPrintMd5Code(String printMd5Code) {
        this.printMd5Code = printMd5Code;
    }

    /**
     * @param printUrl
     *            the printUrl to set
     */
    public void setPrintUrl(String printUrl) {
        this.printUrl = printUrl;
    }

    /**
     * @param version
     *            the version to set
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * @param xMLMd5Code
     *            the xMLMd5Code to set
     */
    public void setXMLMd5Code(String xMLMd5Code) {
        this.xmlMd5Code = xMLMd5Code;
    }

    /**
     * @param xMLUrl
     *            the xMLUrl to set
     */
    public void setXMLUrl(String xMLUrl) {
        this.xmlUrl = xMLUrl;
    }

    /**
     * @return the refTimes
     */
    public Long getRefTimes() {
        return refTimes;
    }

    /**
     * @param refTimes
     *            the refTimes to set
     */
    public void setRefTimes(Long refTimes) {
        this.refTimes = refTimes;
    }

}
