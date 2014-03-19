package fabric.server.vo;

import java.util.Date;

import fabric.common.web.BaseVO;
import fabric.server.entity.Account;


public class AccountVO extends BaseVO {
	
	/**
	 * 用户名 
	 */
	private String userName;
	/**
	 * 真实姓名
	 */
	private String realName;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 备注
	 */
	private String info;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 权限
	 */
	private String power;
	
	/**
	 * @param account
	 */
	public AccountVO(Account account){
	    this.userName = account.getName();
	    this.realName = account.getRealname();
	    this.nickName = account.getNickname();
	    this.info = account.getInfo();
	    this.status = account.getStatus().name();
	    this.power = account.getRule().getName();
	}
	
    /**
     * @return
     */
    public String getInfo() {
        return info;
    }
    /**
     * @return
     */
    public String getNickName() {
        return nickName;
    }
    /**
     * @return
     */
    public String getPower() {
        return power;
    }
    /**
     * @return
     */
    public String getRealName() {
        return realName;
    }
    /**
     * @return
     */
    public String getStatus() {
        return status;
    }
    /**
     * @return
     */
    public String getUserName() {
        return userName;
    }
    /**
     * @param info
     */
    public void setInfo(String info) {
        this.info = info;
    }
    /**
     * @param nickName
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    /**
     * @param power
     */
    public void setPower(String power) {
        this.power = power;
    }
    /**
     * @param realName
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }
    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
	
	
		
	
}

