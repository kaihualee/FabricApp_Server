/**
 * @(#)BusinessManager.java, 2013-6-29. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.common.db;

import java.util.List;

import fabric.server.entity.Account;

/**
 * @author nisonghai
 */
public interface BusinessManager<T extends BusinessEntityImpl> extends
    LogicManager<T> {

    /**
     * 检查名称是是否重复(true 重复 false 没重复)
     * @param name
     * @return
     */
    public boolean checkByName(String name);

    /**
     * 检查名称是是否重复(true 重复 false 没重复)，排除自身ID
     * 
     * @param name
     * @param id
     * @return
     */
    public boolean checkByName(String name, Long id);

    /**
     * 获取角色所有可以读取的数据
     * @param account
     * @return
     */
    public List<T> getAll(Account account);
    
    
    
    /**
     * 根据ID读取角色可读的数据
     * 
     * @param account
     * @param id
     * @return
     */
    public T getById(Account account, Long id);


    /**
     * 根据ID读取角色可编辑的数据
     * @param account
     * @param id
     * @return
     */
    public T getByIdUseOwner(Account account, Long id);

    /**
     * @param ownerId
     * @param name
     * @return
     */
    public T getByName(Long ownerId, String name);
    
    /**
     * 所有者才能修改数据
     * @param account
     * @param entity
     * @return
     */
    public void update(Account account, T entity);
}
