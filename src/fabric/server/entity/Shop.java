/**
 * @(#)Shop.java, 2013-6-29. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.server.entity;

import fabric.common.db.StyleEntityImpl;


/**
 * 店家
 *
 * @author nisonghai
 *
 */
public class Shop extends StyleEntityImpl {
    
    /**
     * 地址
     */
    private String address;
    
    /**
     * 电话
     */
    private String phone;

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

}
