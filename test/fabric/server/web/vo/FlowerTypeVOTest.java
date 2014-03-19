/**
 * @(#)FlowerTypeVOTest.java, 2013-7-19. 
 * 
 */
package fabric.server.web.vo;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.restlet.representation.Representation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fabric.server.entity.FlowerType;
import fabric.server.manager.FlowerTypeManager;
import fabric.server.vo.FlowerTypeVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/*.xml" })
/**
 *
 * @author likaihua
 *
 */
public class FlowerTypeVOTest {

    @Autowired
    private FlowerTypeManager ftManager;
    
    @Test
    public void testRepresentation() throws IOException{
        Long id = 1L;
        FlowerType flowerType = ftManager.get(id);
        FlowerTypeVO flowerTypeVO = new FlowerTypeVO(flowerType);
        Representation rep = flowerTypeVO.representation();
        System.out.println(rep.getText());
    }
}
