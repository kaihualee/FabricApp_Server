/**
 * @(#)BaseManagerImpl.java, 2012-10-8. Copyright 2012 Netease, Inc. All rights
 *                           reserved. NETEASE PROPRIETARY/CONFIDENTIAL. Use is
 *                           subject to license terms.
 */
package fabric.common.db;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import fabric.common.utils.BeanUtils;
import fabric.common.utils.GenericsUtils;
import fabric.common.utils.PageBean;

/**
 * @author nisonghai
 */
@SuppressWarnings("unchecked")
public abstract class BaseManagerImpl<T extends BaseEntity, D extends BaseDao<T>>
    implements BaseManager<T> {

    private static int BATCH_SAVE_LIMIT = 200;

    protected D dao;

    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        List<Field> fields = BeanUtils.getFieldsByType(this,
            GenericsUtils.getSuperClassGenricType(getClass(), 1));
        Assert.isTrue(fields.size() == 1,
            "subclass's has not only one dao property.");
        try {
            dao = (D) BeanUtils.forceGetProperty(this, fields.get(0).getName());
            Assert
                .notNull(dao, "subclass not inject dao to manager sucessful.");
        } catch (Exception e) {
            ReflectionUtils.handleReflectionException(e);
        }
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
    public List<T> getAll(PageBean pageBean) {
        return dao.getAll(pageBean);
    }

    @Override
    public void remove(T entity) {
        dao.remove(entity);
    }

    @Override
    public int removeAll() {
        return dao.removeAll();
    }

    @Override
    public void removeById(Long id) {
        dao.removeById(id);
    }

    @Override
    public void save(T entity) {
        dao.save(entity);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void saveList(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }

        if (list.size() <= BATCH_SAVE_LIMIT) {
            saveListForTransaction(list, 0, BATCH_SAVE_LIMIT);
        } else {
            int offset = 0;
            while (saveListForTransaction(list, offset, BATCH_SAVE_LIMIT) >= BATCH_SAVE_LIMIT) {
                offset += BATCH_SAVE_LIMIT;
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int saveListForTransaction(List<T> list, int offset, int length) {
        int total = 0;
        for (int i = offset; i < (offset + length) && i < list.size(); i++, total++) {
            if (list.get(i).getId() == null) {
                save(list.get(i));
            } else {
                T entity = get(list.get(i).getId());
                if (entity == null) {
                    save(list.get(i));
                } else {
                    list.get(i).setCreateDate(entity.getCreateDate());
                    update(list.get(i));
                }
            }
        }

        return total;
    }

    @Override
    public void saveOrUpdate(T entity) {
        if (entity.getId() == null) {
            save(entity);
        } else {
            update(entity);
        }
    }

    protected void settingDate(BaseEntity entity, Date now) {
        if (entity.getCreateDate() == null) {
            entity.setCreateDate(now);
        }
        if (entity.getModifyTime() == null) {
            entity.setModifyTime(now);
        }
    }

    @Override
    public void update(T entity) {
        entity.setModifyTime(new Date());
        dao.update(entity);
    }

}
