/**
 * @(#)OrderResource.java, 2013-7-5. 
 * 
 */
package fabric.server.web.resource;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import jxl.write.DateTime;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import fabric.common.db.Status;
import fabric.common.utils.MD5Util;
import fabric.common.utils.AppUtils;
import fabric.common.web.BaseVO;
import fabric.common.web.ErrorCode;
import fabric.common.web.ErrorVO;
import fabric.common.web.WebParameters;
import fabric.server.entity.Account;
import fabric.server.entity.Customer;
import fabric.server.entity.DataFile;
import fabric.server.entity.FileType;
import fabric.server.entity.Order;
import fabric.server.entity.OrderStatus;
import fabric.server.entity.Scheme;
import fabric.server.entity.UserRule;
import fabric.server.manager.CustomerManager;
import fabric.server.manager.OrderManager;
import fabric.server.manager.SchemeManager;
import fabric.server.vo.ListVO;
import fabric.server.vo.MapVO;
import fabric.server.vo.OrderVO;

/**
 * @author likaihua
 */

@Controller
@Scope("prototype")
public class OrderResource extends AuthResource<Order, OrderManager> {

    @Autowired
    private OrderManager orderManager;

    private static String LOCAL_PATH = AppUtils.getUploadPath() + "/Order";

    private final static String TEMP = "temp";

    @Override
    protected OrderManager getManager() {
        return orderManager;
    }

    /**
     * 添加订单
     * 
     * @param parameters
     * @return
     */
    public BaseVO newOrder(WebParameters parameters) {
        String orderName = parameters.getString("OrderName");
        // Long schemeID = parameters.getLong("SchemeID");
        String name = parameters.getString("CustomerName");
        String address = parameters.getString("CustomerAddress");
        String phone = parameters.getString("Phone");
        String description = parameters.getString("Info");
        String material = parameters.getString("Material");
        material = "";
        description = "";

        //预览图
        String sketchFileName = parameters.getString("SketchFileName");
        //String sketchUUID = parameters.getString("SketchFileUUID");
        String sketchUUID = sketchFileName;

        //界面缺少用户详细信息，只有电话是必须的
        if (//orderName == null || orderName.isEmpty()
            //|| name == null || name.isEmpty()
            // || address == null || address.isEmpty()
        /* || */phone == null || phone.isEmpty()) {
            return new ErrorVO(ErrorCode.ORDER_PARAMETERS_NO_EXIST);
        }

        Customer customer = new Customer();
        customer.setAddress(address);
        customer.setName(name);
        customer.setPhone(phone);

        Order order = new Order();
        order.setName(orderName);
        order.setDescription(description);
        order.setMaterial(material);
        order.setOwner(account);
        order.setCustomer(customer);
        order.setOrderStatus(OrderStatus.READY);
        order.setOrderCreate(new Date());
        order.setCreateDate(new Date());
        order.setStatus(Status.Deleted);
        orderManager.save(order);

        if (sketchFileName == null) {
            return new ErrorVO(ErrorCode.PARAMETER_ERROR);
        }
        String shopName = "";
        if (account.getRule() != UserRule.BUSINESS) {
            shopName = account.getRule().getName();
        } else {
            shopName = account.getShop().getName();
        }
        String dirFrom = LOCAL_PATH + File.separator + TEMP;
        String dirTo = LOCAL_PATH + File.separator + shopName + File.separator
            + String.valueOf(order.getId());
        /**
         * 效果图
         */
        DataFile sketchImage = createDataFile(dirFrom, sketchFileName,
            sketchUUID, dirTo, FileType.Sketch);
        if (sketchImage == null) {
            logger.info("copy sketchFile fail.");
            return new ErrorVO(ErrorCode.ORDER_SKETCH_NO_EXIST);
        } else {
            sketchImage.setMd5Code(MD5Util.md5(new File(dirTo + File.separator
                + sketchFileName)));
            order.setSketch(sketchImage);
        }

        order.setStatus(Status.Normal);
        orderManager.update(order);

        MapVO mapVO = new MapVO();
        mapVO.put("ID", order.getId());

        logger
            .info(
                "Account:{}, method:{}, execute:{}, parameters:[OrderName={}, CustomerName={}, CustomerAddress={},Phone={}, Info={},Material={}], Result={}",
                new Object[] { account.getName(),
                    this.getClass().getSimpleName(), getAction(), orderName,
                    name, address, phone, description, material, order.getId() });

        return mapVO;

    }

