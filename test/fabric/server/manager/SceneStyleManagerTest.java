package fabric.server.manager;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fabric.server.entity.SceneStyle;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml" })
public class SceneStyleManagerTest {

    @Autowired
    private SceneStyleManager sceneStyleManager;

    @Test
    public void testAdd() {
        String name = "中式";
        SceneStyle sceneStyle = new SceneStyle(name);
        sceneStyleManager.saveByName(sceneStyle);

        name = "西式";
        sceneStyle = new SceneStyle(name);
        sceneStyleManager.saveByName(sceneStyle);

        name = "现代";
        sceneStyle = new SceneStyle(name);
        sceneStyleManager.saveByName(sceneStyle);

        List<SceneStyle> list = sceneStyleManager.getAll();
        for (SceneStyle style: list) {
            System.out.println(style);
        }
    }
}
