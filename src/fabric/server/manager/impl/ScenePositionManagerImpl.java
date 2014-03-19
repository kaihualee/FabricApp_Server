package fabric.server.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fabric.common.db.StyleManagerImpl;
import fabric.server.dao.ScenePositionDao;
import fabric.server.entity.ScenePosition;
import fabric.server.manager.ScenePositionManager;

@Service
public class ScenePositionManagerImpl extends
    StyleManagerImpl<ScenePosition, ScenePositionDao> implements
    ScenePositionManager {

    @Autowired
    private ScenePositionDao scenePosDao;

}
