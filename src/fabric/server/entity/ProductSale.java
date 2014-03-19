/**
 * @(#)ProductSale.java, 2013-7-23. 
 * 
 */
package fabric.server.entity;

import java.util.Date;

import fabric.common.db.BaseEntityImpl;
import fabric.common.db.BusinessEntityImpl;

/**
 * 统计热销产品(未使用)
 * @author likaihua
 *
 */
public class ProductSale extends BaseEntityImpl {
    /**
     * 商品类型
     */
    private ProductType type;
    
    /**
     * 产品ID
     */
    private Long productID;
    
    /**
     * 统计开始时间
     */
    private Date startDate;
    
    /**
     * 统计结束时间
     */
    private Date endDate;
    
    /**
     * 产品的销量
     */
    private Long sales;
    
    
    /**
     * 下单最多的商家
     */
    private Shop hotShop;


    public ProductType getType() {
        return type;
    }


    public void setType(ProductType type) {
        this.type = type;
    }


    public Long getProductID() {
        return productID;
    }


    public void setProductID(Long productID) {
        this.productID = productID;
    }


    public Date getStartDate() {
        return startDate;
    }


    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    public Date getEndDate() {
        return endDate;
    }


    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    public Long getSalses() {
        return sales;
    }


    public void setSalses(Long salses) {
        this.sales = salses;
    }


    public Shop getHotShop() {
        return hotShop;
    }


    public void setHotShop(Shop hotShop) {
        this.hotShop = hotShop;
    }
    
    
    
}
