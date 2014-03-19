package fabric.server.web.resource;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import fabric.common.db.Status;
import fabric.common.utils.AppUtils;
import fabric.common.utils.MD5Util;
import fabric.common.web.BaseVO;
import fabric.common.web.ErrorCode;
import fabric.common.web.ErrorVO;
import fabric.common.web.WebParameters;
import fabric.common.web.XmlHelper;
import fabric.common.web.XmlParameter;
import fabric.common.web.XmlType;
import fabric.server.entity.DataFile;
import fabric.server.entity.FileType;
import fabric.server.entity.FlowerType;
import fabric.server.entity.Scene;
import fabric.server.entity.Scheme;
import fabric.server.entity.UserRule;
import fabric.server.manager.DataFileManager;
import fabric.server.manager.FlowerTypeManager;
import fabric.server.manager.GrantTableManager;
import fabric.server.manager.SceneManager;
import fabric.server.manager.SchemeManager;
import fabric.server.vo.ListVO;
import fabric.server.vo.SchemeVO;
import fabric.server.vo.VersionVO;

@Controller
@Scope("prototype")
public class SchemeResource extends AuthResource<Scheme, SchemeManager> {

    @Autowired
    private SchemeManager schemeManager;

    @Autowired
    private FlowerTypeManager ftManager;

    @Autowired
    private SceneManager sceneManager;

    @Autowired
    private GrantTableManager gtManager;

    @Autowired
    private DataFileManager dataFileManager;

    private static String LOCAL_PATH = AppUtils.getUploadPath() + "/Scheme";

    private final static String TEMP = "temp";

    /**
     * 方案xml中场景结点名
     */
    private final String SCENEID_NODENAME = "SceneID";

    @Override
    protected SchemeManager getManager() {
        return schemeManager;
    }

    /**
     * 获取方案列表
     * 
     * @return
     */
    public BaseVO list() {
        List<Scheme> list = schemeManager.getAll(account,
            accountSession.getPower());
        if (list == null || list.isEmpty()) {
            return new ListVO();
        }
        ListVO result = new ListVO();
        for (Scheme item: list) {
            SchemeVO schemeVO = new SchemeVO(item);
            result.add(schemeVO);
        }
        logger
            .info(
                "Account:{}, method:{}, execute:{}, parameters:[], Result={}, Size:{}",
                new Object[] { account.getName(),
                    this.getClass().getSimpleName(), getAction(), result,
                    list.size() });
        return result;

    }

    /**
     * 版本同步
     * 
     * @return
     */
    public BaseVO refresh() {
        List<Scheme> list = schemeManager.getAll(account,
            accountSession.getPower());
        if (list == null || list.isEmpty()) {
            return new ListVO();
        }
        ListVO result = new ListVO();
        for (Scheme item: list) {
            VersionVO schemeVO = new VersionVO(item);
            result.add(schemeVO);
        }
        logger.info(
            "Account:{}, method:{}, execute:{}, parameters:[], Result={}",
            new Object[] { account.getName(), this.getClass().getSimpleName(),
                getAction(), result });
        return result;
    }

