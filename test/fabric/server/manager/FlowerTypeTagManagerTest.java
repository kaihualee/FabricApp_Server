/**
 * @(#)FlowerTypeTagManagerTest.java, 2013-7-17. 
 * 
 */
package fabric.server.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.restlet.ext.json.JsonRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fabric.server.entity.FlowerTypeTag;
import fabric.server.entity.FlowerTypeTagType;
import fabric.server.entity.TagTypeEnum;

/**
 * @author likaihua
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/*.xml" })
public class FlowerTypeTagManagerTest {

    @Autowired
    private FlowerTypeTagManager ftTagManager;

    @Autowired
    private FlowerTypeTagTypeManager ftTagTypeManager;

    @Test
    public void testSave() {
        FlowerTypeTag tag = new FlowerTypeTag();
        FlowerTypeTagType tagType = ftTagTypeManager.getByName(TagTypeEnum.STYLE
            .getName());
        tag.setName("復古");
        tag.setTagType(tagType);
        ftTagManager.save(tag);
    }

}
