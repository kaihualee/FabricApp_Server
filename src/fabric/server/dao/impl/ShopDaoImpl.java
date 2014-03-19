/**
 * @(#)ShopDaoImpl.java, 2013-6-29. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.server.dao.impl;

import org.springframework.stereotype.Repository;

import fabric.common.db.StyleDaoImpl;
import fabric.server.dao.ShopDao;
import fabric.server.entity.Shop;

/**
 * @author nisonghai
 */
@Repository
public class ShopDaoImpl extends StyleDaoImpl<Shop> implements ShopDao {

}
