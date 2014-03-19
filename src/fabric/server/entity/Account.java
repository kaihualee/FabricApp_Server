package fabric.server.entity;

import fabric.common.db.LogicEntityImpl;

/**
 * 帐号
 * 
 * @author Likaihua
 */
public class Account extends LogicEntityImpl {

    /**
     * 姓名
     */
    private String realname;

    /**
     * 密码
     */
    private String password;

    /**
	 * 昵称
	 */
    private String nickname;

    /**
     * 备注
     */
    private String info;

    /**
     * 角色
     */
    private UserRule rule;
    
    /**
     * 店家信息
     */
    private Shop shop;
    
    /**
     * @return the realname
     */
    public String getRealname() {
        return realname;
    }

    /**
     * @param realname the realname to set
     */
    public void setRealname(String realname) {
        this.realname = realname;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname the nickname to set
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return the info
     */
    public String getInfo() {
        return info;
    }

    /**
     * @param info the info to set
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * @return the rule
     */
    public UserRule getRule() {
        return rule;
    }

    /**
     * @param rule the rule to set
     */
    public void setRule(UserRule rule) {
        this.rule = rule;
    }

    /**
     * @return the shop
     */
    public Shop getShop() {
        return shop;
    }

    /**
     * @param shop the shop to set
     */
    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Account() {
    }
    
    public Account(Long id) {
        this.id = id;
    }

}
