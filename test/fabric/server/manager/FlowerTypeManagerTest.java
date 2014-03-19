package fabric.server.manager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import fabric.server.entity.Account;
import fabric.server.entity.DataFile;
import fabric.server.entity.FileType;
import fabric.server.entity.FlowerType;
import fabric.server.entity.FlowerTypeTag;
import fabric.server.entity.UserRule;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/*.xml" })
public class FlowerTypeManagerTest {

    @Autowired
    private FlowerTypeManager ftManager;

    @Autowired
    private AccountManager accountManager;

    private Account admin;

    @Autowired
    private FlowerTypeTagManager ftTagManager;

    @Autowired
    private FlowerTypeTagTypeManager ftTagTypeManager;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        admin = accountManager.getAccountByName(UserRule.DESIGNER, "designer",
            null);
    }

    @Test
    public void testDeleteAll() {
        ftManager.removeAll();
    }

    @Test
    public void testSave() {
        String[] configs = { "classpath*:spring/*.xml" };
        ApplicationContext ctx = new FileSystemXmlApplicationContext(configs);
        SessionFactory sessionFactory = ctx.getBean("sessionFactory", SessionFactory.class);
        Session session = SessionFactoryUtils.getSession(sessionFactory, true);
        TransactionSynchronizationManager.bindResource(sessionFactory,
            new SessionHolder(session));
        int nCount = 1000*100;
        FlowerTypeTag tag1 = ftTagManager.getByName("欧式");
        FlowerTypeTag tag2 = ftTagManager.getByName("抽象");
        FlowerTypeTag tag3 = ftTagManager.getByName("黄#-256");
        FlowerTypeTag tag4 = ftTagManager.getByName("流行");
        FlowerTypeTag tag5 = ftTagManager.getByName("靠枕");
        for (int nLoop = 0; nLoop < nCount; ++nLoop) {
            String name = UUID.randomUUID().toString();
            String description = "简约简约简约简约简约11111";
            String coverImagePath = "Demo简约.jpg";
            String printImagePath = "Demo简约a.jpg";
            String xmlPath = "Demo简约.xml";
            Set<FlowerTypeTag> tags = new HashSet();

            
            tags.add(tag1);
            tags.add(tag2);
            tags.add(tag3);
            tags.add(tag4);
            tags.add(tag5);
            
            FlowerType ft = new FlowerType();
            ft.setName(name);
            ft.setDescription(description);
            ft.setTags(tags);
            ft.setCoverImage(new DataFile(coverImagePath, "aaaaa1111",
                FileType.Cover_Image));
            ft.setPrintImage(new DataFile(printImagePath, "ddddd1111",
                FileType.Print_Image));
            ft.setXmlFile(new DataFile(xmlPath, "aaaaa11111", FileType.Xml));
            ft.setOwner(admin);

            ftManager.saveOrUpdate(ft);
        }
        TransactionSynchronizationManager.unbindResource(sessionFactory);
    }

    @Test
    public void testGetFlowerTypeByName() {

    }

    @Test
    public void testUpdateFlowerType() {

    }

    @Test
    public void testUpdaeFlowerTypeStyle() {}

    @Test
    public void testGetAll() {
        List<FlowerType> list = ftManager.getAll();
        for (FlowerType ft: list) {
            System.out.println(ft); // line 1   
        }
    }
}
