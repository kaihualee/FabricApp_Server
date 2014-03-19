/**
 * @(#)OrderVO.java, 2013-7-7. 
 * 
 */
package fabric.server.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

import fabric.common.utils.AppUtils;
import fabric.common.web.BaseVO;
import fabric.server.entity.Customer;
import fabric.server.entity.DataFile;
import fabric.server.entity.Order;
import fabric.server.entity.UserRule;

/**
 * @author likaihua
 */
public class OrderVO extends BaseVO {

    /**
     * 订单ID
     */
    private Long id;

    /**
     * 订单名
     */
    private String OrderName;

    /**
     * 订单备注
     */
    private String Info;

    /**
     * 下单账户ID
     */
    private Long ownerID;

    /**
     * 下单账户名
     */
    private String ownerName;

    /**
     * 订单最后修改时间
     */
    private String modifyTime;

    /**
     * 订单下单时间
     */
    private String orderCreateTime;

    /**
     * 订单状态(未启用)
     */
    private String orderStatus;

    /**
     * 顾客名
     */
    private String customerName;

    /**
     * 顾客地址
     */
    private String customerAddress;

    /**
     * 顾客电话
     */
    private String phone;

    /**
     * 面料
     */
    private String material;

    /**
     * 方案ID
     */
    private Long SchemeID;

    /**
     * 设计师名称
     */
    private String designerName;

    /**
     * 效果图URL
     */
    private String SketchFileURL;

    /**
     * 效果图Md5
     */
    private String sketchMd5Code;

    /**
     * @param order
     */
    public OrderVO(Order order) {
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        this.id = order.getId();
        this.OrderName = order.getName();
        this.Info = order.getDescription();
        this.material = order.getMaterial();
        this.modifyTime = sdf.format(order.getModifyTime());

        this.ownerID = order.getOwner().getId();
        this.ownerName = order.getOwner().getName();

        Customer customer = order.getCustomer();
        this.customerName = customer.getName();
        this.customerAddress = customer.getAddress();
        this.phone = customer.getPhone();

        this.orderCreateTime = sdf.format(order.getOrderCreate());
        this.orderStatus = order.getOdStatus();
        /**
         * 需求改变
         */
        if(order.getScheme() != null){
            this.SchemeID = order.getScheme().getId();
            this.designerName = order.getScheme().getOwner().getName();
        }else{
            this.SchemeID = 0L;
            this.designerName = "";
        }
       

        DataFile sketchFile = order.getSketch();
        if (sketchFile != null) {
            String shopName;
            if (order.getOwner().getRule() != UserRule.BUSINESS) {
                shopName =order.getOwner().getRule().getName();
            } else {
                shopName = order.getOwner().getShop().getName();
            }
            this.SketchFileURL = AppUtils.fetchOrderPath(shopName, order.getId(),
                sketchFile.getName());
            this.sketchMd5Code = order.getSketch().getMd5Code();
        }
        
    }

    /**
     * @return
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * @return
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @return
     */
    public String getDesignerName() {
        return designerName;
    }

    /**
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * @return
     */
    public String getInfo() {
        return Info;
    }

    /**
     * @return
     */
    public String getMaterial() {
        return material;
    }

    /**
     * @return
     */
    public String getModifyTime() {
        return modifyTime;
    }

    /**
     * @return
     */
    public String getOrderCreateTime() {
        return orderCreateTime;
    }

    /**
     * @return
     */
    public String getOrderName() {
        return OrderName;
    }

    /**
     * @return
     */
    public String getOrderStatus() {
        return orderStatus;
    }


    /**
     * @return
     */
    public Long getOwnerID() {
        return ownerID;
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
    public String getPhone() {
        return phone;
    }

    /**
     * @return
     */
    public Long getSchemeID() {
        return SchemeID;
    }

    /**
     * @return
     */
    public String getSketchFileURL() {
        return SketchFileURL;
    }

    /**
     * @return
     */
    public String getSketchMd5Code() {
        return sketchMd5Code;
    }

    /**
     * @param customerAddress
     */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @param designerName
     */
    public void setDesignerName(String designerName) {
        this.designerName = designerName;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @param info
     */
    public void setInfo(String info) {
        Info = info;
    }

    /**
     * @param material
     */
    public void setMaterial(String material) {
        this.material = material;
    }

    /**
     * @param modifyTime
     */
    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * @param orderCreateTime
     */
    public void setOrderCreateTime(String orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }


    /**
     * @param orderName
     */
    public void setOrderName(String orderName) {
        OrderName = orderName;
    }

    /**
     * @param orderStatus
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * @param ownerID
     */
    public void setOwnerID(Long ownerID) {
        this.ownerID = ownerID;
    }

    /**
     * @param ownerName
     */
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    /**
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @param schemeID
     */
    public void setSchemeID(Long schemeID) {
        SchemeID = schemeID;
    }

    /**
     * @param sketchFileURL
     */
    public void setSketchFileURL(String sketchFileURL) {
        SketchFileURL = sketchFileURL;
    }

    /**
     * @param sketchMd5Code
     */
    public void setSketchMd5Code(String sketchMd5Code) {
        this.sketchMd5Code = sketchMd5Code;
    }

}
