/**
 * @(#)VersionVO.java, 2013-7-2. 
 * 
 */
package fabric.server.vo;

import fabric.common.db.BusinessEntityImpl;
import fabric.common.db.StyleEntityImpl;
import fabric.common.web.BaseVO;
import fabric.server.entity.DataFile;
import fabric.server.entity.FlowerType;
import fabric.server.entity.Order;
import fabric.server.entity.Scene;
import fabric.server.entity.Scheme;

/**
 * @author likaihua
 */
public class VersionVO extends BaseVO {
    /**
     * id
     */
    private Long id;

    /**
     * 版本号
     */
    private int version;

    /**
     * 封面图
     */
    private String coverMd5Code;

    /**
     * xml
     */
    private String XMLMd5Code;

    /**
     * 打印图
     */
    private String printMd5Code;

    public VersionVO(BusinessEntityImpl entity) {
        this.id = entity.getId();
        this.version = entity.getVersion();

        if (entity instanceof FlowerType) {
            FlowerType flowerType = (FlowerType) entity;
            this.coverMd5Code = flowerType.getCoverImage().getMd5Code();
            this.XMLMd5Code = flowerType.getXmlFile().getMd5Code();
            DataFile printImage = flowerType.getPrintImage();
            if (printImage != null) {
                this.printMd5Code = flowerType.getPrintImage().getMd5Code();
            }
        } else if (entity instanceof Scheme) {
            Scheme scheme = (Scheme) entity;
            this.coverMd5Code = scheme.getCoverImage().getMd5Code();
            this.XMLMd5Code = scheme.getXmlFile().getMd5Code();
        } else if (entity instanceof Scene) {
            Scene scene = (Scene) entity;
            this.coverMd5Code = scene.getCoverImage().getMd5Code();
            this.XMLMd5Code = scene.getXmlFile().getMd5Code();

        } else if (entity instanceof Order) {
            Order order = (Order) entity;
            this.coverMd5Code = order.getSketch().getMd5Code();
        }

    }

    /**
     * @param entity
     */
    public VersionVO(StyleEntityImpl entity) {
        this.id = entity.getId();
        this.version = entity.getVersion();
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
    public Long getId() {
        return id;
    }

    /**
     * @return
     */
    public String getPrintMd5Code() {
        return printMd5Code;
    }

    /**
     * @return
     */
    public int getVersion() {
        return version;
    }

    /**
     * @return
     */
    public String getXMLMd5Code() {
        return XMLMd5Code;
    }

    /**
     * @param coverMd5Code
     */
    public void setCoverMd5Code(String coverMd5Code) {
        this.coverMd5Code = coverMd5Code;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @param printMd5Code
     */
    public void setPrintMd5Code(String printMd5Code) {
        this.printMd5Code = printMd5Code;
    }

    /**
     * @param version
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * @param xMLMd5Code
     */
    public void setXMLMd5Code(String xMLMd5Code) {
        XMLMd5Code = xMLMd5Code;
    }
}