    /**
     * 更改订单详情
     * 
     * @param parameters
     * @return
     */
    public BaseVO modifyOrder(WebParameters parameters) {
        Long id = parameters.getLong("ID");
        String name = parameters.getString("CustomerName");
        String address = parameters.getString("CustomerAddress");
        String phone = parameters.getString("Phone");
        String description = parameters.getString("Info");
        String material = parameters.getString("Material");
        String orderStatus = parameters.getString("OrderStatus");

        if (id == null) {
            return new ErrorVO(ErrorCode.PARAMETER_ERROR);
        }

        Order order = orderManager.getByIdUseOwner(account, id);
        if (order == null) {
            return new ErrorVO(ErrorCode.PARAMETER_ID_NO_EXIST);
        }

        Customer customer = order.getCustomer();
        if (name != null && !name.isEmpty()) {
            customer.setName(name);
        }
        if (address != null && !address.isEmpty()) {
            customer.setAddress(address);
        }
        if (phone != null && !phone.isEmpty()) {
            customer.setAddress(phone);
        }

        if (description != null && !description.isEmpty()) {
            order.setDescription(description);
        }
        if (material != null && !material.isEmpty()) {
            order.setMaterial(material);
        }

        if (orderStatus != null && !orderStatus.isEmpty()) {
            order.setOdStatus(orderStatus);
        }
        //更新修改时间
        order.setModifyTime(new Date());
        orderManager.update(order);

        logger
            .info(
                "Account:{}, method:{}, execute:{}, parameters:[ID={}, CustomerName={}, CustomerAddress={},Phone={}, Info={},Material={}, OrderStatus={}], Result={}",
                new Object[] { account.getName(),
                    this.getClass().getSimpleName(), getAction(), id, name,
                    address, phone, description, material, orderStatus,
                    "BaseVO" });
        return new BaseVO();
    }

    /**
     * 订单查询
     * 
     * @param parameters
     * @return
     */
    public BaseVO orderSearch(WebParameters parameters) {
        String phone = parameters.getString("Phone");

        if (phone == null || StringUtils.isEmpty(phone)) {
            return new ErrorVO(ErrorCode.PARAMETER_ERROR);
        }

        List<Order> list = orderManager.getAllByPhone(account, phone);
        if (list == null || list.isEmpty()) {
            return new BaseVO();
        }
        ListVO result = new ListVO();
        for (Order item: list) {
            OrderVO orderVO = new OrderVO(item);
            result.add(orderVO);
        }
        logger
            .info(
                "Account:{}, method:{}, execute:{}, parameters:[Phone={}], Result={}",
                new Object[] { account.getName(),
                    this.getClass().getSimpleName(), getAction(), phone, result });

        return result;
    }

    /**
     * 获取订单列表
     * 
     * @return
     */
    public BaseVO orderList() {
        List<Order> list = orderManager.getAll(account);
        if (list == null || list.isEmpty()) {
            return new ListVO();
        }
        ListVO result = new ListVO();
        for (Order item: list) {
            OrderVO orderVO = new OrderVO(item);
            result.add(orderVO);
        }
        logger.info(
            "Account:{}, method:{}, execute:{}, parameters:[], Result={}, Size:{}",
            new Object[] { account.getName(), this.getClass().getSimpleName(),
                getAction(), result, list.size() });
        return result;
    }

}
