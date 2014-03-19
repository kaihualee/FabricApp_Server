/**
 * @(#)StyleManagerImpl.java, 2013-6-29. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.common.db;

/**
 * @author nisonghai
 */
public class StyleManagerImpl<T extends StyleEntityImpl, D extends StyleDao<T>>
    extends LogicManagerImpl<T, D> implements StyleManager<T> {

    @Override
    public T getByName(String name) {
        return dao.getByName(name);
    }

    @Override
    public void save(T entity) {
        saveByName(entity);
    }

    @Override
    public T saveByName(T entity) {
        T dbEntity = dao.getByName(entity.getName());
        if (dbEntity != null) {
            dbEntity.setEntity(entity);
            dao.update(dbEntity);
            return dbEntity;
        } else {
            dao.save(entity);
            return entity;
        }
    }

}
