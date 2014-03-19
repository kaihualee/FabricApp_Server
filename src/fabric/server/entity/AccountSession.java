/**
 * @(#)AccountSession.java, 2013-7-13. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.server.entity;

import fabric.common.db.BaseEntityImpl;
import fabric.common.db.LogicEntityImpl;
import fabric.common.db.VersionEntity;

/**
 * 用户会话状态保存
 * 
 * @author nisonghai
 */
public class AccountSession extends LogicEntityImpl implements VersionEntity {

    /**
     * 用户SID
     */
    private String sid;

    /**
     * 账号
     */
    private Account account;

    /**
     * IP
     */
    private String ip;

    /**
     * 登陆界面
     */
    private ClientPower power;
    

    /**
     * 访问次数
     */
    private int version = 0;

    /**
     * @return the sid
     */
    public String getSid() {
        return sid;
    }


    /**
     * @param sid
     *            the sid to set
     */
    public void setSid(String sid) {
        this.sid = sid;
    }

    /**
     * @return the account
     */
    public Account getAccount() {
        return account;
    }

    /**
     * @param account
     *            the account to set
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip
     *            the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the power
     */
    public ClientPower getPower() {
        return power;
    }

    /**
     * @param power
     *            the power to set
     */
    public void setPower(ClientPower power) {
        this.power = power;
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public void setVersion(int version) {
        this.version = version;
    }

}
