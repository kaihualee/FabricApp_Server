/**
 * @(#)ClientPower.java, 2013-7-13. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.server.entity;

import java.rmi.NoSuchObjectException;

/**
 * 客户端连接类型
 * 
 * @author nisonghai
 */
public enum ClientPower {

    /**
     * 系统管理员界面
     */
    ADMIN_SYSTEM(0, UserRule.SUPER_ADMIN),

    /**
     * 一体机界面
     */
    SHOW_SHOP(1, UserRule.BUSINESS, UserRule.GENERAL),

    /**
     * 店长管理界面
     */
    ADMIN_BUSINESS(2, UserRule.BUSINESS),

    /**
     * 设计师管理界面
     */
    ADMIN_DESIGNER(3, UserRule.SUPER_ADMIN, UserRule.DESIGNER);

    
    /**
     * 对应可以访问的角色列表
     */
    private UserRule[] rules;

    private int power;

    private ClientPower(int power, UserRule... rules) {
        this.power = power;
        this.rules = rules;
    }

    public static ClientPower valueOf(int power) throws NoSuchObjectException {
        switch (power) {
            case 0:
                return ADMIN_SYSTEM;
            case 1:
                return SHOW_SHOP;
            case 2:
                return ADMIN_BUSINESS;
            case 3:
                return ADMIN_DESIGNER;
            default:
                throw new NoSuchObjectException("Not fount power object.");
        }
    }

    /**
     * 检查角色权限
     * 
     * @param rule
     * @return
     */
    public boolean checkRule(UserRule rule) {
        for (UserRule tmp: rules) {
            if (tmp == rule) {
                return true;
            }
        }

        return false;
    }

    /**
     * @return the power
     */
    public int getPower() {
        return power;
    }

}
