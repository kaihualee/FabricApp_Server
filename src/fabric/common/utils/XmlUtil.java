/**
 * @(#)XmlHelper.java, 2013-8-7. 
 * 
 */
package fabric.common.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.metadata.IIOMetadataNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author likaihua
 */
public class XmlUtil {

    private DocumentBuilder documentBuilder;

    protected static final Log logger = LogFactory.getLog(XmlUtil.class);

    private Document document;

    private NodeList rootNode;

    public XmlUtil(File xmlFile) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        documentBuilder = dbf.newDocumentBuilder();
        document = documentBuilder.parse(xmlFile);
        rootNode = null;
    }

    public List<Node> getNodeListByName(String rootName, String nodeName,
        String subNodeName) {
        if (rootNode == null) {
            rootNode = document.getElementsByTagName(rootName);
        }
        if (rootNode == null) {
            return null;
        }
        Element[] elements = getElementsByName(rootName, nodeName);
        if (elements == null || elements.length == 0) {
            return null;
        }
        Element parent = elements[0];
        NodeList nodelist = XmlOper.getNodeList(parent);
        List<Node> resList = new ArrayList();
        for (int nLoop = 0; nLoop < nodelist.getLength(); ++nLoop) {
            Node node = nodelist.item(nLoop);
            if (node.getNodeName().equals(subNodeName)) {
                resList.add(node);
            }

        }
        return resList;
    }
    public List<Node> getNodeListByName(String rootName, String nodeName){
        if (rootNode == null) {
            rootNode = document.getElementsByTagName(rootName);
        }
        if (rootNode == null) {
            return null;
        }
        Element parent = (Element) rootNode.item(0);
        NodeList nodelist = XmlOper.getNodeList(parent);
        List<Node> resList = new ArrayList();
        for (int nLoop = 0; nLoop < nodelist.getLength(); ++nLoop) {
            Node node = nodelist.item(nLoop);
            if (node.getNodeName().equals(nodeName)) {
                resList.add(node);
            }
        }
        return resList;
    }
    
    
    public Node getNodeByName(String rootName, String nodeName){
        List<Node> list = getNodeListByName(rootName, nodeName);
        if(list == null || list.isEmpty()){
            return null;
        }
        return list.get(0);
    }
    
    public Element[] getElementsByName(String rootName, String nodeName) {
        if (rootNode == null) {
            rootNode = document.getElementsByTagName(rootName);
        }
        if (rootNode == null) {
            return null;
        }
        Element parent = (Element) rootNode.item(0);
        return XmlOper.getElementsByName(parent, nodeName);
    }

    /*
    public List<Long> getLongListByName(List<Node> nodes, String attrname) {
        if (nodes == null || attrname == null || attrname.isEmpty()) {
            return null;
        }
        List<Long> resList = new ArrayList();
        for (int nLoop = 0; nLoop < nodes.size(); ++nLoop) {
            Node node = nodes.get(nLoop);
            String result = node.getAttributes().getNamedItem(attrname)
                .getNodeValue();
            resList.add(Long.valueOf(result));
        }
        return resList;
    }
    */
    public boolean setElementValue(String rootName, String nodeName,
        String value) {
        if (rootNode == null) {
            rootNode = document.getElementsByTagName(rootName);
        }
        if (rootNode == null) {
            return false;
        }
        Element[] elements = getElementsByName(rootName, nodeName);
        if (elements == null || elements.length == 0) {
            return false;
        }
        Element parent = elements[0];
        XmlOper.setElementValue(parent, value);
        //Node node = parent;
        // 设置ID
        //node.setTextContent(value);
        return true;
    }

    public void save(String dir, String filename) throws Exception {
        if (dir == null || dir.isEmpty() || filename == null
            || filename.isEmpty()) {
            return;
        }
        DOMSource domSource = new DOMSource(document);
        File target = new File(dir + File.separator + filename);
        StreamResult streamResult = new StreamResult(target);
        TransformerFactory.newInstance().newTransformer()
            .transform(domSource, streamResult);
    }

    public static void main(String[] args) throws Exception {
        //File xmlFile = new File("E:\\scheme.xml");
        File xmlFile = new File("E:\\AMPT51E75A82");
        XmlUtil xmlHelper = new XmlUtil(xmlFile);

        String rootName = "AtPattern";
        String nodeName = "PatternID2Path";
        String subNodeName = "PatternPath";

        //List<Node> nodes = xmlHelper.getNodeListByName(rootName, nodeName,
        //    subNodeName);
        // if(nodes == null || nodes.isEmpty()){
        //   System.out.println("sub");
        //  return;
        //}
        /*
         * List<Long> ids = xmlHelper.getLongListByName(nodes, "ID"); for (int
         * nLoop = 0; nLoop < ids.size(); ++nLoop) { System.out.println(":" +
         * String.valueOf(ids.get(nLoop))); }
         */
        if (xmlHelper.setElementValue(rootName, "ID",
            "10000000000000000000000000000000") == false) {
            System.out.println("False");
        }
        xmlHelper.save("E:\\", "kk.xml");
    }
}
