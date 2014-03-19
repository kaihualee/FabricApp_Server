/**
 * @(#)StatusEntity.java, 2013-6-29. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.common.db;

/**
 * 带状态的实体对象
 *
 * @author nisonghai
 *
 */
public interface StatusEntity {
    
    public Status getStatus();
    
    public void setStatus(Status status);

}
