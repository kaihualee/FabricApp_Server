/**
 * @(#)DataResource.java, 2013-6-25. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.server.web.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.restlet.engine.http.HttpRequest;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import fabric.common.utils.AppUtils;
import fabric.common.web.ErrorCode;
import fabric.common.web.ErrorVO;

/**
 * @author nisonghai
 */
//@Controller("dataResource")
@Service
@Scope("prototype")
public class DataResource extends ServerResource {

    private static String LOCAL_PATH = AppUtils.getUploadPath();

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static int BUFFER_SIZE = 1024 * 64;

    @Post
    public Representation chunked(Representation entity) {
        HttpRequest request = (HttpRequest) getRequest();
        JSONObject ret = new JSONObject();
        try {
            String filename = request.getHeaders().getFirstValue("filename");
            String fileSize = request.getHeaders().getFirstValue("filesize");
            String typeStr = request.getHeaders().getFirstValue("type");
            //Client在不变动的情况下使用filename来传输文件类型 
            String fileType = filename;
            //String id = request.getHeaders().getFirstValue("id");

            int length = Integer.valueOf(fileSize);
            int type = new java.math.BigInteger(typeStr, 16).intValue();
            String filepath;
            switch (type) {
                case 0x00000011:
                    ;
                case 0x00000012:
                    ;
                case 0x00000013:
                    filepath = "FlowerType";
                    break;
                case 0x00000021:
                    ;
                case 0x00000022:
                    ;
                case 0x00000023:
                    filepath = "Scene";
                    break;
                case 0x00000031:
                    ;
                case 0x00000032:
                    ;
                case 0x00000033:
                    filepath = "Scheme";
                    break;
                case 0x00000041:
                    filepath = "Design";
                    break;
                case 0x00000051:
                    filepath = "Order";
                    break;
                default:
                    filepath = AppUtils.UNKNOWN;
                    ;
            }
            filepath = filepath + File.separator + "temp";
            //filename = URLDecoder.decode(filename, "UTF-8");

            //Generate uuid
            String uuid = UUID.randomUUID().toString();
            //拼接成文件名,产生输出路径
            String uuidFile = uuid + "." + fileType;
            String destPath = LOCAL_PATH + File.separator + filepath
                + File.separator + uuidFile;

            logger.info("wirteLocalFile begin:{}", new Object[] { destPath });
            writeLocalFile(destPath, entity.getStream(), length);
            logger.info("wirteLocalFile end:{}", new Object[] { destPath });
            ret.accumulate("UUID", uuid);
            return new JsonRepresentation(ret);
        } catch (Exception e) {
            logger.error("Get file stream error.", e);
            return new ErrorVO(ErrorCode.FILE_UPLOAD_ERROR).representation();
        }
    }

    /**
     * 写入本地文件
     * 
     * @param file
     * @param inputStream
     * @param length
     * @throws IOException
     */
    private void writeLocalFile(String filePath, InputStream inputStream,
        int length) throws IOException {
        FileOutputStream output = null;
        try {

            File file = new File(filePath);
            File dir = new File(file.getParent());
            if (!dir.exists()) {
                // 创建目录
                dir.mkdirs();
            } else {
                if (file.exists()) {
                    // 删除原有文件
                    file.deleteOnExit();
                }
            }

            output = new FileOutputStream(file);
            int bufferSize = BUFFER_SIZE;
            if (length < BUFFER_SIZE) {
                bufferSize = length;
            }

            byte[] buffer = new byte[bufferSize];
            int readTotal = 0;
            int readSize = 0;
            while ((readSize = inputStream.read(buffer)) > -1) {
                readTotal += readSize;
                if (readTotal > length) {
                    int over = readSize - (readTotal - length);
                    output.write(buffer, 0, over);
                    output.flush();
                    break;
                } else if (readTotal == length) {
                    output.write(buffer, 0, readSize);
                    output.flush();
                    break;
                } else {
                    output.write(buffer, 0, readSize);
                    output.flush();
                }
            }
        } finally {
            if (output != null) {
                output.close();
            }
        }
    }

}
