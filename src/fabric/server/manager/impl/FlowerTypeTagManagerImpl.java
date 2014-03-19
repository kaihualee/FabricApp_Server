/**
 * @(#)FlowerTypeTagManagerImpl.java, 2013-7-17. 
 * 
 */
package fabric.server.manager.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fabric.common.db.StyleManagerImpl;
import fabric.server.dao.FlowerTypeTagDao;
import fabric.server.entity.FlowerTypeTag;
import fabric.server.entity.FlowerTypeTagType;
import fabric.server.manager.FlowerTypeTagManager;
import fabric.server.manager.FlowerTypeTagTypeManager;

/**
 * @author likaihua
 */
@Service
public class FlowerTypeTagManagerImpl extends
    StyleManagerImpl<FlowerTypeTag, FlowerTypeTagDao> implements
    FlowerTypeTagManager {

    @Autowired
    private FlowerTypeTagDao ftTagDao;

    @Autowired
    private FlowerTypeTagTypeManager ftTagTypeManager;
    
    @Override
    public List<FlowerTypeTag> getAllByTagTypeName(String name) {
       if(name == null || StringUtils.isEmpty(name)){
           return null;
       }
       FlowerTypeTagType tagType = ftTagTypeManager.getByName(name);
       if (tagType == null)
           return null;
       return ftTagDao.getAllByTagType(tagType);
    }
    @Override
    public FlowerTypeTag getByTagTypeAndTagName(String tagtTypeName, String tagName) {
        if(tagtTypeName == null || StringUtils.isEmpty(tagtTypeName)){
            return null;
        }
        FlowerTypeTagType tagType = ftTagTypeManager.getByName(tagtTypeName);
        if (tagType == null)
            return null;
        return ftTagDao.getByTagTypeAndTagName(tagName, tagType);
    }
    
    @Override
    public FlowerTypeTag getByTagTypeAndTagId(String tagTypeName, Long id){
        return ftTagDao.get(id);
    }

    @Override
    public void save(FlowerTypeTag entity){
        ftTagDao.save(entity);
    }
    
}
