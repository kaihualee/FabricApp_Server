/**
 * @(#)Status.java, 2013-6-29. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.common.db;

/**
 * 实体对象的状态
 *
 * @author nisonghai
 *
 */
public enum Status {
    
    /**
     * 可用状态
     */
    Normal, 
    /**
     * 禁用状态
     */
    Disabled, 
    /**
     * 已删除状态
     */
    Deleted;

}
