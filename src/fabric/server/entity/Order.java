package fabric.server.entity;

import java.util.Date;

import fabric.common.db.BusinessEntityImpl;


/**
 * 订单
 *
 * @author likaihua
 *
 */
public class Order extends BusinessEntityImpl {
	
	/**
	 * 方案
	 */
	private Scheme scheme;
	    
	/**
	 * 顾客
	 */              
	
	private Customer customer;
	
	/**
	 * 订单状态(未使用)
	 */
	private OrderStatus orderStatus = OrderStatus.READY;

    /**
     * 面料
     */
	private String material;
	
	/**
	 * 下单时间
	 */
	private Date orderCreate;
	
	/**
	 * 效果图
	 */
	private DataFile sketch;
	
	
	/**
     * @return the scheme
     */
    public Scheme getScheme() {
        return scheme;
    }

    /**
     * 订单状态
     */
    private String odStatus;
    
    /**
     * @param scheme the scheme to set
     */
    public void setScheme(Scheme scheme) {
        this.scheme = scheme;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the orderStatus
     */
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    /**
     * @param orderStatus the orderStatus to set
     */
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * @return
     */
    public String getMaterial() {
        return material;
    }

    /**
     * @param material
     */
    public void setMaterial(String material) {
        this.material = material;
    }

    /**
     * @return
     */
    public Date getOrderCreate() {
        return orderCreate;
    }

    /**
     * @param orderCreate
     */
    public void setOrderCreate(Date orderCreate) {
        this.orderCreate = orderCreate;
    }

    /**
     * @return
     */
    public String getOdStatus() {
        return odStatus;
    }

    /**
     * @param odStatus
     */
    public void setOdStatus(String odStatus) {
        this.odStatus = odStatus;
    }

    /**
     * @return
     */
    public DataFile getSketch() {
        return sketch;
    }

    /**
     * @param sketch
     */
    public void setSketch(DataFile sketch) {
        this.sketch = sketch;
    }
	
	
}
