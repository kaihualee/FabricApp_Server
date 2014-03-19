package fabric.server.web.resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
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

public class SchemeResourceTest {

    private String sid;

    @Before
    public void setUp() throws JSONException {
        ClientResource itemsResource = new ClientResource(
        //"http://192.168.130.100:8080/action/account/login");
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
    public void testAdd() throws Exception {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/scheme/add");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);
        
        String filepath = "E:\\Data\\scheme\\1";
        String filename = "Demo1.xml";
        String filetype = "xml";
        
        String name = "方案081551";
        String description = "描述";
        filename = "61b86213-dc74-4422-b1f7-ed89cfa20b25.jpg";
        filetype = "jpg";
        String CoverFileName = chunk(filepath, filename, filetype);
        filename = "6cf44cb8-6c41-4c47-9d9f-163a23c6dc5f.xml";
        filetype = "xml";
        String XMLFileName = chunk(filepath, filename, filetype);

        JSONObject jObj = new JSONObject();
        jObj.accumulate("Name", name);
        jObj.accumulate("Desc", description);
        jObj.accumulate("CoverImageName", CoverFileName);
        jObj.accumulate("XmlName", XMLFileName);

        Form form = getFormPresentation(jObj);
        Long startTime = System.currentTimeMillis();
        Representation r = itemsResource.post(form.getWebRepresentation());
        Long endTime = System.currentTimeMillis();
        System.out.println("Cost: " + String.valueOf(endTime-startTime) + "ms.");
        System.out.println(r.getText());
    }
    
    @Test
    public void testRefresh() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/scheme/refresh");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);

        //Form form = getFormPresentation(jObj);
        Representation r = itemsResource.post(null);
        System.out.println(r.getText());
    }

    @Test
    public void testDelete() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/scheme/delete");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);
        
        Long id = 1324L;
        JSONObject jObj = new JSONObject();
        jObj.accumulate("ID", id);
        
        Form form = getFormPresentation(jObj);
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());
    }
    
    @Test
    public void testRead() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/scheme/read");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", "1841477869"));
        itemsResource.setCookies(c);

        Long id = new Long(1);
        JSONObject jObj = new JSONObject();
        jObj.accumulate("ID", id);

        Form form = getFormPresentation(jObj);
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());
    }

    @Test
    public void testUpdate() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
            "http://192.168.130.100:8080/action/scheme/update");
        Long id = new Long(2);
        String name = "方案1518";
        String description = "描述1518";
        //String CoverFileName = "";//"Demo.jpg";
        //String XMLFileName = "";//"Demo.xml";

        JSONObject jObj = new JSONObject();
        jObj.accumulate("ID", id);
        jObj.accumulate("Name", name);
        jObj.accumulate("Desc", description);
        //jObj.accumulate("SceneID", 1);
        //jObj.accumulate("SchemeContentID", 9);
        //jObj.accumulate("ColorID", 17);
        //jObj.accumulate("StyleID", 21);
        //jObj.accumulate("PatternID", 25);
        //jObj.accumulate("DesignID", 1);
        //jObj.accumulate("CoverImageName", CoverFileName);
        //jObj.accumulate("XmlName", XMLFileName);

        Form form = getFormPresentation(jObj);
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());

    }

    @Test
    public void testSetEnable() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/scheme/setEnable");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);
        
        Long id = new Long(46);
        boolean enable = true;
        JSONObject jObj = new JSONObject();

        jObj.accumulate("ID", id);
        jObj.accumulate("Enable", enable);

        Form form = getFormPresentation(jObj);
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());

    }
    
    @Test
    public void testList() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
        "http://192.168.130.100:8080/action/scheme/list");   
        //    "http://localhost:8081/action/scheme/list");

        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);

        JSONObject jo = new JSONObject();
        Form form = new Form();
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());

    }

    @Test
    public void testListFTColor() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/scheme/listFTColor");

        JSONObject jo = new JSONObject();
        Form form = new Form();
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());

    }

    @Test
    public void testListSContent() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/scheme/listSContent");

        JSONObject jo = new JSONObject();
        Form form = new Form();
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());

    }

    @Test
    public void testListFTPattern() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/scheme/listFTPattern");

        JSONObject jo = new JSONObject();
        Form form = new Form();
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());

    }

    @Test
    public void testListFTStyle() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/scheme/listFTStyle");

        JSONObject jo = new JSONObject();
        Form form = new Form();
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());

    }

    @Test
    public void testAddFTStyle() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/scheme/addStyle");

        String[] styles = { "抽象", "冷峻", "可爱", "热烈" };

        for (String item: styles) {
            JSONObject jo = new JSONObject();
            jo.accumulate("Name", item);
            Form form = new Form();
            form.add("Parameters", jo.toString());
            Representation r = itemsResource.post(form.getWebRepresentation());
            System.out.println(r.getText());
        }
    }

    @Test
    public void testAddContent() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/scheme/addContent");

        String[] contents = { "人物", "风景", "体育", "卡通" };

        for (String item: contents) {
            JSONObject jo = new JSONObject();
            jo.accumulate("Name", item);
            Form form = new Form();
            form.add("Parameters", jo.toString());
            Representation r = itemsResource.post(form.getWebRepresentation());
            System.out.println(r.getText());
        }
    }

    @Test
    public void testAddColor() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/scheme/addColor");

        String[] colors = { "128", "255", "127", "239" };

        for (String item: colors) {
            JSONObject jo = new JSONObject();
            jo.accumulate("Color", item);
            Form form = new Form();
            form.add("Parameters", jo.toString());
            Representation r = itemsResource.post(form.getWebRepresentation());
            System.out.println(r.getText());
        }
    }

    @Test
    public void testAddPattern() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/scheme/addPattern");

        String[] pattern = { "大花", "碎花", "线条", "多边形" };

        for (String item: pattern) {
            JSONObject jo = new JSONObject();
            jo.accumulate("Name", item);
            Form form = new Form();
            form.add("Parameters", jo.toString());
            Representation r = itemsResource.post(form.getWebRepresentation());
            System.out.println(r.getText());
        }
    }

    public Form getFormPresentation(JSONObject jObj) {
        Form form = new Form();
        form.add("Parameters", jObj.toString());
        return form;
    }
    
    protected String chunk(String filePath, String filename, String filetype)
    throws Exception {
    try {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(
            "http://localhost:8081/action/data/chunked");

        FileInputStream fileInputStream = new FileInputStream(filePath
            + File.separator + filename);

        InputStreamEntity httpEntity = new InputStreamEntity(
            fileInputStream, -1);
        httpEntity.setContentType("binary/octet-stream");
        httpEntity.setChunked(true);
        httpPost.setEntity(httpEntity);
        httpPost.setHeader("type", "00000033");
        httpPost.setHeader("filename", filetype);
        httpPost.setHeader("filesize",
            String.valueOf(fileInputStream.available()));

        HttpResponse response = client.execute(httpPost);
        HttpEntity entity = response.getEntity();
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
            entity.getContent(), HTTP.UTF_8));
        String s;
        while ((s = reader.readLine()) != null) {
            builder.append(s);
        }
        //System.out.println(builder.toString());
        JSONObject jObj = new JSONObject(builder.toString());
        return jObj.getString("UUID") + "." + filetype;
    } catch (Exception e) {
        throw e;
    }
}

}
