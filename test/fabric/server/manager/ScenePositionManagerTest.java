package fabric.server.manager;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fabric.server.entity.ScenePosition;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class ScenePositionManagerTest {

    @Autowired
    private ScenePositionManager scenePosManager;

    @Test
    public void testAdd() {
        String name = "卧室";
        ScenePosition scenePos = new ScenePosition(name);
        scenePosManager.saveByName(scenePos);

        name = "客厅";
        scenePos = new ScenePosition(name);
        scenePosManager.saveByName(scenePos);

        List<ScenePosition> list = scenePosManager.getAll();
        for (ScenePosition pos: list) {
            System.out.println(pos);
        }
    }
}
