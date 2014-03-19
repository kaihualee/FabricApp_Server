/**
 * @(#)LogicEntityImpl.java, 2013-6-29. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.common.db;

/**
 * 业务所需的基本对象，包含名称、版本号和状态
 * 
 * @author nisonghai
 */
public class LogicEntityImpl extends BaseEntityImpl implements StatusEntity {

    /**
     * 状态
     */
    protected Status status = Status.Normal;

    /**
     * 名称
     */
    protected String name;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "[" + getClass().getSimpleName() + " " + id + " " + status + "]"
            + name;
    }

}
