/**
 * @(#)GrantTableVO.java, 2013-7-8. 
 * 
 */
package fabric.server.vo;

import fabric.common.web.BaseVO;
import fabric.server.entity.GrantTable;

/**
 * @author likaihua
 */
public class GrantTableVO extends BaseVO {

    
    /**
     * 授权表ID
     */
    private Long AuthorizationID;
    
    /**
     * 乙方
     */
    private String UserName;
    
    /**
     * 产品类型
     */
    private String ProductName;
    /**
     * 授权表名
     */
    private String Name;

    /**
     * 产品类型
     */
    private String Type;

    /**
     * 甲方
     */
    private String AccountA;

    /**
     * 乙方
     */
    private String AccountB;

    /**
     * 产品ID
     */
    private Long ProductID;

    /**
     * 描述信息
     */
    private String Description;

    /**
     * 授权者
     */
    private String ownerName;
    
    /**
     * @param grantTable
     */
    public GrantTableVO(GrantTable grantTable) {
        this.ownerName = grantTable.getOwner().getName();
        this.Name = grantTable.getName();
        this.AccountB = grantTable.getPartB().getName();
        this.Type = grantTable.getType().name();
        this.ProductID = grantTable.getProduct();
        this.Description = grantTable.getDescription();
        this.ProductName = grantTable.getType().name();
        this.AuthorizationID = grantTable.getId();
        this.UserName = grantTable.getPartB().getName();
        this.AccountA = this.ownerName;
    }

    /**
     * @return
     */
    public String getAccountA() {
        return AccountA;
    }

    /**
     * @return
     */
    public String getAccountB() {
        return AccountB;
    }

    /**
     * @return
     */
    public String getDescription() {
        return Description;
    }

    /**
     * @return
     */
    public String getName() {
        return Name;
    }

    /**
     * @return
     */
    public String getOwnerName() {
        return ownerName;
    }

    /**
     * @return
     */
    public Long getProductID() {
        return ProductID;
    }

    /**
     * @return
     */
    public String getType() {
        return Type;
    }

    /**
     * @param accountA
     */
    public void setAccountA(String accountA) {
        AccountA = accountA;
    }

    /**
     * @param accountB
     */
    public void setAccountB(String accountB) {
        AccountB = accountB;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        Description = description;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        Name = name;
    }

    /**
     * @param ownerName
     */
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    /**
     * @param productID
     */
    public void setProductID(Long productID) {
        ProductID = productID;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        Type = type;
    }

    /**
     * @return the authorizationID
     */
    public Long getAuthorizationID() {
        return AuthorizationID;
    }

    /**
     * @param authorizationID the authorizationID to set
     */
    public void setAuthorizationID(Long authorizationID) {
        AuthorizationID = authorizationID;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return UserName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        UserName = userName;
    }

    /**
     * @return the productName
     */
    public String getProductName() {
        return ProductName;
    }

    /**
     * @param productName the productName to set
     */
    public void setProductName(String productName) {
        ProductName = productName;
    }

}