    /**
     * 添加方案
     * 
     * @param parameters
     * @return
     */
    public BaseVO add(WebParameters parameters) {
        String name = parameters.getString("Name");
        String description = parameters.getString("Desc");
        Long sceneID = parameters.getLong("SceneID");
        String coverFileName = parameters.getString("CoverImageName");
        String XMLFileName = parameters.getString("XmlName");
        Long flowerTypeID = parameters.getLong("MainFlowerTypeID");

        // String coverUUID = parameters.getString("CoverImageUUID");
        // String xmlUUID = parameters.getString("XmlUUID");
        String coverUUID = coverFileName;
        String xmlUUID = XMLFileName;
        /**
         * 验证数据完整性
         */
        if (name == null || StringUtils.isEmpty(name) || coverFileName == null
            || StringUtils.isEmpty(coverFileName) || XMLFileName == null
            || StringUtils.isEmpty(XMLFileName)) {
            return new ErrorVO(ErrorCode.PARAMETER_ERROR);
        }

        if (coverUUID == null || coverUUID.isEmpty() || xmlUUID == null
            || xmlUUID.isEmpty()) {
            return new ErrorVO(ErrorCode.UUID_NO_FOUND);
        }

        /**
         * 查重
         */
        if (schemeManager.checkByName(name)) {
            return new ErrorVO(ErrorCode.PARAMETER_NAME_IS_EXIST);
        }

        Scheme scheme = new Scheme();
        scheme.setName(name);
        scheme.setDescription(description);
        scheme.setOwner(account);
        scheme.setStatus(Status.Deleted);
        schemeManager.save(scheme);

        /**
         * 验证文件有效性
         */
        //原文件->目标文件
        String dirFrom = LOCAL_PATH + File.separator + TEMP;
        String dirTo = LOCAL_PATH + File.separator
            + String.valueOf(scheme.getId());

        //封面图
        DataFile coverImage = createDataFile(dirFrom, coverFileName, coverUUID,
            dirTo, FileType.Cover_Image);
        if (coverImage == null) {
            return new ErrorVO(ErrorCode.SCHEME_FILE_NO_EXIST);
        } else {
            coverImage.setMd5Code(MD5Util.md5(new File(dirTo + File.separator
                + coverFileName)));
            coverImage.setStatus(Status.Deleted);
            dataFileManager.save(coverImage);

        }

        //XML
        DataFile xmlFile = createDataFile(dirFrom, XMLFileName, xmlUUID, dirTo,
            FileType.Xml);
        if (xmlFile == null) {
            return new ErrorVO(ErrorCode.SCHEME_FILE_NO_EXIST);
        } else {
            Set<FlowerType> flowers = null;
            try {
                XmlHelper xmlHelper = xmlFactory.create(dirTo + File.separator
                    + XMLFileName, XmlType.SCHEME);
                xmlHelper.setID(String.valueOf(scheme.getId()));
                xmlHelper.setLocalPath(coverFileName);
                try {
                    xmlHelper.save(dirTo, XMLFileName);
                } catch (Exception e) {
                    return new ErrorVO(ErrorCode.SCHEME_FILE_NO_EXIST);
                }
                sceneID = Long.valueOf(xmlHelper
                    .getTextContent(SCENEID_NODENAME));
                List<XmlParameter> paraList = xmlHelper
                    .getObjectListByNodeName("PatternID2Path", "PatternPath",
                        new XmlParameter());
                if (paraList == null || paraList.isEmpty()) {
                    return new ErrorVO(ErrorCode.SCHEME_XML_ERROR);
                }
                flowers = new HashSet();
                for (XmlParameter item: paraList) {
                    if (item.isMain() == true) {
                        flowerTypeID = item.getID();
                    }
                    /**
                     * 查询花型的权限设置花型和方案的关系表
                     */
                    FlowerType ft = ftManager.getById(account, item.getID());
                    if (ft == null) {
                        return new ErrorVO(
                            ErrorCode.SCHEME_FLOWERTYPE_ID_NO_EXIST_OR_NOPERMISSION);
                    }
                    flowers.add(ft);
                }
            } catch (Exception e) {
                return new ErrorVO(ErrorCode.SCHEME_XML_ERROR);
            }
            xmlFile.setMd5Code(MD5Util.md5(new File(dirTo + File.separator
                + XMLFileName)));
            xmlFile.setStatus(Status.Deleted);
            dataFileManager.save(xmlFile);
            scheme.setFlowers(flowers);
        }

        if (sceneID == null || flowerTypeID == null) {
            return new ErrorVO(ErrorCode.SCHEME_SCENE_NO_EXIST);
        }
        /**
         * 验证场景的归属权以及数据有效性
         */
        Scene scene = sceneManager.getById(account, sceneID);
        if (scene == null) {
            return new ErrorVO(ErrorCode.SCENE_ID_NO_EXIST);
        }

        /**
         * 验证主花型的归属权以及数据有效性
         */
        FlowerType flowerType = ftManager.getById(account, flowerTypeID);
        if (flowerType == null) {
            return new ErrorVO(ErrorCode.SCHEME_MAIN_FLOWER_NO_EXIST);
        }

        coverImage.setStatus(Status.Normal);
        scheme.setCoverImage(coverImage);
        xmlFile.setStatus(Status.Normal);
        scheme.setXmlFile(xmlFile);
        scheme.setScene(scene);
        scheme.setFlowerType(flowerType);
        scheme.setStatus(Status.Normal);
        schemeManager.update(scheme, null);

        SchemeVO schemeVO = new SchemeVO(scheme);

        logger
            .info(
                "Account:{}, method:{}, execute:{}, parameters:[Name={}, description={}, sceneID={}, CoverImageName={}, XmlName={}, MainFlowerTypeID={}], Result={}",
                new Object[] { account.getName(),
                    this.getClass().getSimpleName(), getAction(), name,
                    description, sceneID, coverFileName, XMLFileName,
                    flowerTypeID, schemeVO });

        return schemeVO;
    }

