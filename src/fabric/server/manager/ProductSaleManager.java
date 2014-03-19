/**
 * @(#)ProductSaleManager.java, 2013-7-23. 
 * 
 */
package fabric.server.manager;

import java.util.Date;

import org.springframework.stereotype.Service;

import fabric.common.db.BaseManager;
import fabric.server.entity.ProductSale;

/**
 * @author likaihua
 */
@Service
public interface ProductSaleManager extends BaseManager<ProductSale> {

    /**
     * 统计一个星期的销量
     */
    public void stat(Date date);
}
