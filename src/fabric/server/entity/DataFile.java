/**
 * @(#)DataFile.java, 2013-6-29. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.server.entity;

import fabric.common.db.LogicEntityImpl;

/**
 * 数据类型
 * 
 * @author nisonghai
 */
/**
 *
 * @author likaihua
 *
 */
/**
 * @author likaihua
 */
public class DataFile extends LogicEntityImpl {

    /**
     * 文件MD5码
     */
    protected String md5Code;

    /**
     * 文件类型
     */
    protected FileType fileType;

    /**
     * 下载链接名(暂时不用)
     */
    protected String uuid;

    /**
     * @return the md5Code
     */
    public String getMd5Code() {
        return md5Code;
    }

    /**
     * @param md5Code
     *            the md5Code to set
     */
    public void setMd5Code(String md5Code) {
        this.md5Code = md5Code;
    }

    /**
     * @return the fileType
     */
    public FileType getFileType() {
        return fileType;
    }

    /**
     * @param fileType
     *            the fileType to set
     */
    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

   

    public DataFile() {}

    /**
     * @param name
     * @param fileType
     */
    public DataFile(String name, FileType fileType) {
        this.name = name;
        this.fileType = fileType;
    }

    /**
     * @param name
     * @param md5Code
     * @param fileType
     */
    public DataFile(String name, String md5Code, FileType fileType) {
        this.name = name;
        this.md5Code = md5Code;
        this.fileType = fileType;
    }

    /**
     * 文件名,文件类型,UUID
     * @param name
     * @param fileType
     * @param uuid
     */
    public DataFile(String name,  FileType fileType, String uuid){
        this.name = name;
        this.uuid = uuid;
        this.fileType = fileType;
    }
    
    /**
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid the uuid to set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
