/**
 * @(#)DataResourceTest.java, 2013-6-25. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.server.web.resource;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;
import org.restlet.representation.Representation;

import fabric.common.utils.AppUtils;

/**
 * @author nisonghai
 */
public class DataResourceTest {

    @Test
    public void testChunked() throws Exception {
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(
            //"http://192.168.139.20:8081/action/data/chunked");
                "http://192.168.139.20:8081/action/data/chunked");

            String filetype = "xml";
            String filePath = "/D:/scheme/4" + "." + filetype;

            FileInputStream fileInputStream = new FileInputStream(filePath);

            InputStreamEntity httpEntity = new InputStreamEntity(
                fileInputStream, -1);
            httpEntity.setContentType("binary/octet-stream");
            httpEntity.setChunked(true);
            httpPost.setEntity(httpEntity);
            httpPost.setHeader("type", "00000033");
            httpPost.setHeader("id", "1");
            httpPost.setHeader("filename", "Documents.rar");
            httpPost.setHeader("filesize",
                String.valueOf(fileInputStream.available()));
            httpPost.setHeader("filetype", filetype);

            HttpResponse response = client.execute(httpPost);
            //Representation r = (Representation)response.getEntity();
        } catch (Exception e) {
            throw e;
        }

        //String url = "http://obiee.mail.netease.com:8080/logs/" + encode;

        /*
         * HttpClient httpClient = new DefaultHttpClient(); HttpGet httpGet =
         * new HttpGet(url); HttpResponse response =
         * httpClient.execute(httpGet); InputStream input =
         * response.getEntity().getContent(); OutputStream output = new
         * FileOutputStream(new File("/D:/tmp/" + filename)); byte[] bytes = new
         * byte[1024]; int readSize = 0; while ((readSize = input.read(bytes)) >
         * -1) { output.write(bytes, 0, readSize); output.flush(); }
         * output.close(); input.close();
         */
    }

    @Test
    public void testCopyFile() throws IOException {
        /*
         * String fileFrom = "/D:/Documents.rar"; String fileTo = "/E:/";
         * PublicDataSet.copyFile(fileFrom, fileTo);
         */
        String filename = "场景Demo_Cover.JPG";
        String encode = URLEncoder.encode(filename, "utf-8");
        String out = AppUtils.fetchFlowerTypePath(1L, encode);
        System.out.println(out);
    }

}
