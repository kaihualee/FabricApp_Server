/**
 * @(#)BaseDao.java, 2012-3-13. Copyright 2012 Netease, Inc. All rights reserved.
 *                NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license
 *                terms.
 */
package fabric.common.db;

import java.util.List;

import fabric.common.utils.PageBean;

/**
 * @author nisonghai
 */
public interface BaseDao<T extends BaseEntity> {

    public T get(Long id);

    public List<T> getAll();

    public List<T> getAll(PageBean pageBean);

    public void merge(T entity);

    public void refresh(T entity);

    public void remove(List<T> entityList);

    public void remove(T entity);

    public int removeAll();

    public void removeById(Long id);

    public void save(T entity);

    public void saveOrUpdate(T entity);

    public void update(T entity);

}
