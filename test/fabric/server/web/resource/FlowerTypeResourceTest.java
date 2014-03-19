/**
 * @(#)FlowerTypeResourceTest.java, 2013-6-20. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.server.web.resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.util.Series;
import org.springframework.beans.factory.annotation.Autowired;

import fabric.common.utils.MD5Util;
import fabric.server.entity.UserRule;
import fabric.server.manager.FlowerTypeTagManager;

/**
 * @author nisonghai
 */
public class FlowerTypeResourceTest {

    private String sid;

    @Autowired
    private FlowerTypeTagManager ftTagManager;

    @Before
    public void setUp() throws Exception {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/account/login");
        //"http://fab2.atexco.com:80/action/account/login");
        String name = "admin";
        String password = MD5Util.md5("admin");//"1c6603d99314789430a9ae0fd87978d6";//
        JSONObject jObj = new JSONObject();
        jObj.accumulate("UserName", name);
        jObj.accumulate("Password", password);
        jObj.accumulate("Power", UserRule.SUPER_ADMIN.getPower());
        Form form = new Form();
        form.add("Parameters", jObj.toString());
        Representation r = itemsResource.post(form.getWebRepresentation());

        JsonRepresentation jr = new JsonRepresentation(r);
        JSONObject ret = jr.getJsonObject();
        sid = ret.getString("sid");
    }

    @Test
    public void testList() throws IOException, JSONException {
        ClientResource itemsResource = new ClientResource(
        //"http://localhost:8081/action/flowertype/list");
            "http://fab2.atexco.com:80/action/flowertype/list");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);

        Form form = new Form();

