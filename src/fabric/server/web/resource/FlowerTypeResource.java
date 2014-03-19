package fabric.server.web.resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.restlet.representation.Representation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import fabric.common.db.Status;
import fabric.common.utils.AppUtils;
import fabric.common.utils.MD5Util;
import fabric.common.web.ArrayParameters;
import fabric.common.web.BaseVO;
import fabric.common.web.ErrorCode;
import fabric.common.web.ErrorVO;
import fabric.common.web.WebParameters;
import fabric.common.web.XmlHelper;
import fabric.common.web.XmlType;
import fabric.server.entity.DataFile;
import fabric.server.entity.FileType;
import fabric.server.entity.FlowerType;
import fabric.server.entity.FlowerTypeTag;
import fabric.server.entity.FlowerTypeTagType;
import fabric.server.entity.Scheme;
import fabric.server.entity.TagTypeEnum;
import fabric.server.entity.UserRule;
import fabric.server.manager.AccountManager;
import fabric.server.manager.DataFileManager;
import fabric.server.manager.FlowerTypeManager;
import fabric.server.manager.FlowerTypeTagManager;
import fabric.server.manager.FlowerTypeTagTypeManager;
import fabric.server.manager.SchemeManager;
import fabric.server.vo.FlowerTypeVO;
import fabric.server.vo.ListFlowerVO;
import fabric.server.vo.ListVO;
import fabric.server.vo.MapVO;
import fabric.server.vo.StyleVO;
import fabric.server.vo.TagVO;
import fabric.server.vo.VersionVO;

