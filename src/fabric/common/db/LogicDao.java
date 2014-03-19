/**
 * @(#)LogicDao.java, 2013-6-29. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.common.db;

import java.util.List;

/**
 * LoginEntityImpl 对应的DAO接口
 * 
 * @author nisonghai
 */
public interface LogicDao<T extends LogicEntityImpl> extends BaseDao<T> {
    
    /**
     * 删除某个实体
     * 默认 逻辑删除
     */
    public void deleteById(Long id);
    
    /**
     * 获取所有列表
     * 默认Normal,Disable
     */
    public List<T> getAll();
    
    /**
     * 根据状态集查询列表
     * 
     * @param status
     * @return
     */
    public List<T> getAllByStatus(Status... status);
    
    /**
     * 根据ID查找实体
     * 默认Normal, Disable
     * @param id
     * @return
     */
    public T getById(Long id);
    

    /**
     * 根据id,状态集查询实体
     * @param status
     * @return
     */
    public T getByStatusAndId(Long id, Status...status);

 
    /**
     * 根据ID查找实体
     * 包含Normal
     * @param id
     * @return
     */
    public T getNormalById(Long id);
    
    /**
     * 根据批量ID,状态集获取实体
     * @param ids
     * @param status
     * @return
     */
    public List<T> getListByIdAndStatus(List<Long> ids, Status...status);
    
    
    
}
