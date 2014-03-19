/**
 * @(#)ShopVO.java, 2013-7-12. 
 * 
 */
package fabric.server.vo;

import fabric.common.web.BaseVO;
import fabric.server.entity.Shop;

/**
 *
 * @author likaihua
 *
 */
public class ShopVO extends BaseVO {

    
    /**
     * 店名
     */
    private String shopName;

    
    
    /**
     * @param shop
     */
    public ShopVO(Shop shop){
        this.shopName = shop.getName();
    }
    
    /**
     * @return
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * @param shopName
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
