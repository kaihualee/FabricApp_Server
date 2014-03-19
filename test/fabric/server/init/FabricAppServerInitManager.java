/**
 * @(#)FabricAppServerInitManager.java, 2013-7-8. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.server.init;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fabric.common.db.Status;
import fabric.common.utils.MD5Util;
import fabric.common.web.XmlFactory;
import fabric.common.web.XmlHelper;
import fabric.common.web.XmlType;
import fabric.server.entity.Account;
import fabric.server.entity.DataFile;
import fabric.server.entity.FileType;
import fabric.server.entity.FlowerType;
import fabric.server.entity.FlowerTypeTag;
import fabric.server.entity.FlowerTypeTagType;
import fabric.server.entity.Scene;
import fabric.server.entity.ScenePosition;
import fabric.server.entity.SceneStyle;
import fabric.server.entity.Scheme;
import fabric.server.entity.Shop;
import fabric.server.entity.TagTypeEnum;
import fabric.server.entity.UserRule;
import fabric.server.manager.AccountManager;
import fabric.server.manager.DataFileManager;
import fabric.server.manager.FlowerTypeManager;
import fabric.server.manager.FlowerTypeTagManager;
import fabric.server.manager.FlowerTypeTagTypeManager;
import fabric.server.manager.SceneManager;
import fabric.server.manager.ScenePositionManager;
import fabric.server.manager.SceneStyleManager;
import fabric.server.manager.SchemeManager;
import fabric.server.manager.ShopManager;

/**
 * 初始化数据
 * 
 * @author nisonghai
 */
@Controller
public class FabricAppServerInitManager implements InitializingBean {

    private static String scenePath = "webapp/downloads/Scene";

    private static String flowerTypePath = "webapp/downloads/FlowerType";

    private static String schemePath = "webapp/downloads/Scheme";

    private static Random rand = new Random();

    private DocumentBuilder documentBuilder;

    @Autowired
    private AccountManager accountManager;

    @Autowired
    private SceneManager sceneManager;

    @Autowired
    private ScenePositionManager scenePositionManager;

    @Autowired
    private SceneStyleManager sceneStyleManager;

    @Autowired
    private FlowerTypeManager flowerTypeManager;

    @Autowired
    private FlowerTypeTagTypeManager ftTagTypeManager;

    @Autowired
    private FlowerTypeTagManager ftTagManager;

    @Autowired
    private ShopManager shopManager;

    @Autowired
    private SchemeManager schemeManager;

    @Autowired
    private DataFileManager dataFileManager;

    @Autowired
    private XmlFactory xmlFactory;

