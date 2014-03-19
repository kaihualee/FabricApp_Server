/**
 * @(#)SceneXmlData.java, 2013-7-10. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.server.init;

/**
 *
 * @author nisonghai
 *
 */
public class SceneXmlData {
    
    private String id;
    
    private String name;
    
    private String description;
    
    private String localPath;
    
    private String coverPath;
    
    private String printPath;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the localPath
     */
    public String getLocalPath() {
        return localPath;
    }

    /**
     * @param localPath the localPath to set
     */
    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    /**
     * @return the coverPath
     */
    public String getCoverPath() {
        return coverPath;
    }

    /**
     * @param coverPath the coverPath to set
     */
    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the printPath
     */
    public String getPrintPath() {
        return printPath;
    }

    /**
     * @param printPath the printPath to set
     */
    public void setPrintPath(String printPath) {
        this.printPath = printPath;
    }

}
