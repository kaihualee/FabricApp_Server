/**
 * @(#)ManagerException.java, 2013-7-1. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.common.db;

/**
 *
 * @author nisonghai
 *
 */
public class ManagerException extends RuntimeException {
    
    public ManagerException(String message) {
        super(message);
    }
    
    public ManagerException(String message, Throwable cause) {
        super(message, cause);
    }

}