    @Override
    public void afterPropertiesSet() throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        this.documentBuilder = dbf.newDocumentBuilder();
    }

    public void initAccount() throws Exception {
        createAccountForAdmin();
        createDesigner();
        createBosser();

    }

    /**
     * 创建超级管理员
     */
    private void createAccountForAdmin() {
        Account account = new Account();
        account.setName("admin");
        account.setPassword(MD5Util.md5("admin"));
        account.setRealname("admin");
        account.setRule(UserRule.SUPER_ADMIN);

        if (accountManager.getAccountByName(UserRule.SUPER_ADMIN,
            account.getName()) == null) {
            accountManager.save(account);
        }
    }

    /**
     * 创建设计师账户
     */
    private void createDesigner() {
        Account account = new Account();
        account.setName("designer");
        account.setPassword(MD5Util.md5("123456"));
        account.setRealname("designer");
        account.setRule(UserRule.DESIGNER);

        if (accountManager.getAccountByName(UserRule.DESIGNER,
            account.getName()) == null) {
            accountManager.save(account);
        }
    }

    private void createBosser() {
        Account account = new Account();
        account.setName("bosser");
        account.setPassword(MD5Util.md5("123456"));
        account.setRealname("bosser");
        account.setRule(UserRule.BUSINESS);

        Shop shop = new Shop();
        shop.setName("富安娜家纺");
        shop.setAddress("北京xxx路xxx号");
        shop.setPhone("010-444444");
        shop = shopManager.saveByName(shop);

        account.setShop(shop);

        if (accountManager.getAccountByName(UserRule.BUSINESS,
            account.getName()) == null) {
            accountManager.save(account);
        }
    }

    /**
     * 创建设计师
     * 
     * @param startName
     *            设计师名称
     * @param size
     *            创建个数
     */
    public void createAccountForDesigner(String ruleName, String startName,
        int size) {
        for (int i = 1; i <= size; i++) {
            Account account = new Account();
            account.setName(startName + i);
            account.setPassword(MD5Util.md5("123456"));
            account.setRealname(startName + i);
            UserRule rule = UserRule.valueOf(ruleName.toUpperCase());
            account.setRule(rule);
            if (rule == UserRule.BUSINESS) {
                Shop shop = new Shop();
                shop.setName("店名-" + account.getName());
                account.setShop(shop);
            }

            accountManager.save(account);
        }
    }

    /**
     * 初始化场景位置
     * 
     * @throws Exception
     */
    public void initScenePosition() throws Exception {
        String[] names = { "客厅", "卧室" };
        for (String name: names) {
            ScenePosition pos = new ScenePosition();
            pos.setName(name);
            scenePositionManager.saveByName(pos);
        }
    }

    /**
     * 初始化场景风格
     * 
     * @throws Exception
     */
    public void initSceneStyle() throws Exception {
        String[] names = { "中式", "西式", "现代" };
        for (String name: names) {
            SceneStyle style = new SceneStyle();
            style.setName(name);
            sceneStyleManager.saveByName(style);
        }
    }

    /**
     * 初始化场景
     * 
     * @throws Exception
     */
    public void createScene(String path) throws Exception {

        File sceneRootDir = new File(path);
        for (File positionDir: sceneRootDir.listFiles()) {
            String positionName = positionDir.getName();
            if (!positionDir.isDirectory()) {
                continue;
            }
            for (File styleDir: positionDir.listFiles()) {
                if (!styleDir.isDirectory()) {
                    continue;
                }
                String styleName = styleDir.getName();
                for (File sceneDir: styleDir.listFiles()) {
                    String sceneName = sceneDir.getName();
                    if (!sceneDir.isDirectory()) {
                        continue;
                    }
                    createScene(sceneDir, positionName, styleName, sceneName);
                }
            }
        }
    }

    public void addScene(String path) throws Exception {
        createScene(path);
    }

    public void addFlowerRefTimes() {
        List<FlowerType> flowers = flowerTypeManager.getAllByStatus(
            Status.Normal, Status.Disabled);
        if (flowers == null || flowers.isEmpty()) {
            System.out.println("Empty FLowerTypes set.");
            return;
        }
        for (FlowerType item: flowers) {
            List<Scheme> schemes = schemeManager.getAllByFlowerTypeId(item
                .getId());
            item.setReferences(new Long(schemes.size()));
            flowerTypeManager.update(item);
        }
    }

    /**
     * 读取场景数据初始化DB
     * 
     * @param sceneDir
     * @throws Exception
     */
    private void createScene(File sceneDir, String positionName,
        String styleName, String sceneName) throws Exception {
        Account admin = accountManager.getAccountByName(UserRule.SUPER_ADMIN,
            "admin");
        File[] fileList = sceneDir.listFiles();
        Scene scene = sceneManager.getByName(admin.getId(), sceneName);
        if (scene == null) {
            scene = new Scene();
        } else {
            System.out.println(sceneName + " is existed");
            return;
        }
        File xmlFile = null;
        File coverImage = null;
        File cabFile = null;
        for (File file: fileList) {
            if (file.isDirectory()) {
                continue;
            }
            String fileName = file.getName();
            String fileType = fileName.substring(fileName.lastIndexOf('.') + 1)
                .toLowerCase();
            if (fileType.equals("xml")) {
                SceneXmlData xmlData = parserXml(file, "ATSCENE");
                scene.setName(xmlData.getName());
                scene.setName(sceneName);
                scene.setDescription(xmlData.getDescription());
                xmlFile = file;
            }
            if (fileType.equals("jpg")) {
                coverImage = file;
            }
            if (fileType.equals("cab")) {
                cabFile = file;
            }
        }

        scene.setOwner(admin);
        ScenePosition scenePos = scenePositionManager.getByName(positionName);
        scene.setScenePos(scenePos);
        SceneStyle sceneStyle = sceneStyleManager.getByName(styleName);
        scene.setSceneStyle(sceneStyle);
        if (xmlFile == null || coverImage == null || cabFile == null) {
            throw new Exception("场景缺少数据");
        }

        sceneManager.saveOrUpdate(scene);

        String path = scenePath + File.separator + scene.getId();
        DataFile dataFile = copyXmlFile(xmlFile, scene.getId(), path, "ATSCENE");
        scene.setXmlFile(dataFile);
        dataFile = copyFile(cabFile, path, FileType.Cab);
        scene.setCab(dataFile);
        dataFile = copyFile(coverImage, path, FileType.Cover_Image);
        scene.setCoverImage(dataFile);

        // 存放文件
        sceneManager.update(scene);
    }

    /**
     * 初始化花型标签
     */
    public void initFlowerTag() {
        createFlowerTag(TagTypeEnum.STYLE.getName(), new String[] { "欧式", "中式",
            "简约", "田园", "婚庆" });
        createFlowerTag(TagTypeEnum.ElEMENTS.getName(), new String[] { "花鸟",
            "抽象", "条纹" });
        createFlowerTag(TagTypeEnum.COLOR.getName(), new String[] { "红#-65536",
            "黄#-256", "蓝#-16711681", "绿#-16711936", "青#-16760704",
            "紫#-8388353", "黑#-16777216", "白#-1" });
        createFlowerTag(TagTypeEnum.RECOMMEND.getName(), new String[] { "流行",
            "特色", "新品" });

        createFlowerTag(TagTypeEnum.PRODUCT.getName(), new String[] { "床品",
            "枕头", "窗帘", "沙发", "靠枕", "地毯", "装饰画", "提花", "绣花", "装饰布纹理" });
    }

    public void initProductTag() {
        createFlowerTag(TagTypeEnum.PRODUCT.getName(), new String[] { "床品",
            "枕头", "窗帘", "沙发", "靠枕", "地毯", "装饰画", "提花", "绣花", "装饰布纹理" });
    }

    public void modifyColor() {
        String[] oldTagName = { "蓝#-16711681", "黑#-16777216", "白#-1",
            "紫#-8388353" };
        String[] newTagName = { "蓝#-16776961", "黑#-16119286", "白#-263173",
            "紫#-6283024" };
        for (int nLoop = 0; nLoop < oldTagName.length; ++nLoop) {
            FlowerTypeTag tag = ftTagManager.getByName(oldTagName[nLoop]);
            tag.setName(newTagName[nLoop]);
            ftTagManager.update(tag);
        }
        createFlowerTag(TagTypeEnum.COLOR.getName(), new String[] { "橙#-23296",
            "蓝绿色#-16711681", "米黄色#-657956", "灰#-4934476" });
    }

    /**
     * 创建花型标签
     * 
     * @param tagTypeName
     * @param tagNames
     */
    private void createFlowerTag(String tagTypeName, String[] tagNames) {
        FlowerTypeTagType tagType = new FlowerTypeTagType();
        tagType=ftTagTypeManager.getByName(tagTypeName);
        if(tagType == null){
            tagType = new FlowerTypeTagType();
            tagType.setName(tagTypeName);
            ftTagTypeManager.save(tagType);

        }
        for (String tagName: tagNames) {
            FlowerTypeTag tag = new FlowerTypeTag();
            tag.setName(tagName);
            tag.setTagType(tagType);
            ftTagManager.save(tag);
        }
    }

    /**
     * 创建花型
     * 
     * @param startName
     * @param size
     */
    public void createFlowerType(String ownerName, String path, int nCount)
        throws Exception {

        Account owner = accountManager.getAccountByName(null, ownerName);
        if (owner == null) {
            throw new Exception("Not found account:" + ownerName);
        }

        List<FlowerTypeTag> styles = ftTagManager
            .getAllByTagTypeName(TagTypeEnum.STYLE.getName());
        List<FlowerTypeTag> elements = ftTagManager
            .getAllByTagTypeName(TagTypeEnum.ElEMENTS.getName());
        List<FlowerTypeTag> recommends = ftTagManager
            .getAllByTagTypeName(TagTypeEnum.RECOMMEND.getName());
        List<FlowerTypeTag> colors = ftTagManager
            .getAllByTagTypeName(TagTypeEnum.COLOR.getName());

        int total = 0;
        if (nCount <= 0 || nCount > 500) {
            nCount = 500;
        }
        for (int nLoop = 0; nLoop < nCount; ++nLoop) {
            total = createFlowerType(owner, styles, elements, recommends,
                colors, total, new File(path));
        }
        System.out.println("Create flower type " + total + " success");
    }

    /**
     * 创建单个花型
     * 
     * @param owner
     * @param styleList
     * @param total
     * @param dir
     * @return
     * @throws Exception
     */
    private int createFlowerType(Account owner, List<FlowerTypeTag> styles,
        List<FlowerTypeTag> elements, List<FlowerTypeTag> recommends,
        List<FlowerTypeTag> colors, int total, File dir) throws Exception {
        for (File file: dir.listFiles()) {
            if (file.isDirectory()) {
                total = createFlowerType(owner, styles, elements, recommends,
                    colors, total, file);
            }
            String fileName = file.getName();
            String fileType = fileName.substring(fileName.lastIndexOf('.') + 1)
                .toLowerCase();
            if (fileType.equals("xml")) {
                SceneXmlData xmlData = parserXml(file, "AtPattern");
                String flowerTypeName = xmlData.getName();
                flowerTypeName = "花型" + dir.getName();
                flowerTypeName = "花型" + UUID.randomUUID().toString();
                FlowerType flowerType = flowerTypeManager.getByName(
                    owner.getId(), flowerTypeName);
                if (flowerType == null) {
                    flowerType = new FlowerType();
                }
                flowerType.setName(flowerTypeName);
                flowerType.setOwner(owner);

                /**
                 * 随机插入花型标签
                 */
                Set<FlowerTypeTag> tags = new HashSet();
                FlowerTypeTag tag = randTag(styles);
                tags.add(tag);
                tag = randTag(elements);
                tags.add(tag);
                tag = randTag(recommends);
                tags.add(tag);
                tag = randTag(colors);
                tags.add(tag);
                tag = randTag(colors);
                tags.add(tag);
                flowerType.setTags(tags);

                flowerType.setCoverImage(null);
                flowerType.setXmlFile(null);
                flowerType.setPrintImage(null);
                flowerTypeManager.saveOrUpdate(flowerType);
                String path = flowerTypePath + File.separator
                    + flowerType.getId();

                /**
                 * 封面图片
                 */
                String imageFileName = xmlData.getLocalPath().substring(
                    xmlData.getLocalPath().lastIndexOf('\\') + 1);
                File imageFile = new File(file.getParent(), imageFileName);
                DataFile dateFile = copyFile(imageFile, path,
                    FileType.Cover_Image);
                dataFileManager.save(dateFile);
                flowerType.setCoverImage(dateFile);

                /**
                 * 打印图
                 */
                imageFileName = xmlData.getPrintPath().substring(
                    xmlData.getLocalPath().lastIndexOf('\\') + 1);
                imageFile = new File(file.getParent(), imageFileName);
                if (!imageFile.exists()) {
                    dateFile = null;
                } else {
                    dateFile = copyFile(imageFile, path, FileType.Print_Image);
                    dataFileManager.save(dateFile);
                }

                flowerType.setPrintImage(dateFile);

                /**
                 * XML 伴随xml花型ID
                 */
                dateFile = copyXmlFile(file, flowerType.getId(), path,
                    "AtPattern");
                dataFileManager.save(dateFile);
                flowerType.setXmlFile(dateFile);
                flowerTypeManager.update(flowerType);
                total++;
            }
        }

        return total;
    }

    /**
     * 创建方案
     * 
     * @param ownerName
     * @param path
     */
    /**
     * @param ownerName
     * @param path
     * @throws Exception
     */
    public void createScheme(String ownerName, String path, int nCount)
        throws Exception {
        Account owner = accountManager.getAccountByName(null, ownerName);
        if (owner == null) {
            throw new Exception("Not found account:" + ownerName);
        }

        File schemeRootDir = new File(path);

        if (nCount <= 0 || nCount > 500) {
            nCount = 500;
        }

        for (int nLoop = 0; nLoop < nCount; ++nLoop) {
            for (File subDir: schemeRootDir.listFiles()) {
                if (!subDir.isDirectory()) {
                    continue;
                }
                String schemeName = subDir.getName();
                createScheme(subDir, owner, schemeName);
            }
        }
    }

    /**
     * 读取场景数据初始化DB
     * 
     * @param sceneDir
     * @throws Exception
     */
    private void createScheme(File schemeDir, Account owner, String schemeName)
        throws Exception {
        File[] fileList = schemeDir.listFiles();
        schemeName = "方案" + UUID.randomUUID().toString();
        Scheme scheme = schemeManager.getByName(owner.getId(), schemeName);
        if (scheme == null) {
            scheme = new Scheme();
        }
        File xmlFile = null;
        File coverImage = null;
        for (File file: fileList) {
            if (file.isDirectory()) {
                continue;
            }
            String fileName = file.getName();
            String fileType = fileName.substring(fileName.lastIndexOf('.') + 1)
                .toLowerCase();
            if (fileType.equals("xml")) {
                SchemeXmlData xmlData = parserSchemeXml(file, "ATEXML");
                scheme.setName(xmlData.getName());
                schemeName = "方案" + UUID.randomUUID().toString();
                scheme.setName(schemeName);
                Long sceneID = Long.valueOf(xmlData.getSceneID());
                Long mainFowerTypeID = Long.valueOf(xmlData.getMainFlowerID());
                Scene scene = sceneManager.get(sceneID);
                String path = file.getAbsolutePath();

                Set<FlowerType> flowers = new HashSet();
                XmlHelper xmlHelper = xmlFactory.create(path, XmlType.SCHEME);
                List<Long> ids = xmlHelper.getLongListByNodeName(
                    "PatternID2Path", "PatternPath", "ID");
                if (ids != null && !ids.isEmpty()) {
                    for (Long id: ids) {
                        FlowerType ft = flowerTypeManager.get(id);
                        flowers.add(ft);
                    }
                }
                xmlHelper.setLocalPath(schemeName);
                //xmlHelper.save(file.getParentFile().getAbsolutePath(), file.getName());

                FlowerType flowerType = flowerTypeManager.get(mainFowerTypeID);

                if (scene == null || flowerType == null) {
                    throw new Exception("方案xml场景ID或花型ID不存在");
                }
                scheme.setScene(scene);
                scheme.setFlowerType(flowerType);
                scheme.setFlowers(flowers);
                xmlFile = file;
            }
            if (fileType.equals("jpg")) {
                coverImage = file;
            }
        }

        scheme.setOwner(owner);
        if (xmlFile == null || coverImage == null) {
            throw new Exception("方案缺少数据");
        }

        schemeManager.saveOrUpdate(scheme);

        String path = schemePath + File.separator + scheme.getId();
        DataFile dataFile = copyXmlFile(xmlFile, scheme.getId(), path, "ATEXML");
        dataFileManager.save(dataFile);
        scheme.setXmlFile(dataFile);
        dataFile = copyFile(coverImage, path, FileType.Cover_Image);
        dataFileManager.save(dataFile);
        scheme.setCoverImage(dataFile);

        // 存放文件
        schemeManager.update(scheme);
    }

    private SchemeXmlData parserSchemeXml(File xmlFile, String rootName)
        throws Exception {
        try {
            Document document = documentBuilder.parse(xmlFile);
            NodeList rootNode = document.getElementsByTagName(rootName);
            Element element = (Element) rootNode.item(0);
            String id = getValueByNodeName(element, "ID");
            String name = getValueByNodeName(element, "Name");
            String localPath = getValueByNodeName(element, "LocalPath");
            String sceneID = getValueByNodeName(element, "SceneID");
            String scenePath = getValueByNodeName(element, "ScenePath");

            Element patterns = (Element) element.getElementsByTagName(
                "PatternID2Path").item(0);
            String mainFlowerID = getMainFlowerTypeByNodeName(patterns,
                "PatternPath");

            SchemeXmlData result = new SchemeXmlData();
            result.setId(id);
            result.setName(name);
            result.setLocalPath(localPath);
            result.setSceneID(sceneID);
            result.setMainFlowerID(mainFlowerID);
            return result;
        } catch (Exception e) {
            throw new Exception("Parse xml error, " + xmlFile.getPath(), e);
        }
    }

    private String getMainFlowerTypeByNodeName(Element element, String nodeName) {
        Node node = element.getElementsByTagName(nodeName).item(0);
        NodeList nl = element.getChildNodes();
        for (int index = 0; index < nl.getLength(); ++index) {
            Node nd = nl.item(index);
            if (nd.getNodeName().equals(nodeName)) {
                String result = "";
                try {
                    result = nd.getAttributes().getNamedItem("Main")
                        .getNodeValue();
                } catch (Exception e) {

                }
                if (result.equalsIgnoreCase("1")) {
                    return nd.getAttributes().getNamedItem("ID").getNodeValue();
                }
            }

        }
        return "1";
    }

    private SceneXmlData parserXml(File xmlFile, String rootName)
        throws Exception {
        try {
            Document document = documentBuilder.parse(xmlFile);
            NodeList rootNode = document.getElementsByTagName(rootName);
            Element element = (Element) rootNode.item(0);
            String id = getValueByNodeName(element, "ID");
            String name = getValueByNodeName(element, "Name");
            String description = getValueByNodeName(element, "Description");
            String localPath = getValueByNodeName(element, "LocalPath");
            String coverPath = getValueByNodeName(element, "CoverPath");
            String printPath = getValueByNodeName(element, "PrintPath");
            SceneXmlData result = new SceneXmlData();
            result.setId(id);
            result.setName(name);
            result.setDescription(description);
            result.setLocalPath(localPath);
            result.setCoverPath(coverPath);
            result.setPrintPath(printPath);
            return result;
        } catch (Exception e) {
            throw new Exception("Parse xml error, " + xmlFile.getPath(), e);
        }
    }

    private String getValueByNodeName(Element element, String nodeName)
        throws Exception {
        try {
            Node node = element.getElementsByTagName(nodeName).item(0);
            if (node == null) {
                return null;
            }
            if (node.getFirstChild() == null) {
                return null;
            }

            return node.getFirstChild().getNodeValue();
        } catch (Exception e) {
            throw new Exception("Parse " + nodeName + " error.", e);
        }
    }

    private DataFile copyXmlFile(File xmlFile, Long id, String path,
        String rootName) throws Exception {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        Document document = documentBuilder.parse(xmlFile);
        NodeList rootNode = document.getElementsByTagName(rootName);
        Element element = (Element) rootNode.item(0);
        Node node = element.getElementsByTagName("ID").item(0);
        // 设置ID
        node.setTextContent(id.toString());

        DOMSource domSource = new DOMSource(document);
        File target = new File(dir.getPath() + File.separator
            + xmlFile.getName());
        StreamResult streamResult = new StreamResult(target);
        TransformerFactory.newInstance().newTransformer()
            .transform(domSource, streamResult);

        String fileName = target.getName();
        String md5code = MD5Util.md5(target);

        return new DataFile(fileName, md5code, FileType.Xml);
    }

    private DataFile copyFile(File source, String path, FileType fileType)
        throws IOException {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File wirteFile = new File(dir.getPath() + File.separator
            + source.getName());
        if (wirteFile.exists()) {
            wirteFile.delete();
        }

        FileInputStream input = null;
        FileOutputStream output = null;
        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(wirteFile);
            byte[] bytes = new byte[1024 * 64];
            int readSize = 0;
            while ((readSize = input.read(bytes)) > -1) {
                output.write(bytes, 0, readSize);
                output.flush();
            }
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {}
            }
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {}
            }
        }

        String md5code = MD5Util.md5(wirteFile);

        return new DataFile(wirteFile.getName(), md5code, fileType);
    }

    public void deleteScene() throws Exception {
        deletefile(scenePath);
    }

    public void deleteScheme() throws Exception {
        deletefile(schemePath);
    }

    public void deleteFlower() throws Exception {
        deletefile(flowerTypePath);
    }

    public FlowerTypeTag randTag(List<FlowerTypeTag> tags) {
        int index = rand.nextInt(tags.size() - 1);
        return tags.get(index);
    }

    /**
     * 删除某个文件夹下的所有文件夹和文件
     * 
     * @param delpath
     *            String
     * @throws FileNotFoundException
     * @throws IOException
     * @return boolean
     */
    public static boolean deletefile(String delpath) throws Exception {
        try {

            File file = new File(delpath);
            // 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true  
            if (!file.isDirectory()) {
                file.delete();
            } else if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File delfile = new File(delpath + "\\" + filelist[i]);
                    if (!delfile.isDirectory()) {
                        delfile.delete();
                        System.out
                            .println(delfile.getAbsolutePath() + "删除文件成功");
                    } else if (delfile.isDirectory()) {
                        deletefile(delpath + "\\" + filelist[i]);
                    }
                }
                System.out.println(file.getAbsolutePath() + "删除成功");
                file.delete();
            }

        } catch (FileNotFoundException e) {
            System.out.println("deletefile() Exception:" + e.getMessage());
        }
        return true;
    }
}
