package fabric.server.web.resource;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Form;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import fabric.common.utils.MD5Util;
import fabric.common.utils.AppUtils;
import fabric.common.web.BaseVO;
import fabric.common.web.ErrorCode;
import fabric.common.web.ErrorVO;
import fabric.common.web.WebParameters;
import fabric.server.entity.Account;
import fabric.server.entity.DataFile;
import fabric.server.entity.FileType;
import fabric.server.entity.FlowerType;
import fabric.server.entity.Scene;
import fabric.server.entity.ScenePosition;
import fabric.server.entity.SceneStyle;
import fabric.server.manager.SceneManager;
import fabric.server.manager.ScenePositionManager;
import fabric.server.manager.SceneStyleManager;
import fabric.server.vo.FlowerTypeVO;
import fabric.server.vo.ListVO;
import fabric.server.vo.MapVO;
import fabric.server.vo.SceneVO;
import fabric.server.vo.StyleVO;
import fabric.server.vo.VersionVO;

@Controller
@Scope("prototype")
public class SceneResource extends AuthResource<Scene, SceneManager> {

    @Autowired
    private SceneManager sceneManager;

    @Autowired
    private SceneStyleManager ssManager;

    @Autowired
    private ScenePositionManager spManager;

    private static String LOCAL_PATH = AppUtils.getUploadPath() + "/Scene";

    @Override
    protected SceneManager getManager() {
        return sceneManager;
    }

    /**
     * 获取场景列表
     * 
     * @return
     */
    public BaseVO list() {
        List<Scene> list = sceneManager.getAll(account);
        if (list == null || list.isEmpty()) {
            return new ListVO();
        }
        ListVO result = new ListVO();
        for (Scene item: list) {
            SceneVO sceneVO = new SceneVO(item);
            result.add(sceneVO);
        }
        logger.info(
            "Account:{}, method:{}, execute:{}, parameters:[], Result={}",
            new Object[] { account.getName(), this.getClass().getSimpleName(),
                getAction(), result });
        return result;
    }

    /**
     * 版本同步
     * 
     * @return
     */
    public BaseVO refresh() {
        List<Scene> list = sceneManager.getAll(account);
        if (list == null || list.isEmpty()) {
            return new ListVO();
        }
        ListVO result = new ListVO();
        for (Scene item: list) {
            VersionVO versionVO = new VersionVO(item);
            result.add(versionVO);
        }
        logger.info(
            "Account:{}, method:{}, execute:{}, parameters:[], Result={}",
            new Object[] { account.getName(), this.getClass().getSimpleName(),
                getAction(), result });
        return result;
    }

    /**
     * 场景风格列表
     * 
     * @return
     */
    public BaseVO listStyle() {
        List<SceneStyle> list = ssManager.getAll();
        if (list == null || list.isEmpty()) {
            return new ListVO();
        }
        ListVO result = new ListVO();
        for (SceneStyle item: list) {
            StyleVO styleVO = new StyleVO(item);
            result.add(styleVO);
        }
        logger.info(
            "Account:{}, method:{}, execute:{}, parameters:[], Result={}",
            new Object[] { account.getName(), this.getClass().getSimpleName(),
                getAction(), result });
        return result;
    }

    /**
     * 场景位置列表
     * 
     * @return
     */
    public BaseVO listSP() {
        List<ScenePosition> list = spManager.getAll();
        if (list == null || list.isEmpty()) {
            return new ListVO();
        }
        ListVO result = new ListVO();
        for (ScenePosition item: list) {
            StyleVO styleVO = new StyleVO(item);
            result.add(styleVO);
        }
        logger.info(
            "Account:{}, method:{}, execute:{}, parameters:[], Result={}",
            new Object[] { account.getName(), this.getClass().getSimpleName(),
                getAction(), result });
        return result;
    }

