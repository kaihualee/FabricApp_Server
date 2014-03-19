/**
 * @(#)FlowerTypeTagTypeManagerImpl.java, 2013-7-17. 
 * 
 */
package fabric.server.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fabric.common.db.BaseManagerImpl;
import fabric.server.dao.FlowerTypeTagTypeDao;
import fabric.server.entity.FlowerTypeTagType;
import fabric.server.manager.FlowerTypeTagTypeManager;

/**
 * @author likaihua
 */
@Service
public class FlowerTypeTagTypeManagerImpl extends
    BaseManagerImpl<FlowerTypeTagType, FlowerTypeTagTypeDao> implements
    FlowerTypeTagTypeManager {

    @Autowired
    private FlowerTypeTagTypeDao ftTagTypeDao;

    @Override
    public FlowerTypeTagType getByName(String name) {
        return ftTagTypeDao.getByName(name);
    }

}
