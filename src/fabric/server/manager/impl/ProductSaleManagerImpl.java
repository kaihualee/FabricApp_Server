/**
 * @(#)ProductSaleManagerImpl.java, 2013-7-23. 
 * 
 */
package fabric.server.manager.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import fabric.common.db.BaseManagerImpl;
import fabric.server.dao.ProductSaleDao;
import fabric.server.entity.ProductSale;
import fabric.server.manager.OrderManager;
import fabric.server.manager.ProductSaleManager;

/**
 * @author likaihua
 */
public class ProductSaleManagerImpl extends
    BaseManagerImpl<ProductSale, ProductSaleDao> implements ProductSaleManager {

    @Autowired
    private OrderManager orderManager;

    @Override
    public void stat(Date date) {
        //orderManager.get
    }

}
