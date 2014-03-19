package fabric.server.web.resource;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.restlet.data.Cookie;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.restlet.util.Series;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import fabric.common.utils.MD5Util;
import fabric.server.entity.FlowerType;
import fabric.server.entity.UserRule;

public class AccountResourceTest {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private String sid;

    @Before
    public void setUp() throws JSONException {
        ClientResource itemsResource = new ClientResource(
        "http://192.168.130.100:8080/action/account/login");
            //"http://localhost:8081/action/account/login");

        String name = "admin";
        String password = MD5Util.md5("admin");
        JSONObject jObj = new JSONObject();
        jObj.accumulate("UserName", name);
        jObj.accumulate("Password", password);
        jObj.accumulate("Power", UserRule.SUPER_ADMIN.getPower());
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
    public void testTimeOut() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/account/login");

        String name = "boss";
        String password = "123456";//MD5Util.md5(name);
        JSONObject jObj = new JSONObject();
        jObj.accumulate("UserName", name);
        jObj.accumulate("Password", password);
        jObj.accumulate("power", UserRule.DESIGNER.getPower());
        Form form = getFormPresentation(jObj);

        //Map<String, Object> attributes = new TreeMap();
        //attributes.put("Set-Cookie", 2);
        //itemsResource.getRequest().setAttributes(attributes);
        Representation r = itemsResource.post(form.getWebRepresentation());
        //String out = itemsResource.gejtRequest().getCookies().getValues("Sid").toString();
        //logger.info("Return" + out);
        //logger.info(values);
        logger.info(r.getText());

    }

    @Test
    public void testRegister() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/account/alterPWD");

        String name = "likaihua";
        String realname = name;
        String password = MD5Util.md5(name);
        String nickname = name;
        String info = "info";
        int power = 3;

        JSONObject jObj = new JSONObject();
        jObj.accumulate("UserName", name);
        jObj.accumulate("RealName", realname);
        jObj.accumulate("Password", password);
        jObj.accumulate("NickName", nickname);
        jObj.accumulate("Info", info);
        jObj.accumulate("Power", power);

        Form form = getFormPresentation(jObj);
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());

    }

    @Test
    public void testAdd() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/account/create");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);
        
        String name = "designer";
        String realname = name;
        String password = MD5Util.md5("123456");
        String nickname = name;
        String info = "info";
        int power = 3;

        JSONObject jObj = new JSONObject();
        jObj.accumulate("UserName", name);
        jObj.accumulate("RealName", realname);
        jObj.accumulate("Password", password);
        jObj.accumulate("NickName", nickname);
        jObj.accumulate("Info", info);
        jObj.accumulate("Power", power);

        Form form = getFormPresentation(jObj);
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());
        
        
        
    }

    @Test
    public void testLogin() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
        //     "http://localhost:8081/action/account/login");
        "http://192.168.130.100:8081/action/account/login");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);
        
        String name = "designer";
        String password = MD5Util.md5("123456");
        JSONObject jObj = new JSONObject();
        jObj.accumulate("UserName", name);
        jObj.accumulate("Password", password);
        jObj.accumulate("Power", UserRule.DESIGNER.getPower());
        Form form = getFormPresentation(jObj);

        //Map<String, Object> attributes = new TreeMap();
        //attributes.put("Set-Cookie", 2);
        //itemsResource.getRequest().setAttributes(attributes);
        Representation r = itemsResource.post(form.getWebRepresentation());
        //String out = itemsResource.gejtRequest().getCookies().getValues("Sid").toString();
        //logger.info("Return" + out);
        //logger.info(values);
        logger.info(r.getText());

    }

    @Test
    public void testLogout() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/account/logout");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", "38097968"));
        itemsResource.setCookies(c);

        Form form = new Form();
        Representation r = itemsResource.post(form.getWebRepresentation());

        //String out = itemsResource.gejtRequest().getCookies().getValues("Sid").toString();
        //logger.info("Return" + out);
        //logger.info(values);
        logger.info(r.getText());

    }

    @Test
    public void testAlterPWD() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/account/alterPWD");

        String name = "123456";
        String password = MD5Util.md5(name);
        JSONObject jObj = new JSONObject();
        jObj.accumulate("UserName", name);
        jObj.accumulate("Password", password);
        jObj.accumulate("power", UserRule.SUPER_ADMIN.getPower());
        Form form = getFormPresentation(jObj);

        //Map<String, Object> attributes = new TreeMap();
        //attributes.put("Set-Cookie", 2);
        //itemsResource.getRequest().setAttributes(attributes);
        Representation r = itemsResource.post(form.getWebRepresentation());

        //String out = itemsResource.gejtRequest().getCookies().getValues("Sid").toString();
        //logger.info("Return" + out);
        //logger.info(values);
        logger.info(r.getText());

    }

    @Test
    public void testList() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
        //"http://localhost:8081/action/account/accountList");
            "http://192.168.130.100:8080/action/account/shopList");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);

        JSONObject jObj = new JSONObject();

        Form form = getFormPresentation(jObj);
        form = new Form();
        Representation r = itemsResource.post(null);
        logger.info(r.getText());
    }

    public Form getFormPresentation(JSONObject jObj) {
        Form form = new Form();
        form.add("Parameters", jObj.toString());
        return form;
    }
}
