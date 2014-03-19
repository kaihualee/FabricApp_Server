package fabric.server.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fabric.common.db.BusinessManagerImpl;
import fabric.server.dao.SceneDao;
import fabric.server.entity.Account;
import fabric.server.entity.Scene;
import fabric.server.entity.ScenePosition;
import fabric.server.entity.SceneStyle;
import fabric.server.entity.UserRule;
import fabric.server.manager.DataFileManager;
import fabric.server.manager.SceneManager;
import fabric.server.manager.ScenePositionManager;
import fabric.server.manager.SceneStyleManager;

@Service
public class SceneManagerImpl extends BusinessManagerImpl<Scene, SceneDao>
    implements SceneManager {

    @Autowired
    private SceneDao sceneDao;

    @Autowired
    private SceneStyleManager sceneStyleManager;

    @Autowired
    private ScenePositionManager scenePosManager;

    @Autowired
    private DataFileManager dataFileManager;

    @Override
    public void save(Scene scene) {
        saveBefore(scene);

        saveDataFile(scene);

        sceneDao.save(scene);
    }

    @Override
    public void update(Scene scene) {
        saveBefore(scene);

        Scene sceneDb = sceneDao.get(scene.getId());
        if (sceneDb.getCoverImage() != null
            && scene.getCoverImage() != null
            && !sceneDb.getCoverImage().getId()
                .equals(scene.getCoverImage().getId())) {
            dataFileManager.remove(sceneDb.getCoverImage());
        }
        if (sceneDb.getXmlFile() != null && scene.getXmlFile() != null
            && !sceneDb.getXmlFile().getId().equals(scene.getXmlFile().getId())) {
            dataFileManager.remove(sceneDb.getXmlFile());
        }
        if (sceneDb.getCab() != null && scene.getCab() != null
            && !sceneDb.getCab().getId().equals(scene.getCab().getId())) {
            dataFileManager.remove(sceneDb.getCab());
        }

        saveDataFile(scene);

        sceneDb.setEntity(scene);
        sceneDao.update(sceneDb);
        sceneDao.merge(scene);
    }

    /**
     * 保存文件
     * 
     * @param scene
     */
    private void saveDataFile(Scene scene) {
        if (scene.getCoverImage() != null) {
            dataFileManager.saveOrUpdate(scene.getCoverImage());
        }
        if (scene.getXmlFile() != null) {
            dataFileManager.saveOrUpdate(scene.getXmlFile());
        }
        if (scene.getCab() != null) {
            dataFileManager.saveOrUpdate(scene.getCab());
        }
    }

    private void saveBefore(Scene scene) {
        // 场景位置
        ScenePosition scenePos = scene.getScenePos();
        if (scenePos.getId() == null) {
            scenePos = scenePosManager.getByName(scenePos.getName());
            if (scenePos == null) {
                scenePosManager.save(scenePos);
            }
            scene.setScenePos(scenePos);
        }

        // 场景风格
        SceneStyle sceneStyle = scene.getSceneStyle();
        if (sceneStyle.getId() == null) {
            sceneStyle = sceneStyleManager.getByName(sceneStyle.getName());
            if (sceneStyle == null) {
                sceneStyleManager.save(sceneStyle);
            }
            scene.setSceneStyle(sceneStyle);
        }
    }

    @Override
    public List<Scene> getAll(Account account) {
        if (account.getRule() == UserRule.SUPER_ADMIN) {
            return sceneDao.getAll();
        }
        return sceneDao.getAll();
    }

    @Override
    public Scene getById(Account account, Long id) {
        if (account.getRule() == UserRule.SUPER_ADMIN) {
            return sceneDao.getById(id);
        }
        return sceneDao.get(id);
    }

}
