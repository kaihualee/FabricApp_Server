/**
 * @(#)FlowerTypeXml.java, 2013-8-8. 
 * 
 */
package fabric.common.web;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Node;

import fabric.common.utils.XmlUtil;

/**
 * @author likaihua
 */
public class ProductXml implements XmlHelper {

    protected static final Log logger = LogFactory.getLog(ProductXml.class);

    private XmlUtil xmlUtil;

    private XmlType type;

    public ProductXml(File f, XmlType type) throws Exception {
        xmlUtil = new XmlUtil(f);
        this.type = type;
    }

    @Override
    public XmlType getXmlType() {
        return type;
    }

    public String getID() {
        Node node = xmlUtil.getNodeByName(type.getRootName(), type.getIdName());
        return node.getTextContent();
    }

    @Override
    public void setID(String id) {
        Node node = xmlUtil.getNodeByName(type.getRootName(), type.getIdName());
        node.setTextContent(id);
    }

    @Override
    public String getLocalPath() {
        Node node = xmlUtil.getNodeByName(type.getRootName(),
            type.getCoverName());
        return node.getTextContent();
    }

    @Override
    public void setLocalPath(String path) {
        Node node = xmlUtil.getNodeByName(type.getRootName(),
            type.getCoverName());
        node.setTextContent(path);
    }

    @Override
    public String getTextContent(String nodeName) {
        Node node = xmlUtil.getNodeByName(type.getRootName(), nodeName);
        return node.getTextContent();
    }

    @Override
    public List<String> getStringListByNodeName(String nodeName, String attrname) {
        List<Node> list = xmlUtil.getNodeListByName(type.getRootName(),
            nodeName);
        List<String> resList = new ArrayList();
        for (int nLoop = 0; nLoop < list.size(); ++nLoop) {
            Node node = list.get(nLoop);
            String result = node.getAttributes().getNamedItem(attrname)
                .getNodeValue();
            resList.add(result);
        }
        return resList;
    }

    @Override
    public String getStringByNodeName(String nodeName, String attrname) {
        List<String> list = getStringListByNodeName(nodeName, attrname);
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public List<Long> getLongListByNodeName(String nodeName,
        String subNodeName, String attrname) {
        List<Node> list = xmlUtil.getNodeListByName(type.getRootName(),
            nodeName, subNodeName);
        List<Long> resList = new ArrayList();
        for (int nLoop = 0; nLoop < list.size(); ++nLoop) {
            Node node = list.get(nLoop);
            String result = node.getAttributes().getNamedItem(attrname)
                .getNodeValue();
            resList.add(Long.valueOf(result));
        }
        return resList;

    }

    @Override
    public <T extends XmlParameter> List<T> getObjectListByNodeName(
        String nodeName, String subNodeName, T bean) {
        Class klass = bean.getClass();
        Method[] methods = klass.getMethods();
        List<T> resList;
        try {
            List<Node> nodelist = xmlUtil.getNodeListByName(type.getRootName(),
                nodeName, subNodeName);
            if (nodelist == null || nodelist.isEmpty()) {
                return null;
            }
            resList = new ArrayList();
            for (Node node: nodelist) {
                bean = (T) new XmlParameter();
                for (int i = 0; i < methods.length; ++i) {
                    Method method = methods[i];
                    String name = method.getName();
                    String key = "";
                    if (name.startsWith("set")) {
                        key = name.substring(3);
                        String value = node.getAttributes().getNamedItem(key)
                            .getNodeValue();
                        try {
                            Object result = method.invoke(bean, value);
                        } catch (Exception ignore) {
                            logger.info("invoke method set" + key + "(" + value
                                + ")");
                        }
                    }
                }
                resList.add(bean);
            }
        } catch (Exception e) {
            logger.error("Scheme xml 解析出错.\n"+ e.getMessage());
            return null;
        }
        return resList;
    }

    @Override
    public void save(String dir, String filename) throws Exception {
        xmlUtil.save(dir, filename);
    }

    public static void main(String[] args) throws Exception {
        File xmlFile = new File("E:\\scheme.xml");
        ProductXml ftXml = new ProductXml(xmlFile, XmlType.SCHEME);

        String id = ftXml.getID();
        System.out.println(id);
        id = "123456";
        ftXml.setID(id);
        String path = ftXml.getLocalPath();
        System.out.println(path);
        path = "NetEase";
        ftXml.setLocalPath(path);

        List<Long> list = ftXml.getLongListByNodeName("PatternID2Path",
            "PatternPath", "ID");

        for (int nLoop = 0; nLoop < list.size(); ++nLoop) {
            System.out.println(list.get(nLoop));
        }
        ftXml.save("E:\\", "kk1025.xml");

        List<XmlParameter> plist = ftXml.getObjectListByNodeName(
            "PatternID2Path", "PatternPath", new XmlParameter());

        for (XmlParameter item: plist) {
            System.out.println("ID:" + item.getID());
        }
    }

}
