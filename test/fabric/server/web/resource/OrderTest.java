/**
 * @(#)OrderTest.java, 2013-7-7. 
 * 
 */
package fabric.server.web.resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

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

/**
 * @author likaihua
 */
public class OrderTest {

    private String sid;

    @Before
    public void setUp() throws Exception {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/account/login");

        String name = "bosser";
        String password = MD5Util.md5("123456");
        JSONObject jObj = new JSONObject();
        jObj.accumulate("UserName", name);
        jObj.accumulate("Password", password);
        jObj.accumulate("Power", UserRule.BUSINESS.getPower());
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
    public void testOrderSearch() throws IOException, JSONException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/order/orderSearch");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);

        JSONObject jObj = new JSONObject();
        jObj.accumulate("Phone", 119);
        Form form = new Form();
        form.add("Parameters", jObj.toString());
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());
    }

    @Test
    public void testAdd() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/order/newOrder");
        HttpPost httpPost = new HttpPost(
            "http://localhost:8081/action/data/chunked");

        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);

        //String orderName = ("订单名称");
        Long schemeID = 9L;
        //String name = ("周宇");
        //String address = ("浙大玉泉");
        String phone = ("119");
        //String description = ("Info");
        String material = ("麻布");

        DefaultHttpClient client = new DefaultHttpClient();
        String filetype = "jpg";
        String filePath = "C:/Users/Administrator/Desktop/8cf5c1ee-1a8f-46c8-a796-fe6d12c2277b.jpg";
        FileInputStream fileInputStream = new FileInputStream(filePath);
        InputStreamEntity httpEntity = new InputStreamEntity(fileInputStream,
            -1);
        httpEntity.setContentType("binary/octet-stream");
        httpEntity.setChunked(true);
        httpPost.setEntity(httpEntity);
        httpPost.setHeader("type", "00000051");
        //注意
        httpPost.setHeader("filename", filetype);
        httpPost.setHeader("filesize",
            String.valueOf(fileInputStream.available()));
        httpPost.setHeader("filetype", filetype);

        HttpResponse response = client.execute(httpPost);
        HttpEntity entity = response.getEntity();

        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
            entity.getContent(), HTTP.UTF_8));
        String s;
        while ((s = reader.readLine()) != null) {
            builder.append(s);
        }
        System.out.println(builder.toString());
        JSONObject object = new JSONObject(builder.toString());
        String sketchFileName = object.getString("UUID");
        sketchFileName = sketchFileName + "." + filetype;

        JSONObject jObj = new JSONObject();
        //jObj.accumulate("OrderName", orderName);
        jObj.accumulate("SchemeID", schemeID);
        //jObj.accumulate("CustomerName", name);
        //jObj.accumulate("CustomerAddress", address);
        jObj.accumulate("Phone", phone);
        //jObj.accumulate("Info", description);
        jObj.accumulate("Material", material);
        jObj.accumulate("SketchFileName", sketchFileName);

        Form form = new Form();
        form.add("Parameters", jObj.toString());
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());
    }

    @Test
    public void testModify() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/order/modifyOrder");

        Long id = 2L;
        String name = "likaihu";
        String address = "NetEase";
        String phone = "110";
        String description = "desc";
        String material = "尼龙";

        JSONObject jObj = new JSONObject();
        jObj.accumulate("ID", id);
        jObj.accumulate("CustomerName", name);
        jObj.accumulate("CustomerAddress", address);
        jObj.accumulate("phone", phone);
        jObj.accumulate("Info", description);
        jObj.accumulate("Material", material);

        Form form = new Form();
        form.add("Parameters", jObj.toString());
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());
    }

    @Test
    public void testRead() throws JSONException, IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/order/read");

        Long id = 1L;
        JSONObject jObj = new JSONObject();
        jObj.accumulate("ID", id);
        Form form = new Form();
        form.add("Parameters", jObj.toString());
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());

    }

    @Test
    public void testList() throws IOException {
        ClientResource itemsResource = new ClientResource(
            "http://localhost:8081/action/order/orderList");
        Series<Cookie> c = itemsResource.getCookies();
        c.add(new Cookie("Sid", sid));
        itemsResource.setCookies(c);

        Form form = new Form();
        Representation r = itemsResource.post(form.getWebRepresentation());
        System.out.println(r.getText());

        ClientResource itemResource = null;
        itemResource = new ClientResource(r.getLocationRef());

    }
}