    /**
     * 添加场景（未启用）
     * 
     * @param parameters
     * @return
     */
    @Deprecated
    public BaseVO add(WebParameters parameters) {
        Long id = parameters.getLong("ID");
        String name = parameters.getString("SceneName");
        String description = parameters.getString("Description");
        String cabFileName = parameters.getString("CABFileName");
        String coverFileName = parameters.getString("CoverFileName");
        String xmlFileName = parameters.getString("XMLFileName");
        String sceneStyleName = parameters.getString("SceneStyleName");
        String scenePosName = parameters.getString("ScenePositionName");

        //Check
        if (name == null || StringUtils.isEmpty(name) || description == null
            || StringUtils.isEmpty(description) || cabFileName == null
            || StringUtils.isEmpty(cabFileName) || coverFileName == null
            || StringUtils.isEmpty(coverFileName) || xmlFileName == null
            || StringUtils.isEmpty(xmlFileName) || sceneStyleName == null
            || StringUtils.isEmpty(sceneStyleName) || scenePosName == null
            || StringUtils.isEmpty(scenePosName)) {
            return new ErrorVO(ErrorCode.PARAMETER_ERROR);
        }

        if (sceneManager.checkByName(name)) {
            return new ErrorVO(ErrorCode.PARAMETER_NAME_IS_EXIST);
        }

        SceneStyle sceneStyle = ssManager.getByName(sceneStyleName);
        ScenePosition scenePos = spManager.getByName(scenePosName);

        if (sceneStyle == null || scenePos == null)
            return new ErrorVO(ErrorCode.PARAMETER_ERROR);

        Scene scene = new Scene();
        scene.setScenePos(scenePos);
        scene.setSceneStyle(sceneStyle);

        //Need modify
        String coverImagePath = String.format("%s/%d/%s", LOCAL_PATH, id,
            coverFileName);
        String cabPath = String.format("%s/%d/%s", LOCAL_PATH, id, cabFileName);
        String xmlPath = String.format("%s/%d/%s", LOCAL_PATH, id, xmlFileName);

        File coverFile = new File(coverImagePath);
        File cabFile = new File(cabPath);
        File xmlFile = new File(xmlPath);

        if (!coverFile.exists() || !coverFile.exists() || !coverFile.exists()) {
            return new ErrorVO(ErrorCode.SCENE_FILE_NO_EXIST);
        }

        //ToDo
        DataFile coverImage = new DataFile(coverFileName, FileType.Cover_Image);
        coverImage.setMd5Code(MD5Util.md5(coverFile));
        scene.setCoverImage(coverImage);

        //ToDo
        DataFile cab = new DataFile(cabFileName, FileType.Cab);
        cab.setMd5Code(MD5Util.md5(cabFile));
        scene.setCab(cab);

        //ToDo		 
        DataFile xml = new DataFile(xmlFileName, FileType.Xml);
        xml.setMd5Code(MD5Util.md5(xmlFile));
        scene.setXmlFile(xml);

        scene.setName(name);
        scene.setDescription(description);
        scene.setOwner(account);
        sceneManager.save(scene);
        MapVO mapVO = new MapVO();
        mapVO.put("ID", scene.getId());
        return mapVO;
    }

    /**
     * 读取特定场景信息
     * 
     * @param parameters
     * @return
     */
    public BaseVO read(WebParameters parameters) {
        Long id = parameters.getLong("SceneID");
        if (id == null)
            return new ErrorVO(ErrorCode.PARAMETER_ID_NO_EXIST);

        Scene scene = sceneManager.getById(account, id);
        if (scene == null)
            return new ErrorVO(ErrorCode.NOPERMISSIONS);
        SceneVO sceneVO = new SceneVO(scene);
        logger
            .info(
                "Account:{}, method:{}, execute:{}, parameters:[SceneID={}], Result={}",
                new Object[] { account.getName(),
                    this.getClass().getSimpleName(), getAction(), id, sceneVO });
        return sceneVO;
    }

}
