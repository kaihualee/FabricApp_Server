package fabric.server.dao.impl;

import org.springframework.stereotype.Repository;

import fabric.common.db.StyleDaoImpl;
import fabric.server.dao.ScenePositionDao;
import fabric.server.entity.ScenePosition;

@Repository
public class ScenePositionDaoImpl extends StyleDaoImpl<ScenePosition> implements
    ScenePositionDao {

}
