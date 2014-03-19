/**
 * @(#)DataFileDaoImpl.java, 2013-7-1. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.server.dao.impl;

import org.springframework.stereotype.Repository;

import fabric.common.db.LogicDaoImpl;
import fabric.server.dao.DataFileDao;
import fabric.server.entity.DataFile;

/**
 * @author nisonghai
 */
@Repository
public class DataFileDaoImpl extends LogicDaoImpl<DataFile> implements
    DataFileDao {

}
