/**
 * @(#)XmlOper.java, 2013-7-23. 
 * 
 */
package fabric.common.utils;

/**
 *用于操作ＸＭＬ文件，包括查找、新增、删除、修改结点
 * @author likaihua
 *
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlOper {

    protected static final Log logger = LogFactory.getLog(XmlOper.class);

    /**
     * 构造函数说明： 参数说明：
     **/
    private XmlOper() {}

    /**
     * 方法名称：getNodeList 方法功能：获取父结点parent的所有子结点 参数说明：@param parent 参数说明：@return
     * 返回：NodeList
     **/
    public static NodeList getNodeList(Element parent) {
        return parent.getChildNodes();
    }

    /**
     * 方法名称：getElementsByName 方法功能：在父结点中查询指定名称的结点集 参数说明：@param parent
     * 参数说明：@param name 参数说明：@return 返回：Element[]
     **/
    public static Element[] getElementsByName(Element parent, String name) {
        ArrayList resList = new ArrayList();
        NodeList nl = getNodeList(parent);
        for (int i = 0; i < nl.getLength(); i++) {
            Node nd = nl.item(i);
            if (nd.getNodeName().equals(name)) {
                resList.add(nd);
            }
        }
        Element[] res = new Element[resList.size()];
        for (int i = 0; i < resList.size(); i++) {
            res[0] = (Element) resList.get(i);
        }
        logger.debug(parent.getNodeName() + "'s children of " + name
            + "'s num:" + res.length);
        return res;
    }

    /**
     * 方法名称：getElementName 方法功能：获取指定Element的名称 参数说明：@param element 参数说明：@return
     * 返回：String
     **/
    public static String getElementName(Element element) {
        return element.getNodeName();
    }

    /**
     * 方法名称：getElementValue 方法功能：获取指定Element的值 参数说明：@param element 参数说明：@return
     * 返回：String
     **/
    public static String getElementValue(Element element) {
        NodeList nl = element.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            if (nl.item(i).getNodeType() == Node.TEXT_NODE)//是一个Text Node
            {
                logger.debug(element.getNodeName() + " has a Text Node.");
                return element.getFirstChild().getNodeValue();
            }
        }
        logger.error(element.getNodeName() + " hasn't a Text Node.");
        return null;
    }

    /**
     * 方法名称：getElementAttr 方法功能：获取指定Element的属性attr的值 参数说明：@param element
     * 参数说明：@param attr 参数说明：@return 返回：String
     **/
    public static String getElementAttr(Element element, String attr) {
        return element.getAttribute(attr);
    }

    /**
     * 方法名称：setElementValue 方法功能：设置指定Element的值 参数说明：@param element 参数说明：@param
     * val 返回：void
     **/
    public static void setElementValue(Element element, String val) {
        Node node = element.getOwnerDocument().createTextNode(val);
        NodeList nl = element.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node nd = nl.item(i);
            if (nd.getNodeType() == Node.TEXT_NODE)//是一个Text Node
            {
                nd.setNodeValue(val);
                logger.debug("modify " + element.getNodeName()
                    + "'s node value succe.");
                return;
            }
        }
        logger.debug("new " + element.getNodeName() + "'s node value succe.");
        element.appendChild(node);
    }

    /**
     * 方法名称：setElementAttr 方法功能：设置结点Element的属性 参数说明：@param element 参数说明：@param
     * attr 参数说明：@param attrVal 返回：void
     **/
    public static void setElementAttr(Element element, String attr,
        String attrVal) {
        element.setAttribute(attr, attrVal);
    }

    /**
     * 方法名称：addElement 方法功能：在parent下增加结点child 参数说明：@param parent 参数说明：@param
     * child 返回：void
     **/
    public static void addElement(Element parent, Element child) {
        parent.appendChild(child);
    }

    /**
     * 方法名称：addElement 方法功能：在parent下增加字符串tagName生成的结点 参数说明：@param parent
     * 参数说明：@param tagName 返回：void
     **/
    public static void addElement(Element parent, String tagName) {
        Document doc = parent.getOwnerDocument();
        Element child = doc.createElement(tagName);
        parent.appendChild(child);
    }

    /**
     * 方法名称：addElement 方法功能：在parent下增加tagName的Text结点，且值为text
     * 
     * @param parent
     * @param tagName
     * @param text
     *            返回：void
     **/
    public static void addElement(Element parent, String tagName, String text) {
        Document doc = parent.getOwnerDocument();
        Element child = doc.createElement(tagName);
        setElementValue(child, text);
        parent.appendChild(child);
    }

    /**
     * 方法名称：removeElement 方法功能：将父结点parent下的名称为tagName的结点移除 参数说明：@param parent
     * 参数说明：@param tagName 返回：void
     **/
    public static void removeElement(Element parent, String tagName) {
        logger.debug("remove " + parent.getNodeName()
            + "'s children by tagName " + tagName + " begin...");
        NodeList nl = parent.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node nd = nl.item(i);
            if (nd.getNodeName().equals(tagName)) {
                parent.removeChild(nd);
                logger.debug("remove child '" + nd + "' success.");
            }
        }
        logger.debug("remove " + parent.getNodeName()
            + "'s children by tagName " + tagName + " end.");
    }

    public static void main(String[] args) throws SAXException, IOException,
        ParserConfigurationException {
        File xmlFile = new File("E:\\scheme.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
        Document document = documentBuilder.parse(xmlFile);
        String rootName = "ATEXML";
        NodeList rootNode = document.getElementsByTagName(rootName);
        Element element = (Element) rootNode.item(0);
        
        
        NodeList nl= element.getElementsByTagName("PatternID2Path").item(0).getChildNodes();
        for(int nLoop =0; nLoop<nl.getLength(); ++nLoop){
            Node node = nl.item(nLoop);
            if(node.getNodeName().equals("PatternPath")){
                String result = node.getAttributes().getNamedItem("ID")
                .getNodeValue();
                System.out.println(result);
            }
        }
        
        
        Element[] elements = getElementsByName(element, "PatternID2Path");
        nl= getNodeList(elements[0]);
         for(int nLoop =0; nLoop<nl.getLength(); ++nLoop){
             Node node = nl.item(nLoop);
             if(node.getNodeName().equals("PatternPath")){
                 String result = node.getAttributes().getNamedItem("ID")
                 .getNodeValue();
                 System.out.println(":"+result);
             }
         }
    }
}