        Long startTime = System.currentTimeMillis();
        Representation r = itemsResource.post(form.getWebRepresentation());
        Long endTime = System.currentTimeMillis();
        //System.out.println(r.getText());
        System.out.println("Cost: " + String.valueOf(endTime - startTime)
            + "ms.");
        JsonRepresentation jr = new JsonRepresentation(r);
        //JSONObject jObj = jr.getJsonObject();

    }

    @Test
    public void testListTag() throws IOException, JSONException {
        ClientResource itemsResource = new ClientResource(
            "http://192.168.130.100:8080/action/flowertype/listTag");
        //"http://localhost:8081/action/flowertype/listTag");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);
        String name = "元素";

        JSONObject jObj = new JSONObject();
        jObj.accumulate("TagTypeName", "");
        Form form = getFormPresentation(jObj);
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());
        ClientResource itemResource = null;
        itemResource = new ClientResource(r.getLocationRef());

    }

    @Test
    public void testListType() throws IOException, JSONException {
        ClientResource itemsResource = new ClientResource(
            "http://192.168.130.100:8080/action/flowertype/listType");
        //"http://localhost:8081/action/flowertype/listType");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);
        Form form = new Form();
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());
        ClientResource itemResource = null;
        itemResource = new ClientResource(r.getLocationRef());

    }

    @Test
    public void testListStyle() throws IOException, JSONException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/flowertype/listStyle");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);
        Form form = new Form();
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());
        ClientResource itemResource = null;
        itemResource = new ClientResource(r.getLocationRef());

    }

    @Test
    public void testAddColor() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/flowertype/addColor");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);

        String name = "褐色";
        JSONObject jObj = new JSONObject();
        jObj.accumulate("Name", name);
        Form form = getFormPresentation(jObj);
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());
    }

    @Test
    public void testAddProduct() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/flowertype/addTag");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);

        String name = "aa";
        JSONObject jObj = new JSONObject();
        jObj.accumulate("TagTypeName", "产品");
        jObj.accumulate("Name", name);
        Form form = getFormPresentation(jObj);
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());
    }

    @Test
    public void testAddStyle() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/flowertype/addStyle");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);

        String name = "条纹";
        JSONObject jObj = new JSONObject();
        jObj.accumulate("Name", name);
        Form form = getFormPresentation(jObj);
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());
    }

    @Test
    public void testDeleteTag() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/flowertype/deleteTag");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);

        Long id = 41L;
        JSONObject jObj = new JSONObject();
        jObj.accumulate("ID", id);
        Form form = getFormPresentation(jObj);
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());

    }

    @Test
    public void testAddElement() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/flowertype/addElement");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);

        String name = "元素";
        JSONObject jObj = new JSONObject();
        jObj.accumulate("Name", name);
        Form form = getFormPresentation(jObj);
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());
    }

    @Test
    public void testAddRecommend() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/flowertype/addRecommend");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);

        String name = "推荐";
        JSONObject jObj = new JSONObject();
        jObj.accumulate("Name", name);
        Form form = getFormPresentation(jObj);
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());
    }

    @Test
    public void testListColor() throws IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/flowertype/listColor");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);
        Form form = new Form();
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());
        ClientResource itemResource = null;
        itemResource = new ClientResource(r.getLocationRef());

    }

    @Test
    public void testListElement() throws IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/flowertype/listElement");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);
        Form form = new Form();
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());
        ClientResource itemResource = null;
        itemResource = new ClientResource(r.getLocationRef());

    }

    @Test
    public void testListRecommend() throws IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/flowertype/listRecommend");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);
        Form form = new Form();
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());
        ClientResource itemResource = null;
        itemResource = new ClientResource(r.getLocationRef());

    }

    @Test
    public void testChunk() throws Exception {
        String filepath = "E:\\Data\\flowertype";
        String filename = "Demo1.xml";
        String filetype = "xml";
        String newFileName = chunk(filepath, filename, filetype);
        System.out.println(newFileName);
    }

    @Test
    public void testAdd() throws Exception {
        ClientResource itemsResource = new ClientResource(
            "http://192.168.130.100:8080/action/flowertype/add");
        //    "http://localhost:8081/action/flowertype/add");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);

        String filepath = "E:\\Data\\flowertype";
        String filename = "Demo1.xml";
        String filetype = "xml";

        String name = "花型09041542" + System.currentTimeMillis();
        String description = "描述";

        filename = "Demo1_Cover.jpg";
        filetype = "jpg";
        String CoverFileName = chunk(filepath, filename, filetype);
        //String PrintFileName = "b7fc07ce-03ae-4875-aeb9-6296790b1ff2.jpg";
        filename = "Demo1.xml";
        filetype = "xml";
        String XMLFileName = chunk(filepath, filename, filetype);
        JSONObject jObj = new JSONObject();
        jObj.accumulate("FlowerTypeName", name);
        jObj.accumulate("Description", description);
        jObj.accumulate("CoverFileName", CoverFileName);
        // jObj.accumulate("PrintFileName", PrintFileName);
        jObj.accumulate("XMLFileName", XMLFileName);

        List<Long> list = new ArrayList();
        list.add(1L);
        jObj.accumulate("StyleIDs", list);
        list.add(7L);
        jObj.accumulate("ElementIDs", list);
        list.add(12L);
        jObj.accumulate("ColorIDs", list);
        list.add(18L);
        jObj.accumulate("RecommendIDs", list);
        list.add(31L);
        jObj.accumulate("ProductIDs", list);

        Form form = getFormPresentation(jObj);

        Long startTime = System.currentTimeMillis();
        Representation r = itemsResource.post(form.getWebRepresentation());
        Long endTime = System.currentTimeMillis();
        System.out.println("Cost: " + String.valueOf(endTime - startTime)
            + "ms.");
        System.out.println(r.getText());
    }

    public Form getFormPresentation(JSONObject jObj) {
        Form form = new Form();
        form.add("Parameters", jObj.toString());
        return form;
    }

    @Test
    public void testRefresh() throws IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/flowertype/refresh");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);

        Representation r = itemsResource.get();
        System.out.println(r.getText());
    }

    @Test
    public void testRefreshTag() throws IOException, JSONException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/flowertype/refreshTag");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);

        String name = "元素";
        JSONObject jObj = new JSONObject();
        jObj.accumulate("TagTypeName", name);
        Form form = getFormPresentation(jObj);
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());
    }

    @Test
    public void testDelete() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/flowertype/delete");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);

        Long id = new Long(501);
        JSONObject jObj = new JSONObject();
        jObj.accumulate("FlowerTypeID", id);
        Form form = getFormPresentation(jObj);
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());
    }

    @Test
    public void testUpdate() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/flowertype/update");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);

        Long id = new Long(501);
        String name = "花型New958";
        String flowerStyleName = "壮丽";
        String description = "New958";
        String coverFileName = "";
        String printFileName = "";//"打印图.jpg";
        String XMLFileName = "";

        JSONObject jObj = new JSONObject();
        jObj.accumulate("FlowerTypeID", id);
        jObj.accumulate("FlowerTypeName", name);
        jObj.accumulate("Description", description);
        jObj.accumulate("FlowerTypeStyleName", flowerStyleName);
        jObj.accumulate("CoverFileName", coverFileName);
        jObj.accumulate("PrintFileName", printFileName);
        jObj.accumulate("XMLFileName", XMLFileName);

        List<Long> list = new ArrayList();
        list.add(1L);
        jObj.accumulate("StyleIDs", list);
        list.add(6L);
        jObj.accumulate("ElementIDs", list);
        list.add(9L);
        list.add(10L);
        list.add(11L);
        jObj.accumulate("ColorIDs", list);
        list.add(18L);
        jObj.accumulate("RecommendIDs", list);

        Form form = getFormPresentation(jObj);
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());
    }

    @Test
    public void testRead() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
        //"http://192.168.130.100:8080/action/flowertype/read");
            "http://localhost:8081/action/flowertype/read");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);

        Long id = new Long(10);
        JSONObject jObj = new JSONObject();
        jObj.accumulate("FlowerTypeID", id);
        Form form = getFormPresentation(jObj);
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());
    }

    @Test
    public void testSetEnable() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/flowertype/setEnable");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);

        Long id = new Long(22);
        boolean enable = true;
        JSONObject jObj = new JSONObject();

        jObj.accumulate("FlowerTypeID", id);
        jObj.accumulate("Enable", enable);

        Form form = getFormPresentation(jObj);
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());

    }

    protected String chunk(String filePath, String filename, String filetype)
        throws Exception {
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(
            //"http://localhost:8081/action/data/chunked");
                "http://192.168.130.100:8080/action/data/chunked");

            FileInputStream fileInputStream = new FileInputStream(filePath
                + File.separator + filename);

            InputStreamEntity httpEntity = new InputStreamEntity(
                fileInputStream, -1);
            httpEntity.setContentType("binary/octet-stream");
            httpEntity.setChunked(true);
            httpPost.setEntity(httpEntity);
            httpPost.setHeader("type", "00000013");
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
