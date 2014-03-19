/**
 * @(#)BaseManager.java, 2012-10-8. Copyright 2012 Netease, Inc. All rights
 *                       reserved. NETEASE PROPRIETARY/CONFIDENTIAL. Use is
 *                       subject to license terms.
 */
package fabric.common.db;

import java.util.List;

import fabric.common.utils.PageBean;

/**
 * @author nisonghai
 */
public interface BaseManager<T extends BaseEntity> {

    public T get(Long id);
    
    public List<T> getAll();

    public List<T> getAll(PageBean pageBean);

    public void remove(T entity);

    public int removeAll();
    
    public void removeById(Long id);

    public void save(T entity);

    public void saveList(List<T> list);

    public void saveOrUpdate(T entity);

    public void update(T entity);

}
