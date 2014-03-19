package fabric.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

@Controller
@Lazy(false)
public class AppUtils implements InitializingBean {

    private final static String prefix = "downloads/";//下载

    private final static String ScenePath = prefix + "Scene";//场景地址

    private final static String SchemePath = prefix + "Scheme";//方案地址

    private final static String DesignPath = prefix + "Design";//设计地址

    private final static String FlowerTypePath = prefix + "FlowerType";//花型地址

    private final static String OrderPath = prefix + "Order";//订单地址

    public final static String UNKNOWN = "unknown";//未知

    private static String URL;

    private static String ip; //获取本地IP

    private static Logger logger = LoggerFactory.getLogger(AppUtils.class);

    private static int PORT;

    private static String PROJECT_PATH;

    @Autowired
    @Value(value = "#{'${fabric.port}'}")
    private int portValue;

    @Autowired
    @Value(value = "#{'${fabric.app_path}'}")
    private String projectPathValue;

    @Autowired
    @Value(value = "#{'${fabric.app_url}'}")
    private String appUrl;

    public static String fetchScenePath(Long id, String filename) {

        String ret = String.format("http://%s:%d/%s/%d/%s", ip, PORT,
            ScenePath, id, filename);

        return ret;
    }

    public static String fetchSchemePath(Long id, String filename) {
        String ret = String.format("http://%s:%d/%s/%d/%s", ip, PORT,
            SchemePath, id, filename);
        return ret;
    }

    public static String fetchDesignPath(Long id, String filename) {
        String ret = String.format("http://%s:%d/%s/%d/%s", ip, PORT,
            DesignPath, id, filename);
        return ret;
    }

    public static String fetchFlowerTypePath(Long id, String filename) {
        String ret = String.format("http://%s:%d/%s/%d/%s", ip, PORT,
            FlowerTypePath, id, filename);
        return ret;
    }

    public static String fetchOrderPath(String shopName, Long id,
        String filename) {
        String ret = String.format("http://%s:%d/%s/%s/%d/%s", ip, PORT,
            OrderPath, shopName, id, filename);
        return ret;
    }

    public static String getLocalIP() {
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
        byte[] ipAddr = addr.getAddress();
        String ipAddrStr = "";
        for (int i = 0; i < ipAddr.length; i++) {
            if (i > 0) {
                ipAddrStr += ".";
            }
            ipAddrStr += ipAddr[i] & 0xFF;
        }
        //System.out.println(ipAddrStr);  
        return ipAddrStr;
    }

    public static String getPrefix() {
        return prefix;
    }

    public static String getScenePath() {
        return ScenePath;
    }

    public static String getSchemePath() {
        return SchemePath;
    }

    public static String getDesignPath() {
        return DesignPath;
    }

    public static String getFlowerTypePath() {
        return FlowerTypePath;
    }

    public static String getIp() {
        return ip;
    }

    public static int getPort() {
        return PORT;
    }

    public static String getUploadPath() {
        return PROJECT_PATH + "/downloads";
    }

    public static boolean copyFile(String fileSource, String dirDest,
        String fileName) {
        try {
            FileUtils.copyFile(fileSource, dirDest, fileName);
            return true;
        } catch (IOException e) {
            logger.info("copyFile fail: from {} to {}.", new Object[] {
                fileSource + File.separator + fileName, dirDest });
            return false;
        }
    }

    /**
     * 删除单个文件
     * 
     * @param sPath
     *            被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        return FileUtils.deleteFile(sPath);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        PORT = this.portValue;
        PROJECT_PATH = this.projectPathValue;
        URL = this.appUrl;
        if(URL != null && !URL.isEmpty()){
            this.ip = URL;
        }else{
            this.ip = getLocalIP();
        }
    }

    public static void main(String[] args) {
        System.out.println(AppUtils.fetchOrderPath("富安娜", 1L, "效果图"));
    }
}
