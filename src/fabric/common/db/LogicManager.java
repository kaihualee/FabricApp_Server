/**
 * @(#)LogicManager.java, 2013-6-29. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.common.db;

import java.util.List;

/**
 * @author nisonghai
 */
public interface LogicManager<T extends LogicEntityImpl> extends BaseManager<T> {

    /**
     * 将状态置为Deleted
     * 
     * @param entity
     */
    public void delete(T entity);
    
    /**
     * 将状态置为Deleted
     * 
     * @param id
     */
    public void deleteById(Long id);
    
    /**
     * 按状态查询列表
     * 
     * @param status
     * @return
     */
    public List<T> getAllByStatus(Status... status);

}
