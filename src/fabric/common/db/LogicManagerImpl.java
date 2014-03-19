/**
 * @(#)LogicManagerImpl.java, 2013-6-29. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.common.db;

import java.util.List;

/**
 * @author nisonghai
 */
public class LogicManagerImpl<T extends LogicEntityImpl, D extends LogicDao<T>>
    extends BaseManagerImpl<T, D> implements LogicManager<T> {

    @Override
    public void delete(T entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(Long id) {
        T entity = get(id);
        entity.setStatus(Status.Deleted);
        update(entity);
    }

    @Override
    public List<T> getAllByStatus(Status... status) {
        return dao.getAllByStatus(status);
    }

}
