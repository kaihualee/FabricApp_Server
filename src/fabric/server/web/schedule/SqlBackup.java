/**
 * @(#)SqlBackup.java, 2013-8-19. 
 * 
 */
package fabric.server.web.schedule;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import fabric.server.sql.MySQLUtils;

/**
 * @author likaihua
 */
@Component
public class SqlBackup implements InitializingBean {

    private static Logger logger = LoggerFactory.getLogger(SqlBackup.class);

    /**
     * 用户名
     */
    @Autowired
    @Value(value = "#{'${jdbc.username}'}")
    private String username;

    /**
     * 密码
     */
    @Autowired
    @Value(value = "#{'${jdbc.password}'}")
    private String password;

    /**
     * 备份路径
     */
    @Autowired
    @Value(value = "#{'${backup.path}'}")
    private String backpath;

    @Autowired
    @Value(value = "#{'${database.name}'}")
    private String databasename;

    @Autowired
    @Value(value = "#{'${database.address}'}")
    private String address;

    @Autowired
    @Value(value = "#{'${mysql.home}'}")
    private String mysqlinstalpath;

    @Scheduled(cron = "59 59 3 * * ?")
    public void mysqlBackup() {
        if (mysqlinstalpath == null || mysqlinstalpath.isEmpty()) {
            logger.info("备份MySQL服务失败: 未找到MySQL的安装目录.");
            return;
        }
        String mysqlpaths;
        try {
            mysqlpaths = mysqlinstalpath + "\\" + "bin" + "\\";
            File backupath = new File(backpath);
            if (!backupath.exists()) {
                backupath.mkdir();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            File backFilePath = new File(backpath + "/"
                + sdf.format(new Date()) + ".sql");
            if (!backFilePath.exists()) {

            }
            String stm = String
                .format(
                    "%smysqldump --opt -h %s --user=%s --password=%s --lock-all-tables=true --result-file=%s --default-character-set=utf8 %s",
                    mysqlpaths, address, username, password, backFilePath,
                    databasename);
            logger.info("备份数据库 ：" + stm.toString());
            try {
                Runtime.getRuntime().exec(stm.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    public void load(String filename) {
        String mysqlpaths = mysqlinstalpath + "\\" + "bin" + "\\";
        String sqlpath = backpath;
        String filepath = backpath + File.separator + filename; // 备份的路径地址
        String stmt1 = mysqlpaths + "mysqladmin -u " + username + " -p"
            + password + " create finacing"; // -p后面加的是你的密码
        String stmt2 = mysqlpaths + "mysql -u " + username + " -p" + password
            + " finacing < " + filepath;
        String[] cmd = { "cmd", "/c", stmt2 };
        try {
            Runtime.getRuntime().exec(stmt1);
            Runtime.getRuntime().exec(cmd);
            logger.info("数据已从 " + filepath + " 导入到数据库中");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
    }
}
