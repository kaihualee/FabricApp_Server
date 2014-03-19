package fabric.server.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fabric.common.db.StyleManagerImpl;
import fabric.server.dao.SceneStyleDao;
import fabric.server.entity.SceneStyle;
import fabric.server.manager.SceneStyleManager;

@Service
public class SceneStyleManagerImpl extends
    StyleManagerImpl<SceneStyle, SceneStyleDao> implements SceneStyleManager {

    @Autowired
    private SceneStyleDao sceneStyleDao;

}
