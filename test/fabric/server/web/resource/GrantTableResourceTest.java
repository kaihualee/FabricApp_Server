/**
 * @(#)GrantTableResourceTest.java, 2013-7-8. 
 * 
 */
package fabric.server.web.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.restlet.data.Cookie;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.util.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fabric.common.utils.MD5Util;
import fabric.common.web.ArrayParameters;
import fabric.server.entity.ProductType;
import fabric.server.entity.Scheme;
import fabric.server.entity.UserRule;
import fabric.server.manager.AccountManager;
import fabric.server.manager.SchemeManager;
import fabric.server.manager.ShopManager;

/**
 * @author likaihua
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/*.xml" })
public class GrantTableResourceTest {
    private String sid;

    @Autowired
    private SchemeManager schemeManager;

    @Autowired
    private AccountManager accountManager;

    @Autowired
    private ShopManager shopManager;

    @Before
    public void setUp() throws JSONException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/account/login");

        String name = "designer";
        String password = MD5Util.md5("123456");
        JSONObject jObj = new JSONObject();
        jObj.accumulate("UserName", name);
        jObj.accumulate("Password", password);
        jObj.accumulate("Power", UserRule.DESIGNER.getPower());
        Form form = new Form();
        form.add("Parameters", jObj.toString());
        Representation r = itemsResource.post(form.getWebRepresentation());

        JSONObject ret = new JSONObject(r);
        String text = ret.getString("text");
        String startChar = ":";
        String endChar = "}";
        String arr[] = text.split(":");
        sid = (arr[1].substring(0, arr[1].length() - 1));
    }

    @Test
    public void testList() throws IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/grant/listSchemes");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);

        Form form = new Form();

        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());
    }

    @Test
    public void testAdd() throws IOException, JSONException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/grant/grant");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);

        Scheme scheme = schemeManager.get(11L);
        String name = "授权表";
        String accountA = scheme.getOwner().getName();
        String accountB = accountManager.getAccountByName(null, "bosser")
            .getShop().getName();
        String description = "描述信息";
        Long productType = ProductType.Scheme.getType();

        JSONObject jObj = new JSONObject();
        jObj.accumulate("Name", name);
        jObj.accumulate("AccountA", accountA);
        jObj.accumulate("AccountB", accountB);
        jObj.accumulate("Description", description);
        jObj.accumulate("ProductID", scheme.getId());
        

        Form form = new Form();
        form.add("Parameters", jObj.toString());
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());
    }
    @Test
    public void testDelete() throws IOException, JSONException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/grant/grantDelete");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);

        Scheme scheme = schemeManager.get(11L);
        String name = "授权表";
        String accountA = scheme.getOwner().getName();
        String accountB = accountManager.getAccountByName(null, "bosser")
            .getShop().getName();
        String description = "描述信息";
        Long productType = ProductType.Scheme.getType();

        JSONObject jObj = new JSONObject();
        jObj.accumulate("Name", name);
        jObj.accumulate("AccountA", accountA);
        jObj.accumulate("AccountB", accountB);
        jObj.accumulate("Description", description);
        jObj.accumulate("ProductID", scheme.getId());

        Form form = new Form();
        form.add("Parameters", jObj.toString());
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());

    }
    

    @Test
    public void testDeleteAll() throws IOException, JSONException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/grant/grantDelete");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);

        List<Scheme> list = schemeManager.getAll();
        if (list == null || list.isEmpty()) {
            System.out.println("方案列表为空");
            return;
        }
        String name = "授权表";
        String accountA = list.get(0).getOwner().getName();
        String accountB = accountManager.getAccountByName(null, "bosser")
            .getShop().getName();
        String description = "描述信息";
        Long productType = ProductType.Scheme.getType();
        List<Long> schemeIDs = new ArrayList();
        for (Scheme item: list) {
            schemeIDs.add(item.getId());
        }

        JSONObject jObj = new JSONObject();
        jObj.accumulate("Name", name);
        jObj.accumulate("AccountA", accountA);
        jObj.accumulate("AccountB", accountB);
        jObj.accumulate("Description", description);
        jObj.accumulate("ProductIDs", schemeIDs);

        Form form = new Form();
        form.add("Parameters", jObj.toString());
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());

    }

    
    
    @Test
    public void testAddAll() throws IOException, JSONException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/grant/grant");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);

        List<Scheme> list = schemeManager.getAll();
        if (list == null || list.isEmpty()) {
            System.out.println("方案列表为空");
            return;
        }
        String name = "授权表";
        String accountA = list.get(0).getOwner().getName();
        String accountB = accountManager.getAccountByName(null, "bosser")
            .getShop().getName();
        String description = "描述信息";
        Long productType = ProductType.Scheme.getType();
        List<Long> schemeIDs = new ArrayList();
        for (Scheme item: list) {
            schemeIDs.add(item.getId());
        }

        JSONObject jObj = new JSONObject();
        jObj.accumulate("Name", name);
        jObj.accumulate("AccountA", accountA);
        jObj.accumulate("AccountB", accountB);
        jObj.accumulate("Description", description);
        jObj.accumulate("ProductIDs", schemeIDs);

        Form form = new Form();
        form.add("Parameters", jObj.toString());
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());

    }
}
