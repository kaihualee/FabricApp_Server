/**
 * @(#)OrderStatus.java, 2013-6-29. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.server.entity;

/**
 * 订单状态
 * 
 * @author nisonghai
 */
public enum OrderStatus {

    /**
     * 未处理
     */
    READY,
    /**
     * 已付款
     */
    Paid, 
    /**
     * 发货
     */
    Delivery, 
    /**
     * 成交
     */
    End

}
