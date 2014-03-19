/**
 * @(#)DataFileManagerImpl.java, 2013-7-1. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.server.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fabric.common.db.LogicManagerImpl;
import fabric.common.db.ManagerException;
import fabric.server.dao.DataFileDao;
import fabric.server.entity.DataFile;
import fabric.server.manager.DataFileManager;

/**
 * @author nisonghai
 */
@Service
public class DataFileManagerImpl extends
    LogicManagerImpl<DataFile, DataFileDao> implements DataFileManager {

    @Autowired
    private DataFileDao dataFileDao;

    @Override
    public void saveOrUpdate(DataFile dateFile) {
        if (dateFile.getId() != null) {
            throw new ManagerException("Date file do not update.");
        } else {
            save(dateFile);
        }
    }

}