@Controller
@Scope("prototype")
public class FlowerTypeResource extends
    AuthResource<FlowerType, FlowerTypeManager> {

    @Autowired
    private FlowerTypeManager flowerTypeManager;

    @Autowired
    private AccountManager accountManager;

    @Autowired
    private DataFileManager dataFileManager;

    @Autowired
    private FlowerTypeTagManager ftTagManager;

    @Autowired
    private FlowerTypeTagTypeManager ftTagTypeManager;

    @Autowired
    private SchemeManager schemeManager;

    private static String LOCAL_PATH = AppUtils.getUploadPath() + "/FlowerType";

    private final static String TEMP = "temp";

    @Override
    protected FlowerTypeManager getManager() {
        return flowerTypeManager;
    }

    /**
     * 获取花型列表
     * 
     * @return
     */
    public BaseVO list() {
        if (account == null) {
            return new ErrorVO(ErrorCode.ACCOUNT_NO_EXIST);
        }
        ListFlowerVO result = null;
        UserRule rule = account.getRule();
        if (rule == UserRule.BUSINESS) {
            List<Scheme> schemes = schemeManager.getAll(account,
                accountSession.getPower());
            if (schemes == null || schemes.isEmpty()) {
                return new ListVO();
            }
            result = new ListFlowerVO();
            List<FlowerType> curList = new ArrayList();
            for (Scheme item: schemes) {
                Set<FlowerType> flowers = item.getFlowers();
                if (flowers != null && !flowers.isEmpty()) {
                    for (FlowerType ft: flowers) {
                        if (curList.contains(ft)) {
                            continue;
                        } else {
                            curList.add(ft);
                        }
                        FlowerTypeVO ftVO = new FlowerTypeVO(ft);
                        result.add(ftVO);
                    }
                }
            }
            logger
                .info(
                    "Account:{}, method:{}, execute:{}, parameters:[], Result:{}, Size:{}",
                    new Object[] { account.getName(),
                        this.getClass().getSimpleName(), getAction(), "ListVO",
                        curList.size() });
            return result;
        } else {
            List<FlowerType> list = flowerTypeManager.getAll(account);
            if (list == null || list.isEmpty()) {
                return new ListVO();
            }

            result = new ListFlowerVO();
            for (FlowerType ft: list) {
                FlowerTypeVO ftVO = new FlowerTypeVO(ft);
                result.add(ftVO);
            }
            logger
                .info(
                    "Account:{}, method:{}, execute:{}, parameters:[], Result:{}, Size:{}",
                    new Object[] { account.getName(),
                        this.getClass().getSimpleName(), getAction(), "ListVO",
                        list.size() });
            return result;
        }
    }

    /**
     * 版本同步
     * 
     * @return
     */
    public BaseVO refresh() {
        if (account == null) {
            return new ErrorVO(ErrorCode.ACCOUNT_NO_EXIST);
        }
        ListVO result = null;
        UserRule rule = account.getRule();
        if (rule == UserRule.BUSINESS) {
            List<Scheme> schemes = schemeManager.getAll(account,
                accountSession.getPower());
            if (schemes == null || schemes.isEmpty()) {
                return new ListVO();
            }
            result = new ListVO<VersionVO>();
            for (Scheme item: schemes) {
                Set<FlowerType> flowers = item.getFlowers();
                if (flowers != null && !flowers.isEmpty()) {
                    for (FlowerType ft: flowers) {
                        VersionVO versionVO = new VersionVO(ft);
                        result.add(versionVO);
                    }
                }
            }
            logger.info(
                "Account:{}, method:{}, execute:{}, parameters:[], Result:{}",
                new Object[] { account.getName(),
                    this.getClass().getSimpleName(), getAction(), "ListVO" });
            return result;
        } else {
            List<FlowerType> list = flowerTypeManager.getAll(account);
            if (list == null || list.isEmpty()) {
                return new ListVO();
            }
            result = new ListVO<VersionVO>();
            for (FlowerType ft: list) {
                VersionVO versionVO = new VersionVO(ft);
                result.add(versionVO);
            }
            logger.info(
                "Account:{}, method:{}, execute:{}, parameters:[], Result:{}",
                new Object[] { account.getName(),
                    this.getClass().getSimpleName(), getAction(), "ListVO" });
            return result;
        }
    }

    /**
     * 添加花型
     * 
     * @param parameters
     * @return
     */
    public BaseVO add(WebParameters parameters) {
        String name = parameters.getString("FlowerTypeName");
        String description = parameters.getString("Description");
        String coverFileName = parameters.getString("CoverFileName");
        String printFileName = parameters.getString("PrintFileName");
        String XMLFileName = parameters.getString("XMLFileName");

        /**
         * 客户端不愿意改，暂时用FileName来传递UUID
         */
        //String coverUUID = parameters.getString("CoverFileUUID");
        //String printUUID = parameters.getString("PrintFileUUID");
        //String xmlUUID = parameters.getString("XMLFileUUID");
        String coverUUID = coverFileName;
        String printUUID = printFileName;
        String xmlUUID = XMLFileName;

        ArrayParameters Styles = webParametersFactory.createArrayParameters(
            parameters, "StyleIDs");
        ArrayParameters Colors = webParametersFactory.createArrayParameters(
            parameters, "ColorIDs");
        ArrayParameters Elements = webParametersFactory.createArrayParameters(
            parameters, "ElementIDs");
        ArrayParameters Recommends = webParametersFactory
            .createArrayParameters(parameters, "RecommendIDs");
        ArrayParameters Products = webParametersFactory.createArrayParameters(
            parameters, "ProductIDs");


        /**
         * 验证数据的完整性
         */
        if (name == null || StringUtils.isEmpty(name)
            //|| ftStyleName == null|| StringUtils.isEmpty(ftStyleName) 
            || coverFileName == null || StringUtils.isEmpty(coverFileName)
            || XMLFileName == null || StringUtils.isEmpty(XMLFileName)) {
            return new ErrorVO(ErrorCode.PARAMETER_ERROR);
        }

        if (coverUUID == null || coverUUID.isEmpty() || xmlUUID == null
            || xmlUUID.isEmpty()) {
            return new ErrorVO(ErrorCode.UUID_NO_FOUND);
        }

        if (Styles == null || Styles.length() == 0 || Colors == null
            || Colors.length() == 0 || Elements == null
            || Elements.length() == 0 || Recommends == null
            || Recommends.length() == 0 || Products == null
            || Products.length() == 0) {
            return new ErrorVO(ErrorCode.FLOWERTYPE_TAG_LOSE);
        }

        /**
         * 花型名查重
         */
        if (flowerTypeManager.checkByName(name)) {
            return new ErrorVO(ErrorCode.PARAMETER_NAME_IS_EXIST);
        }

        /**
         * 添加花型标签
         */
        Set<FlowerTypeTag> tags = new HashSet();
        if (attachTags(tags, Styles, TagTypeEnum.STYLE) == false) {
            return new ErrorVO(ErrorCode.FLOWERTYPE_TAG_STYLE_NO_EXIST);
        }
        if (attachTags(tags, Colors, TagTypeEnum.COLOR) == false) {
            return new ErrorVO(ErrorCode.FLOWERTYPE_TAG_COLOR_NO_EXIST);
        }
        if (attachTags(tags, Recommends, TagTypeEnum.RECOMMEND) == false) {
            return new ErrorVO(ErrorCode.FLOWERTYPE_TAG_RECOMMEND_NO_EXIST);
        }
        if (attachTags(tags, Elements, TagTypeEnum.ElEMENTS) == false) {
            return new ErrorVO(ErrorCode.FLOWERTYPE_TAG_ElEMENTS_NO_EXIST);
        }
        if (attachTags(tags, Products, TagTypeEnum.PRODUCT) == false) {
            return new ErrorVO(ErrorCode.FLOWERTYPE_TAG_PRODUCTS_NO_EXIST);
        }

        FlowerType ft = new FlowerType();
        ft.setName(name);
        ft.setDescription(description);
        ft.setOwner(account);
        ft.setTags(tags);
        ft.setStatus(Status.Deleted);
        flowerTypeManager.save(ft);


        //原文件->目标文件
        String dirFrom = LOCAL_PATH + File.separator + TEMP;
        String dirTo = LOCAL_PATH + File.separator + String.valueOf(ft.getId());
        /**
         * 验证文件有效性
         */
        //封面图
        DataFile coverImage = createDataFile(dirFrom, coverFileName, coverUUID,
            dirTo, FileType.Cover_Image);
        if (coverImage == null) {
            return new ErrorVO(ErrorCode.FLOWERTYPE_FILE_NO_EXIST);
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
            return new ErrorVO(ErrorCode.FLOWERTYPE_FILE_NO_EXIST);
        } else {
            XmlHelper xmlHelper = xmlFactory.create(dirTo + File.separator
                + XMLFileName, XmlType.FLOWERTYPE);
            xmlHelper.setID(String.valueOf(ft.getId()));
            xmlHelper.setLocalPath(XMLFileName);
            try {
                xmlHelper.save(dirTo, XMLFileName);
            } catch (Exception e) {
                return new ErrorVO(ErrorCode.FLOWERTYPE_FILE_NO_EXIST);
            }
            xmlFile.setMd5Code(MD5Util.md5(new File(dirTo + File.separator
                + XMLFileName)));
            xmlFile.setStatus(Status.Deleted);
            dataFileManager.save(xmlFile);
        }

        //打印图
        if (printFileName != null && !printFileName.isEmpty()) {
            DataFile printImage = createDataFile(dirFrom, printFileName,
                printUUID, dirTo, FileType.Print_Image);
            if (printImage == null) {
                return new ErrorVO(ErrorCode.FLOWERTYPE_FILE_NO_EXIST);
            } else {
                printImage.setMd5Code(MD5Util.md5(new File(dirTo
                    + File.separator + printFileName)));
                dataFileManager.save(printImage);
                ft.setPrintImage(printImage);

            }
        }

        coverImage.setStatus(Status.Normal);
        ft.setCoverImage(coverImage);
        xmlFile.setStatus(Status.Normal);
        ft.setXmlFile(xmlFile);
        ft.setStatus(Status.Normal);
        flowerTypeManager.update(ft);

        FlowerTypeVO ftVO = new FlowerTypeVO(ft);

        logger
            .info(
                "Account:{}, method:{}, execute:{}, parameters:[name={}, description={},coverFileName={},printFileName={},XMLFileName={}], result={}",
                new Object[] { account.getName(),
                    this.getClass().getSimpleName(), getAction(), name,
                    description, coverFileName, printFileName, XMLFileName,
                    ft.getId() });

        return ftVO;
    }

    /**
     * 读取特定ID的花型
     * 
     * @param parameters
     * @return
     */
    public BaseVO read(WebParameters parameters) {
        Long id = parameters.getLong("FlowerTypeID");
        if (id == null) {
            return new ErrorVO(ErrorCode.PARAMETER_ERROR);
        }
        Long startTime = System.currentTimeMillis();

        FlowerType flowerType = flowerTypeManager.getById(account, id);
        if (flowerType == null) {
            return new ErrorVO(ErrorCode.PARAMETER_ID_NO_EXIST);
        }
        Long endTime = System.currentTimeMillis();

        System.out.println("Cost:" + String.valueOf(endTime - startTime)
            + "ms.");
        FlowerTypeVO ftVO = new FlowerTypeVO(flowerType);

        logger
            .info(
                "Account:{}, method:{}, execute:{}, parameters:[FlowerTypeID={}], result={}",
                new Object[] { account.getName(),
                    this.getClass().getSimpleName(), getAction(),
                    flowerType.getId(), flowerType.getId() });

        return ftVO;
    }

    /**
     * 更新花型的信息
     * 
     * @param parameters
     * @return
     */
    public BaseVO update(WebParameters parameters) {
        Long id = parameters.getLong("FlowerTypeID");
        String name = parameters.getString("FlowerTypeName");
        String description = parameters.getString("Description");
        String coverFileName = parameters.getString("CoverFileName");
        String printFileName = parameters.getString("PrintFileName");
        String XMLFileName = parameters.getString("XMLFileName");

        ArrayParameters Styles = webParametersFactory.createArrayParameters(
            parameters, "StyleIDs");
        ArrayParameters Colors = webParametersFactory.createArrayParameters(
            parameters, "ColorIDs");
        ArrayParameters Elements = webParametersFactory.createArrayParameters(
            parameters, "ElementIDs");
        ArrayParameters Recommends = webParametersFactory
            .createArrayParameters(parameters, "RecommendIDs");
        ArrayParameters Products = webParametersFactory.createArrayParameters(
            parameters, "ProductIDs");
        /**
         * 检查数据完整性
         */
        if (id == null || name == null || StringUtils.isEmpty(name)) {
            return new ErrorVO(ErrorCode.PARAMETER_ERROR);
        }

        /**
         * 检查数据有效性
         */
        FlowerType ft = flowerTypeManager.getByIdUseOwner(account, id);
        if (ft == null)
            return new ErrorVO(ErrorCode.NOPERMIDDION_OR_INVALID_ID);
        if (flowerTypeManager.checkByName(name, ft.getId())) {
            return new ErrorVO(ErrorCode.PARAMETER_NAME_IS_EXIST);
        }

        ft.setName(name);
        ft.setDescription(description);

        /**
         * 更改标签
         */
        Set<FlowerTypeTag> tags = new HashSet();
        if (attachTags(tags, Styles, TagTypeEnum.STYLE) == false) {
            return new ErrorVO(ErrorCode.FLOWERTYPE_TAG_STYLE_NO_EXIST);
        }
        if (attachTags(tags, Colors, TagTypeEnum.COLOR) == false) {
            return new ErrorVO(ErrorCode.FLOWERTYPE_TAG_COLOR_NO_EXIST);
        }
        if (attachTags(tags, Recommends, TagTypeEnum.RECOMMEND) == false) {
            return new ErrorVO(ErrorCode.FLOWERTYPE_TAG_RECOMMEND_NO_EXIST);
        }
        if (attachTags(tags, Elements, TagTypeEnum.ElEMENTS) == false) {
            return new ErrorVO(ErrorCode.FLOWERTYPE_TAG_ElEMENTS_NO_EXIST);
        }
        if (attachTags(tags, Products, TagTypeEnum.PRODUCT) == false) {
            return new ErrorVO(ErrorCode.FLOWERTYPE_TAG_PRODUCTS_NO_EXIST);
        }

        flowerTypeManager.update(account, tags, ft);

        MapVO mapVO = new MapVO();
        mapVO.put("Version", ft.getVersion());
        mapVO.put("Result", true);

        logger
            .info(
                "Account:{}, method:{}, execute:{}, parameters:[FlowerTypeID={},FlowerTypeName={},Description={}], result={}",
                new Object[] { account.getName(),
                    this.getClass().getSimpleName(), getAction(), id, name,
                    description, ft.getId() });
        return mapVO;
    }

    public BaseVO updateFile(WebParameters parameters) {
        Long id = parameters.getLong("FlowerTypeID");
        String name = parameters.getString("FlowerTypeName");
        String description = parameters.getString("Description");
        String coverFileName = parameters.getString("CoverFileName");
        String printFileName = parameters.getString("PrintFileName");
        String XMLFileName = parameters.getString("XMLFileName");

        /**
         * 客户端不愿意改，暂时用FileName来传递UUID
         */
        //String coverUUID = parameters.getString("CoverFileUUID");
        //String printUUID = parameters.getString("PrintFileUUID");
        //String xmlUUID = parameters.getString("XMLFileUUID");
        String coverUUID = coverFileName;
        String printUUID = printFileName;
        String xmlUUID = XMLFileName;

        ArrayParameters Styles = webParametersFactory.createArrayParameters(
            parameters, "StyleIDs");
        ArrayParameters Colors = webParametersFactory.createArrayParameters(
            parameters, "ColorIDs");
        ArrayParameters Elements = webParametersFactory.createArrayParameters(
            parameters, "ElementIDs");
        ArrayParameters Recommends = webParametersFactory
            .createArrayParameters(parameters, "RecommendIDs");
        ArrayParameters Products = webParametersFactory.createArrayParameters(
            parameters, "ProductIDs");
        /**
         * 检查数据完整性
         */
        if (id == null || name == null || StringUtils.isEmpty(name)) {
            return new ErrorVO(ErrorCode.PARAMETER_ERROR);
        }

        /**
         * 检查数据有效性
         */
        FlowerType ft = flowerTypeManager.getByIdUseOwner(account, id);
        if (ft == null)
            return new ErrorVO(ErrorCode.NOPERMIDDION_OR_INVALID_ID);
        if (flowerTypeManager.checkByName(name, ft.getId())) {
            return new ErrorVO(ErrorCode.PARAMETER_NAME_IS_EXIST);
        }

        ft.setName(name);
        ft.setDescription(description);

        /**
         * 更改标签
         */
        Set<FlowerTypeTag> tags = new HashSet();
        if (attachTags(tags, Styles, TagTypeEnum.STYLE) == false) {
            return new ErrorVO(ErrorCode.FLOWERTYPE_TAG_STYLE_NO_EXIST);
        }
        if (attachTags(tags, Colors, TagTypeEnum.COLOR) == false) {
            return new ErrorVO(ErrorCode.FLOWERTYPE_TAG_COLOR_NO_EXIST);
        }
        if (attachTags(tags, Recommends, TagTypeEnum.RECOMMEND) == false) {
            return new ErrorVO(ErrorCode.FLOWERTYPE_TAG_RECOMMEND_NO_EXIST);
        }
        if (attachTags(tags, Elements, TagTypeEnum.ElEMENTS) == false) {
            return new ErrorVO(ErrorCode.FLOWERTYPE_TAG_ElEMENTS_NO_EXIST);
        }
        if (attachTags(tags, Products, TagTypeEnum.PRODUCT) == false) {
            return new ErrorVO(ErrorCode.FLOWERTYPE_TAG_PRODUCTS_NO_EXIST);
        }
        //原文件->目标文件
        String dirFrom = LOCAL_PATH + File.separator + TEMP;
        String dirTo = LOCAL_PATH + File.separator + String.valueOf(ft.getId());
        /**
         * 验证文件有效性
         */
        //封面图
        if (coverFileName != null && !coverFileName.isEmpty()) {
            DataFile coverImage = createDataFile(dirFrom, coverFileName,
                coverUUID, dirTo, FileType.Cover_Image);
            if (coverImage == null) {
                return new ErrorVO(ErrorCode.FLOWERTYPE_FILE_NO_EXIST);
            } else {
                coverImage.setMd5Code(MD5Util.md5(new File(dirTo
                    + File.separator + coverFileName)));
                dataFileManager.save(coverImage);
                ft.setCoverImage(coverImage);
            }
        }

        //XML
        DataFile xmlFile = createDataFile(dirFrom, XMLFileName, xmlUUID, dirTo,
            FileType.Xml);
        if (xmlFile == null) {
            return new ErrorVO(ErrorCode.FLOWERTYPE_FILE_NO_EXIST);
        } else {
            XmlHelper xmlHelper = xmlFactory.create(dirTo + File.separator
                + XMLFileName, XmlType.FLOWERTYPE);
            xmlHelper.setID(String.valueOf(ft.getId()));
            xmlHelper.setLocalPath(XMLFileName);
            try {
                xmlHelper.save(dirTo, XMLFileName);
            } catch (Exception e) {
                return new ErrorVO(ErrorCode.FLOWERTYPE_FILE_NO_EXIST);
            }
            xmlFile.setMd5Code(MD5Util.md5(new File(dirTo + File.separator
                + XMLFileName)));
            dataFileManager.save(xmlFile);
            ft.setXmlFile(xmlFile);
        }

        //打印图
        if (printFileName != null && !printFileName.isEmpty()) {
            DataFile printImage = createDataFile(dirFrom, printFileName,
                printUUID, dirTo, FileType.Print_Image);
            if (printImage == null) {
                return new ErrorVO(ErrorCode.FLOWERTYPE_FILE_NO_EXIST);
            } else {
                printImage.setMd5Code(MD5Util.md5(new File(dirTo
                    + File.separator + printFileName)));
                dataFileManager.save(printImage);
                ft.setPrintImage(printImage);

            }
        }
        flowerTypeManager.update(account, tags, ft);

        MapVO mapVO = new MapVO();
        mapVO.put("Version", ft.getVersion());
        mapVO.put("Result", true);

        logger
            .info(
                "Account:{}, method:{}, execute:{}, parameters:[FlowerTypeID={},FlowerTypeName={},Description={}], result={}",
                new Object[] { account.getName(),
                    this.getClass().getSimpleName(), getAction(), id, name,
                    description, ft.getId() });
        return mapVO;
    }

    /**
     * 禁用/启用 花型
     * 
     * @param parameters
     * @return
     */
    public BaseVO setEnable(WebParameters parameters) {
        Long id = parameters.getLong("FlowerTypeID");
        String srenable = parameters.getString("Enable");

        if (id == null || StringUtils.isEmpty(srenable)) {
            return new ErrorVO(ErrorCode.PARAMETER_ERROR);
        }
        boolean enable = Boolean.parseBoolean(srenable);
        FlowerType flowerType = flowerTypeManager.getByIdUseOwner(account, id);
        if (id == null || flowerType == null)
            return new ErrorVO(ErrorCode.NOPERMIDDION_OR_INVALID_ID);
        if (enable)
            flowerType.setStatus(Status.Normal);
        else
            flowerType.setStatus(Status.Disabled);

        /**
         * 只有作者才可以修改自己的作品
         */
        if (account.getId() != flowerType.getOwner().getId()) {
            return new ErrorVO(ErrorCode.OWNER_ERROR);
        } else {
            flowerTypeManager.update(account, flowerType);
        }

        MapVO mapVO = new MapVO();
        mapVO.put("Result", true);

        logger
            .info(
                "Account:{}, method:{}, execute:{}, parameters:[FlowerTypeID={},Enable={}], result={}",
                new Object[] { account.getName(),
                    this.getClass().getSimpleName(), getAction(),
                    flowerType.getId(), srenable, true });

        return mapVO;
    }

    public BaseVO delete(WebParameters parameters) {
        Long id = parameters.getLong("FlowerTypeID");
        if (id == null) {
            return new ErrorVO(ErrorCode.PARAMETER_ID_NO_EXIST);
        }
        FlowerType flowerType = flowerTypeManager.getById(account, id);
        if (flowerType == null) {
            return new ErrorVO(ErrorCode.PARAMETER_ID_NO_EXIST);
        }
        List<Scheme> schemes = schemeManager.getAllByFlowerTypeId(flowerType
            .getId());
        if (schemes == null || schemes.isEmpty()) {
            flowerTypeManager.delete(flowerType);
            return new BaseVO();
        } else {
            return new ErrorVO(ErrorCode.DELETE_REJECT);
        }
    }

    /**
     * 获取花型标签列表
     * 
     * @param parameters
     * @return
     */
    public BaseVO listTag(WebParameters parameters) {
        String tagTypeName = parameters.getString("TagTypeName");
        TagTypeEnum tagType = TagTypeEnum.valueof(tagTypeName);
        if (tagType == null) {
            List<FlowerTypeTag> list = ftTagManager.getAll();
            if (list == null || list.isEmpty()) {
                return new ListVO();
            }
            ListVO result = new ListVO();
            for (FlowerTypeTag item: list) {
                TagVO tagVO = new TagVO(item);
                result.add(tagVO);
            }
            return result;
        }

        logger
            .info(
                "Account:{}, method:{}, execute:{}, parameters:[TagTypeName={}], result={}",
                new Object[] { account.getName(),
                    this.getClass().getSimpleName(), getAction(), tagTypeName,
                    tagType.getName() });
        return listTag(tagType.getName());
    }

    /**
     * 版本同步
     * 
     * @param parameters
     * @return
     */
    public BaseVO refreshTag(WebParameters parameters) {
        String tagTypeName = parameters.getString("TagTypeName");
        TagTypeEnum tagType = TagTypeEnum.valueof(tagTypeName);
        List<FlowerTypeTag> list = ftTagManager.getAllByTagTypeName(tagType
            .getName());
        if (list == null || list.isEmpty()) {
            return new ListVO();
        }
        ListVO result = new ListVO();
        for (FlowerTypeTag item: list) {
            TagVO tagVO = new TagVO(item);
            result.add(tagVO);
        }

        logger
            .info(
                "Account:{}, method:{}, execute:{}, parameters:[TagTypeName={}], result={}",
                new Object[] { account.getName(),
                    this.getClass().getSimpleName(), getAction(), tagTypeName,
                    result });
        return result;
    }

    /**
     * 添加花型标签
     * 
     * @param parameters
     * @return
     */
    public BaseVO addTag(WebParameters parameters) {
        String tagTypeName = parameters.getString("TagTypeName");
        String tagName = parameters.getString("Name");
        TagTypeEnum tagType = TagTypeEnum.valueof(tagTypeName);

        logger
            .info(
                "Account:{}, method:{}, execute:{}, parameters:[TagTypeName={}, TagName={}], Result={}",
                new Object[] { account.getName(),
                    this.getClass().getSimpleName(), getAction(), tagTypeName,
                    tagName, "BaseVO" });

        return addTag(tagName, tagType.getName());
    }

    /**
     * 删除花型标签
     * 
     * @param parameters
     * @return
     */
    public BaseVO deleteTag(WebParameters parameters) {
        Long id = parameters.getLong("ID");
        if (id == null) {
            return new ErrorVO(ErrorCode.PARAMETER_ID_NO_EXIST);
        }
        FlowerTypeTag tag = ftTagManager.getByTagTypeAndTagId(null, id);
        if (tag != null) {
            List<FlowerType> list = flowerTypeManager.getAllByTag(tag);
            if (list == null || list.isEmpty()) {
                ftTagManager.deleteById(id);
            } else {
                return new ErrorVO(ErrorCode.DELETE_REJECT);
            }
        } else {
            return new ErrorVO(ErrorCode.FLOWERTYPE_TAG_NAME_NO_EXIST);
        }

        logger
            .info(
                "Account:{}, method:{}, execute:{}, parameters:[TagID={}], Result={}",
                new Object[] { account.getName(),
                    this.getClass().getSimpleName(), getAction(), id, "BaseVO" });
        return new BaseVO();
    }

    /**
     * 获取所有花型标签类型
     * 
     * @return
     */
    public BaseVO listType() {
        return listType(new String(""));
    }

    public BaseVO listType(WebParameters parameters) {
        return listType(new String(""));
    }

    private BaseVO listType(String unuse) {
        List<FlowerTypeTagType> list = ftTagTypeManager.getAll();
        if (list == null || list.isEmpty()) {
            return new ListVO();
        }
        ListVO result = new ListVO();
        for (FlowerTypeTagType item: list) {
            StyleVO tagTypeVO = new StyleVO(item);
            result.add(tagTypeVO);
        }

        logger.info(
            "Account:{}, method:{}, execute:{}, parameters:[], Result={}",
            new Object[] { account.getName(), this.getClass().getSimpleName(),
                getAction(), result });
        return result;
    }

    /**
     * 获取某花型标签类型的标签列表
     * 
     * @param tagTypeName
     * @return
     */
    private BaseVO listTag(String tagTypeName) {
        List<FlowerTypeTag> list = ftTagManager
            .getAllByTagTypeName(tagTypeName);
        if (list == null || list.isEmpty()) {
            return new ListVO();
        }
        ListVO result = new ListVO();
        for (FlowerTypeTag item: list) {
            TagVO tagVO = new TagVO(item);
            result.add(tagVO);
        }
        return result;
    }

    /**
     * 添加花型标签到指定花型标签类型
     * 
     * @param tagName
     * @param tagTypeName
     * @return
     */
    private BaseVO addTag(String tagName, String tagTypeName) {
        
        //1: 判断字符串的有效性
        //2: 获取类型标签
        //3：直接save实体,然后返回信息.
        
        if (tagName == null || StringUtils.isEmpty(tagName)
            || tagTypeName == null || StringUtils.isEmpty(tagTypeName)) {
            return new ErrorVO(ErrorCode.PARAMETER_ERROR);
        }
        FlowerTypeTag tag = ftTagManager.getByTagTypeAndTagName(tagTypeName,
            tagName);
        if (tag != null) {
            return new ErrorVO(ErrorCode.FLOWERTYPE_TAG_NAME_IS_EXIST);
        }
        FlowerTypeTagType tagType = ftTagTypeManager.getByName(tagTypeName);
        if (tagType == null) {
            return new ErrorVO(ErrorCode.FLOWERTYPE_TAG_TYPE_NO_EXIST);
        }
        tag = new FlowerTypeTag();
        tag.setName(tagName);
        tag.setTagType(tagType);
        ftTagManager.save(tag);

        MapVO mapVO = new MapVO();
        mapVO.put("ID", tag.getId());
        return mapVO;
    }

    /**
     * 获取指定花型标签类型
     * 
     * @param tags
     * @param parameters
     * @param tagType
     */
    private boolean attachTags(Set<FlowerTypeTag> tags,
        ArrayParameters parameters, TagTypeEnum tagType) {
        if (parameters == null) {
            return false;
        }
        int length = parameters.length();
        for (int index = 0; index < length; ++index) {
            Long id = parameters.getLong(index);
            FlowerTypeTag tag = ftTagManager.getByTagTypeAndTagId(
                tagType.getName(), id);
            if (tag == null) {
                return false;
            }
            tags.add(tag);
        }
        return true;
    }

}
