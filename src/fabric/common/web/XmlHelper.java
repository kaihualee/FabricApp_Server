/**
 * @(#)XmlHelper.java, 2013-8-8. 
 * 
 */
package fabric.common.web;

import java.util.List;

import org.w3c.dom.Element;

/**
 * @author likaihua
 */
public interface XmlHelper {

    abstract XmlType getXmlType();

    /**
     * @return
     */
    public String getID();

    /**
     * @param id
     */
    public void setID(String id);

    /**
     * @return
     */
    public String getLocalPath();

    /**
     * @param path
     */
    public void setLocalPath(String path);

    /**
     * @param nodeName
     * @return
     */
    public String getTextContent(String nodeName);

    /**
     * <root> 
     *      <nodeName atrrname='xx' /> 
     * </root>
     * 
     * @param nodeName
     * @param attrname
     * @return
     */
    public String getStringByNodeName(String nodeName, String attrname);

    /**
     * <root> 
     *      <nodeName attrname='xx' />
     *      <nodeName atrrname='xx' /> 
     * </root>
     * 
     * @param nodeName
     * @param attrname
     * @return
     */
    public List<String> getStringListByNodeName(String nodeName, String attrname);

    /**
     * <root> 
     *      <nodeName  >
     *          <subNodeName attrname="xxx">
     *          <subNodeName attrname="xxx">
     *          <subNodeName attrname="xxx">
     *      </nodeName>
     *      <nodeName atrrname='xx' /> 
     * </root>
     * @param nodeName
     * @param subNodeName
     * @param attrname
     * @return
     */
    public List<Long> getLongListByNodeName(String nodeName,
        String subNodeName, String attrname);

    /**
     * <root>
     *      <nodeName obj.getxxx />
     * </root>
     * @param <T>
     * @param nodeName
     * @param obj
     * @return
     */
    public <T extends XmlParameter> List<T> getObjectListByNodeName(String nodeName,
        String subNodeName, T bean);

    /**
     * 保存到指定目录指定文件
     * @param dir
     * @param fileName
     * @throws Exception
     */
    public void save(String dir, String fileName) throws Exception;

   
}
