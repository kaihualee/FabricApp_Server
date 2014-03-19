/**
 * @(#)FlowerTypeTagTypeManagerTest.java, 2013-7-17. 
 * 
 */
package fabric.server.manager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fabric.server.entity.FlowerTypeTagType;
import fabric.server.entity.TagTypeEnum;

/**
 * @author likaihua
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/*.xml" })
public class FlowerTypeTagTypeManagerTest {
    @Autowired
    private FlowerTypeTagTypeManager ftTagTypeManager;

    @Before
    public void setUp() throws Exception {}

    @Test
    public void testGetAll() {}

    @Test
    public void testSave() {
        FlowerTypeTagType ftTagType = ftTagTypeManager.getByName("风格");
        System.out.println(ftTagType.getName());
        //ftTagTypeManager.save(new FlowerTypeTagType(TagTypeEnum.STYLE));
        //ftTagTypeManager.save(new FlowerTypeTagType(TagTypeEnum.ElEMENTS));
        //ftTagTypeManager.save(new FlowerTypeTagType(TagTypeEnum.ELSE));
        //ftTagTypeManager.save(new FlowerTypeTagType(TagTypeEnum.COLOR));
        //ftTagTypeManager.save(new FlowerTypeTagType(TagTypeEnum.RECOMMEND));
    }
}
