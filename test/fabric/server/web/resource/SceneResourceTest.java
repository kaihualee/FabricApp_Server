package fabric.server.web.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import org.restlet.util.Series;

import fabric.common.utils.MD5Util;
import fabric.server.entity.UserRule;

public class SceneResourceTest {

    private String sid;

    @Before
    public void setUp() throws JSONException {
        ClientResource itemsResource = new ClientResource(
            //"http://192.168.130.100:8081/action/account/login");
            "http://localhost:8081/action/account/login");
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
    public void testAdd() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/scene/add");

        List<Integer> lst = new ArrayList<Integer>();
        int count = 18;
        for (int nLoop = 1; nLoop <= count; ++nLoop) {
            lst.add(nLoop);
        }
        int nCount = 1;
        for (Integer item: lst) {
            String sceneName = String.format("Demo%d", item);
            String description = "无";
            String CABFileName = String.format("Demo%d.cab", item);
            String CoverFileName = String.format("Demo%d_Cover.JPG", item);
            String XMLFileName = String.format("Demo%d.xml", item);
            String sceneStyleName = "中式";
            String scenePosName = "卧室";
            JSONObject jo = new JSONObject();

            jo.accumulate("ID", nCount++);
            jo.accumulate("SceneName", sceneName);
            jo.accumulate("Description", description);
            jo.accumulate("CABFileName", CABFileName);
            jo.accumulate("CoverFileName", CoverFileName);
            jo.accumulate("XMLFileName", XMLFileName);
            jo.accumulate("SceneStyleName", sceneStyleName);
            jo.accumulate("ScenePositionName", scenePosName);

            Form form = new Form();
            form.add("Parameters", jo.toString());
            Representation r = itemsResource.post(form.getWebRepresentation());
            System.out.println(r.getText());
        }
    }

    @Test
    public void testOne() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/scene/add");

        int item = 1;
        String sceneName = String.format("Demo%d", item);
        String description = "无";
        String sceneStyleName = "中式";
        String scenePosName = "卧室";
        JSONObject jo = new JSONObject();

        jo.accumulate("SceneName", sceneName);
        jo.accumulate("Description", description);
        jo.accumulate("SceneStyleName", sceneStyleName);
        jo.accumulate("ScenePositionName", scenePosName);

        Form form = new Form();
        form.add("Parameters", jo.toString());
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());
    }

    @Test
    public void testRead() throws IOException, JSONException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/scene/read");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);

        List<Integer> lst = new ArrayList<Integer>();
        Integer rid = 4;
        lst.add(rid);

        for (Integer item: lst) {
            Long id = new Long(item);
            String sceneName = String.format("Demo%d", item);
            JSONObject jo = new JSONObject();
            jo.accumulate("SceneID", id);
            jo.accumulate("SceneName", sceneName);
            Form form = new Form();
            form.add("Parameters", jo.toString());
            Representation r = itemsResource.post(form.getWebRepresentation());
            System.out.println(r.getText());
        }
    }

    @Test
    public void testList() throws IOException {
        ClientResource itemsResource = new ClientResource(
            //"http://192.168.130.100:8081/action/scene/list");
            "http://localhost:8081/action/scene/list");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);

        Representation r = itemsResource.post(null);
        System.out.println(r.getText());

        ClientResource itemResource = null;
        itemResource = new ClientResource(r.getLocationRef());
    }
    @Test
    public void testRefresh() throws IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/scene/refresh");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);

        Representation r = itemsResource.post(null);
        System.out.println(r.getText());

        ClientResource itemResource = null;
        itemResource = new ClientResource(r.getLocationRef());
    }
}