    /**
     * 禁用方案（用于店老板）
     * 
     * @param parameters
     * @return
     */
    public BaseVO SetEnable(WebParameters parameters) {
        Long id = parameters.getLong("ID");
        String srEnable = parameters.getString("Enable");

        boolean enable = Boolean.valueOf(srEnable);
        /**
         * 验证数据的完整性
         */
        if (id == null) {
            return new ErrorVO(ErrorCode.PARAMETER_ID_NO_EXIST);
        }
        Status status;
        if (enable) {
            status = Status.Normal;
        } else {
            status = Status.Disabled;
        }
        if (account.getRule() != UserRule.BUSINESS) {
            return new ErrorVO(ErrorCode.PARAMETER_ERROR);
        }
        boolean result = schemeManager.setStatusByAccount(account, id, status);
        if (result == false) {
            return new ErrorVO(ErrorCode.OPERATOR_ERROR);
        }
        logger.info(
            "Account:{}, method:{}, execute:{}, parameters:[ID={}], Result={}",
            new Object[] { account.getName(), this.getClass().getSimpleName(),
                getAction(), id, "BaseVO" });
        return new BaseVO();

    }

    /**
     * 更新方案的信息
     * 
     * @param parameters
     * @return
     */
    public BaseVO update(WebParameters parameters) {
        Long id = parameters.getLong("ID");
        String name = parameters.getString("Name");
        String description = parameters.getString("Desc");
        String coverFileName = parameters.getString("CoverImageName");
        String XMLFileName = parameters.getString("XmlName");
        Long flowerTypeID = parameters.getLong("MainFlowerTypeID");
        Long sceneID;
        //String coverUUID = parameters.getString("CoverImageUUID");
        //String xmlUUID = parameters.getString("XmlUUID");
        String coverUUID = coverFileName;
        String xmlUUID = XMLFileName;

        /**
         * 验证数据的完整性
         */
        if (id == null) {
            return new ErrorVO(ErrorCode.PARAMETER_ID_NO_EXIST);
        }

        /**
         * 验证权限
         */
        Scheme scheme = schemeManager.getByIdUseOwner(account, id);
        if (scheme == null) {
            return new ErrorVO(ErrorCode.NOPERMIDDION_OR_INVALID_ID);
        }

        /**
         * 验证数据的有效性
         */
        if (name != null && !StringUtils.isEmpty(name)) {
            if (schemeManager.checkByName(name, scheme.getId())) {
                return new ErrorVO(ErrorCode.PARAMETER_NAME_IS_EXIST);
            }
            scheme.setName(name);
        }
        if (description != null && !StringUtils.isEmpty(description)) {
            scheme.setDescription(description);
        }

        //原文件->目标文件
        String dirFrom = LOCAL_PATH + File.separator + TEMP;
        String dirTo = LOCAL_PATH + File.separator
            + String.valueOf(scheme.getId());

        /**
         * 封面图片
         */
        DataFile coverImage = null;
        if (coverFileName != null && !StringUtils.isEmpty(coverFileName)) {
            coverImage = createDataFile(dirFrom, coverFileName, coverUUID,
                dirTo, FileType.Cover_Image);
            if (coverImage == null) {
                return new ErrorVO(ErrorCode.SCHEME_FILE_NO_EXIST);
            } else {
                coverImage.setMd5Code(MD5Util.md5(new File(dirTo
                    + File.separator + coverFileName)));
                coverImage.setStatus(Status.Deleted);
                dataFileManager.save(coverImage);
            }
        }

        /**
         * 修改XML
         */
        DataFile xmlFile = null;
        Set<FlowerType> oldflowers = scheme.getFlowers();
        if (XMLFileName != null && !StringUtils.isEmpty(XMLFileName)) {
            xmlFile = createDataFile(dirFrom, XMLFileName, xmlUUID, dirTo,
                FileType.Xml);
            if (xmlFile == null) {
                return new ErrorVO(ErrorCode.SCHEME_FILE_NO_EXIST);
            } else {
                XmlHelper xmlHelper = xmlFactory.create(dirTo + File.separator
                    + XMLFileName, XmlType.SCHEME);
                xmlHelper.setID(String.valueOf(scheme.getId()));
                xmlHelper.setLocalPath(coverFileName);
                try {
                    xmlHelper.save(dirTo, XMLFileName);
                } catch (Exception e) {
                    return new ErrorVO(ErrorCode.SCHEME_FILE_NO_EXIST);
                }
                sceneID = Long.valueOf(xmlHelper
                    .getTextContent(SCENEID_NODENAME));
                List<XmlParameter> paraList = xmlHelper
                    .getObjectListByNodeName("PatternID2Path", "PatternPath",
                        new XmlParameter());
                Set<FlowerType> flowers = new HashSet();
                for (XmlParameter item: paraList) {
                    if (item.isMain() == true) {
                        flowerTypeID = item.getID();
                    }
                    /**
                     * 查询花型的权限设置花型和方案的关系表
                     */
                    FlowerType ft = ftManager.getById(account, item.getID());
                    if (ft == null) {
                        return new ErrorVO(
                            ErrorCode.SCHEME_FLOWERTYPE_ID_NO_EXIST_OR_NOPERMISSION);
                    }
                    flowers.add(ft);
                }
                xmlFile.setMd5Code(MD5Util.md5(new File(dirTo + File.separator
                    + XMLFileName)));
                xmlFile.setStatus(Status.Deleted);
                dataFileManager.save(xmlFile);
                scheme.setFlowers(flowers);
            }

            if (sceneID == null || flowerTypeID == null) {
                return new ErrorVO(ErrorCode.SCHEME_SCENE_NO_EXIST);
            }
            /**
             * 验证场景的归属权以及数据有效性
             */
            Scene scene = sceneManager.getById(account, sceneID);
            if (scene == null) {
                return new ErrorVO(ErrorCode.SCENE_ID_NO_EXIST);
            }

            /**
             * 验证主花型的归属权以及数据有效性
             */
            FlowerType flowerType = ftManager.getById(account, flowerTypeID);
            if (flowerType == null) {
                return new ErrorVO(ErrorCode.SCHEME_MAIN_FLOWER_NO_EXIST);
            }
            scheme.setScene(scene);
            scheme.setFlowerType(flowerType);
        }

        if (coverImage != null) {
            DataFile coverImageOld = scheme.getCoverImage();
            if (coverImageOld != null) {
                coverImageOld.setStatus(Status.Deleted);
                dataFileManager.update(coverImageOld);
            }
            coverImage.setStatus(Status.Normal);
            scheme.setCoverImage(coverImage);
        }

        if (xmlFile != null) {
            DataFile xmlFileOld = scheme.getXmlFile();
            if (xmlFileOld != null) {
                xmlFileOld.setStatus(Status.Deleted);
                dataFileManager.update(xmlFileOld);
            }
            xmlFile.setStatus(Status.Normal);
            scheme.setXmlFile(xmlFile);
            //递减以前关联的花型,递增现在关联户型
            schemeManager.update(scheme, oldflowers);
        } else {
            //仅仅修改方案的一些属性
            schemeManager.updateWithoutFlower(scheme);
        }

        /**
         * 返回版本号
         */
        SchemeVO schemeVO = new SchemeVO(scheme);
        logger
            .info(
                "Account:{}, method:{}, execute:{}, parameters:[Name={}, description={}, sceneID={}, CoverImageName={}, XmlName={}, MainFlowerTypeID={}], Result={}",
                new Object[] { account.getName(),
                    this.getClass().getSimpleName(), getAction(), name,
                    description, "sceneID", coverFileName, XMLFileName,
                    flowerTypeID, schemeVO });
        return schemeVO;
    }

