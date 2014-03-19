/**
 * @(#)BusinessDao.java, 2013-6-29. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.common.db;

import java.util.List;

import fabric.common.utils.PageBean;

/**
 * 业务DAO
 * 
 * @author nisonghai
 */
public interface BusinessDao<T extends BusinessEntityImpl> extends LogicDao<T> {

    /**
     * 检查作品名称是否重复.
     * True : 重复
     * 
     * @param name
     * @return
     */
    public boolean checkName(String name);

    /**
     * 检查作品名称是否重复
     * 
     * @param name
     * @param id
     *            排除自身ID
     * @return
     */
    public boolean checkName(String name, Long id);


   
    
    /**
     * 获取所有作品 
     * 默认Normal,Disable
     */
    public List<T> getAll();

    /**
     * 查询該作者的所有作品 
     * 默认包含Normal,Disable
     * 
     * @param ownerId
     * @return
     */
    public List<T> getAllByOwnerId(Long ownerId);

    /**
     * 根据作者Id,Page,状态集合查询
     * @param ownerId
     * @param status
     * @return
     */
    public List<T> getAllByOwnerIdAndStatus(Long ownerId, Status... status);

    /**
     * 根据作者,作品ID查询单个作品 
     * 默认包含Normal,Disable
     * 
     * @param ownerID
     *            , id
     * @return
     */
    public T getByIdUseOwner(Long ownerID, Long id);
    
    
    /**
     * 根据作者Id,Page,状态集合
     * 
     * @param ownerId
     * @param pageBean
     * @param status
     * @return
     */
    public List<T> getAllByOwnerIdAndStatus(Long ownerId, PageBean pageBean, Status...status);
    
    /**
     * 根据作者和作品名称查询单个作品
     * 默认作品名称是唯一
     * @param ownerId
     * @param name
     * @return
     */
    public T getByName(Long ownerId, String name);
}
