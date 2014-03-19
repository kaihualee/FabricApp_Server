/**
 * @(#)ShopManagerImpl.java, 2013-6-30. Copyright 2013 Netease, Inc. All rights
 *                           reserved. NETEASE PROPRIETARY/CONFIDENTIAL. Use is
 *                           subject to license terms.
 */
package fabric.server.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fabric.common.db.StyleManagerImpl;
import fabric.server.dao.ShopDao;
import fabric.server.entity.Shop;
import fabric.server.manager.ShopManager;

/**
 * @author nisonghai
 */
@Service
public class ShopManagerImpl extends StyleManagerImpl<Shop, ShopDao> implements
    ShopManager {

    @Autowired
    private ShopDao shopDao;

}
