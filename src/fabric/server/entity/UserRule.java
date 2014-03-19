/**
 * @(#)UserRule.java, 2013-6-29. Copyright 2013 Netease, Inc. All rights
 *                    reserved. NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject
 *                    to license terms.
 */
package fabric.server.entity;

/**
 * 用户角色
 * 
 * @author nisonghai
 */
public enum UserRule {

    /**
     * 超级管理员
     */
    SUPER_ADMIN("超级管理员", 0),
    /**
     * 设计师
     */
    DESIGNER("设计师", 3),
    /**
     * 商家店长
     */
    BUSINESS("商家店长", 2),
    /**
     * 普通用户
     */
    GENERAL("普通用户", 1),
    /**
     * 访问用户
     */
    GUEST("访问用户", 4);

    /**
     * 角色名称
     */
    private String name;

    private int power;

    /**
     * @param name
     * @param power
     */
    private UserRule(String name, int power) {
        this.name = name;
        this.power = power;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the power
     */
    public int getPower() {
        return power;
    }

    /**
     * @param power
     * @return
     */
    public static UserRule valueOf(int power) {
        if (power == GENERAL.power) {
            return GENERAL;
        } else if (power == BUSINESS.power) {
            return BUSINESS;
        } else if (power == DESIGNER.power) {
            return DESIGNER;
        } else if (power == SUPER_ADMIN.power) {
            return SUPER_ADMIN;
        } else if(power == GUEST.power){
            return GUEST;
        }else{
            return null;
        }
    }
}
