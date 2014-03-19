/**
 * @(#)GrantTable.java, 2013-7-5. 
 * 
 */
package fabric.server.entity;

import java.util.Date;

import fabric.common.db.BusinessEntityImpl;
import fabric.common.db.LogicEntityImpl;

/**
 * @author likaihua
 */
public class GrantTable extends BusinessEntityImpl {

    /**
     * 甲方
     */
    private Account partA;

    
    /**
     * 被授权者的商家(弃用)
     */
    //private Shop partB;

    
    /**
     * 乙方
     */
    private Account partB;
    
    
    /**     
     * 
     * 授权产品类型
     */
    private ProductType type;

    /**
     * 授权产品
     */
    private Long product;

    /**
     * 主要授权，用于主方案
     */
    private boolean major = false;

    /**
     * 授权生效时间
     */
    private Date startDate;

    /**
     * 授权截止时间
     */
    private Date endDate;

    

    /**
     * @return
     */
    public Account getPartA() {
        return partA;
    }

    /**
     * @param partA
     */
    public void setPartA(Account partA) {
        this.partA = partA;
    }

    /**
     * @return
     */
    public ProductType getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(ProductType type) {
        this.type = type;
    }

    /**
     * @return
     */
    public Long getProduct() {
        return product;
    }

    /**
     * @param product
     */
    public void setProduct(Long product) {
        this.product = product;
    }

    /**
     * @return
     */
    public boolean getMajor() {
        return major;
    }

    /**
     * @param major
     */
    public void setMajor(boolean major) {
        this.major = major;
    }

  

    /**
     * @return
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the partB
     */
    public Account getPartB() {
        return partB;
    }

    /**
     * @param partB the partB to set
     */
    public void setPartB(Account partB) {
        this.partB = partB;
    }


}
