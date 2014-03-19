/**
 * @(#)XmlType.java, 2013-8-8. 
 * 
 */
package fabric.common.web;

/**
 *
 * @author likaihua
 *
 */
public enum XmlType {
    FLOWERTYPE("AtPattern", "ID", "LocalPath"),
    SCHEME("ATEXML", "ID", "LocalPath");
    
    /**
     * 顶结点
     */
    private String rootName;
    /**
     * ID结点
     */
    private String idName;
    /**
     * 封面图片结点   
     */
    private String coverName;
    
    
    XmlType(String rootName, String idName, String coverName){
        this.rootName = rootName;
        this.idName = idName;
        this.coverName = coverName;
    }


    /**
     * @return the rootName
     */
    public String getRootName() {
        return rootName;
    }


    /**
     * @param rootName the rootName to set
     */
    public void setRootName(String rootName) {
        this.rootName = rootName;
    }


    /**
     * @return the idName
     */
    public String getIdName() {
        return idName;
    }


    /**
     * @param idName the idName to set
     */
    public void setIdName(String idName) {
        this.idName = idName;
    }


    /**
     * @return the coverName
     */
    public String getCoverName() {
        return coverName;
    }


    /**
     * @param coverName the coverName to set
     */
    public void setCoverName(String coverName) {
        this.coverName = coverName;
    }
}