    /**
     * 读取指定方案的信息
     * 
     * @param parameters
     * @return
     */
    public BaseVO read(WebParameters parameters) {
        Long id = parameters.getLong("ID");
        Scheme scheme = schemeManager.getById(account, id);
        if (scheme == null) {
            return new ErrorVO(ErrorCode.NOPERMIDDION_OR_INVALID_ID);
        }
        SchemeVO schemeVO = new SchemeVO(scheme);
        logger.info(
            "Account:{}, method:{}, execute:{}, parameters:[ID={}], Result={}",
            new Object[] { account.getName(), this.getClass().getSimpleName(),
                getAction(), id, schemeVO });

        return schemeVO;

    }

    /**
     * 刪除指定方案
     * 
     * @param parameters
     * @return
     */
    public BaseVO delete(WebParameters parameters) {
        Long id = parameters.getLong("ID");
        if (id == null) {
            return new ErrorVO(ErrorCode.PARAMETER_ERROR);
        }
        Scheme scheme = schemeManager.getByIdUseOwner(account, id);
        if (scheme == null) {
            return new ErrorVO(ErrorCode.NOPERMIDDION_OR_INVALID_ID);
        }

        /**
         * 删除对应的所有授权表 这里本应该用一个事务处理
         */
        schemeManager.delete(scheme);
        logger.info(
            "Account:{}, method:{}, execute:{}, parameters:[ID={}], Result={}",
            new Object[] { account.getName(), this.getClass().getSimpleName(),
                getAction(), id, "BaseVO" });
        return new BaseVO();
    }
}
