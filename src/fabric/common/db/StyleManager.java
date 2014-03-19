/**
 * @(#)StyleManager.java, 2013-6-29. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.common.db;

/**
 * @author nisonghai
 */
public interface StyleManager<T extends StyleEntityImpl> extends
    LogicManager<T> {
    
    /**
     * 根据name获取对象
     * 
     * @param name
     * @return
     */
    public T getByName(String name);
    
    /**
     * 根据name保存对象，如果该对象已存在直接返回
     * 
     * @param entity
     */
    public T saveByName(T entity);

}
