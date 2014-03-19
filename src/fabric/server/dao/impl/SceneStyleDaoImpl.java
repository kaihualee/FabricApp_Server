package fabric.server.dao.impl;

import org.springframework.stereotype.Repository;

import fabric.common.db.StyleDaoImpl;
import fabric.server.dao.SceneStyleDao;
import fabric.server.entity.SceneStyle;

@Repository
public class SceneStyleDaoImpl extends StyleDaoImpl<SceneStyle> implements
    SceneStyleDao {

}
