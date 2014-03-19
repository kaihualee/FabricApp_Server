/**
 * @(#)BusinessManagerImpl.java, 2013-6-29. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.common.db;

import java.util.List;

import fabric.common.utils.PageBean;
import fabric.server.entity.Account;
import fabric.server.entity.UserRule;

/**
 * @author nisonghai
 */
public class BusinessManagerImpl<T extends BusinessEntityImpl, D extends BusinessDao<T>>
    extends LogicManagerImpl<T, D> implements BusinessManager<T> {

    @Override
    public boolean checkByName(String name) {
        return dao.checkName(name);
    }

    @Override
    public boolean checkByName(String name, Long id) {
        return dao.checkName(name, id);
    }

    @Override
    public T get(Long id) {
        return dao.get(id);
    }

    @Override
    public List<T> getAll() {
        return dao.getAll();
    }

    @Override
    public List<T> getAll(Account account) {
        if (account.getRule() == UserRule.SUPER_ADMIN) {
            return dao.getAll();
        }
        return dao.getAllByOwnerId(account.getId());
    }

    @Override
    public T getById(Account account, Long id) {
        T entity;
        switch (account.getRule()) {
            case SUPER_ADMIN:
            case DESIGNER:
                entity = dao.getById(id);
                break;
            case BUSINESS:
            case GENERAL:
            case GUEST:
                entity = dao.getNormalById(id);
                break;
            default:
                entity = null;
        }
        if (entity == null)
            return null;
        if (entity.getOwner().getId() == account.getId())
            return entity;

        return null;
    }

    @Override
    public T getByIdUseOwner(Account account, Long id) {
        if (account.getRule() == UserRule.SUPER_ADMIN) {
            return dao.getById(id);
        }
        return dao.getByIdUseOwner(account.getId(), id);
    }

    @Override
    public T getByName(Long ownerId, String name) {
        return dao.getByName(ownerId, name);
    }

    @Override
    public void update(Account account, T entity) {
        //only owner can modify or admin
        if (account.getRule() == UserRule.SUPER_ADMIN) {
            dao.update(entity);
        }
        if (entity.getOwner().getId() == account.getId())
            dao.update(entity);
    }

}
