package fabric.server.dao.impl;

import org.springframework.stereotype.Repository;

import fabric.common.db.BusinessDaoImpl;
import fabric.server.dao.SceneDao;
import fabric.server.entity.Scene;

@Repository
public class SceneDaoImpl extends BusinessDaoImpl<Scene> implements SceneDao {

}
