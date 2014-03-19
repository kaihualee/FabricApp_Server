/**
 * @(#)BusinessEntityImpl.java, 2013-6-29. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.common.db;

import java.util.List;

import fabric.common.utils.BeanUtils;
import fabric.server.entity.Account;

/**
 * 业务对象
 * 
 * @author nisonghai
 */
public class BusinessEntityImpl extends LogicEntityImpl implements
    VersionEntity {

    /**
     * 版本
     */
    protected int version = 0;

    /**
     * 说明
     */
    protected String description;

    /**
     * 所有者
     */
    protected Account owner;

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the owner
     */
    public Account getOwner() {
        return owner;
    }

    /**
     * @return the version
     */
    public int getVersion() {
        return version;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setEntity(BaseEntity entity) {
        List<String> fields = BeanUtils.getPropertyNames(this.getClass());
        for (String fieldName: fields) {
            if (fieldName.equals("version")) {
                continue;
            }
            try {
                Object newValue = BeanUtils.forceGetProperty(entity, fieldName);
                if (newValue != null) {
                    BeanUtils.forceSetProperty(this, fieldName, newValue);
                }
            } catch (NoSuchFieldException e) {
                // ignore
            }
        }
    }

    /**
     * @param owner
     *            the owner to set
     */
    public void setOwner(Account owner) {
        this.owner = owner;
    }

    /**
     * @param version
     *            the version to set
     */
    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "[" + getClass().getSimpleName() + " " + id + " " + status + " "
            + version + "]" + name + " owner=" + owner.getName();
    }

}
