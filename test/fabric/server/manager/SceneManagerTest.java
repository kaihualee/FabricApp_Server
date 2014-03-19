/**
 * @(#)SceneManagerTest.java, 2013-7-1. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.server.manager;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fabric.server.entity.Account;
import fabric.server.entity.DataFile;
import fabric.server.entity.FileType;
import fabric.server.entity.Scene;
import fabric.server.entity.ScenePosition;
import fabric.server.entity.SceneStyle;
import fabric.server.entity.UserRule;

/**
 * @author nisonghai
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/*.xml" })
public class SceneManagerTest {

    @Autowired
    private SceneManager sceneManager;

    @Autowired
    private AccountManager accountManager;

    private Account admin;

    @Before
    public void setUp() throws Exception {
        admin = accountManager.getAccountByName(UserRule.SUPER_ADMIN, "admin",
            null);
    }

    @Test
    public void testGetAll() {}

    @Test
    public void testSave() {
        Scene scene = new Scene();
        scene.setName("场景1111");
        scene.setDescription("场景场景场景场景1111");
        scene.setCoverImage(new DataFile("Demo场景.jpg", "md5code",
            FileType.Cover_Image));
        scene.setCab(new DataFile("Demo场景.cab", "md5code", FileType.Cab));
        scene.setXmlFile(new DataFile("Demo场景.xml", "md5code", FileType.Xml));

        scene.setScenePos(new ScenePosition("卧室"));
        scene.setSceneStyle(new SceneStyle("西式"));
        scene.setOwner(admin);

        sceneManager.saveOrUpdate(scene);

        List<Scene> list = sceneManager.getAll();
        for (Scene sceneTmp: list) {
            System.out.println(scene);
        }
    }
    
    @Test
    public void testUpdate(){
        List<Integer> lst = new ArrayList<Integer>();
        int count = 18;
        for(int nLoop = 1; nLoop <= count; ++nLoop){
            lst.add(nLoop);
        }
        Long id = 1L;
        for(Integer item : lst){
            String sceneName =String.format("Demo%d", item);
            String description = "无";
            Scene scene = sceneManager.get(id++);
            scene.setName(sceneName);
            scene.setDescription(description);
            sceneManager.update(scene);
        }
    }

}
