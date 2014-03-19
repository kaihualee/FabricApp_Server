/**
 * @(#)StyleDao.java, 2013-6-29. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.common.db;

/**
 *
 * @author nisonghai
 *
 */
public interface StyleDao<T extends StyleEntityImpl> extends LogicDao<T> {
    
    /**
     * 根据名称查找对象
     * 默认不能重名
     * @param name
     * @return
     */
    public T getByName(String name);

}
