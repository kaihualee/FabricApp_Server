/**
 * @(#)UrlEncoderTest.java, 2013-7-8. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * @author nisonghai
 */
public class UrlEncoderTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        try {
            String filename = "场景Demo_Cover.JPG";
            String encode = URLEncoder.encode(filename, "utf-8");
            String url = "http://localhost:80/downloads/" + encode;
            System.out.println(url);
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);
            InputStream input = response.getEntity().getContent();
            OutputStream output = new FileOutputStream(new File("/D:/tmp/"
                + filename));
            byte[] bytes = new byte[1024];
            int readSize = 0;
            while ((readSize = input.read(bytes)) > -1) {
                output.write(bytes, 0, readSize);
                output.flush();
            }
            output.close();
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
