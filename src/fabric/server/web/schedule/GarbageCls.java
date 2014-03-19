/**
 * @(#)GarbageCls.java, 2013-8-6. 
 * 
 */
package fabric.server.web.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import fabric.common.db.Status;
import fabric.common.utils.AppUtils;
import fabric.common.utils.FileUtils;
import fabric.server.entity.DataFile;
import fabric.server.entity.FlowerType;
import fabric.server.manager.DataFileManager;
import fabric.server.manager.FlowerTypeManager;

/**
 * @author likaihua
 */
@Component
public class GarbageCls {

    @Autowired
    private DataFileManager dataFileManager;

    @Autowired
    private FlowerTypeManager flowerTypeManager;
    
    private static String FLOWER_PATH = AppUtils.getUploadPath()
        + "/FlowerType/temp";

    private static String SCENE_PATH = AppUtils.getUploadPath() + "/Scene/temp";

    private static String SCHEME_PATH = AppUtils.getUploadPath()
        + "/Scheme/temp";

    private static String ORDER_PATH = AppUtils.getUploadPath() + "/Order/temp";

    private static String UNKNOWN_PATH = AppUtils.getUploadPath() + "unknown";

    @Scheduled(cron = "59 59 23 * * ?")
    public void task() {
        FileUtils.deleteFiles(FLOWER_PATH);
        FileUtils.deleteFiles(SCENE_PATH);
        FileUtils.deleteFiles(SCHEME_PATH);
        FileUtils.deleteFiles(ORDER_PATH);
        FileUtils.deleteFiles(UNKNOWN_PATH);

    }

}
