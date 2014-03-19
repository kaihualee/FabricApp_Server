/**
 * @(#)ErrorCode.java, 2013-6-30. 
 * 
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package fabric.common.web;

/**
 *
 * @author nisonghai
 *
 */
public enum ErrorCode {
    
    /**
     * 一般错误信息
     */
    PARAMETER_ERROR(100, "参数出错"),
    PARAMETER_ID_NO_EXIST(101, "ID不存在"),
    PARAMETER_NAME_NO_EXIST(102, "名称不存在"),
    LIST_EMPTY(103, "列表为空"),
    DISABLE(104, "禁用状态"),
    DELETED(105, "删除状态"),
    PARAMETER_NAME_IS_EXIST(106, "名称已经存在"),
    DELETE_REJECT(107, "已被引用所以不能删除"),
    FILE_LOSING(108,"上传文件丢失"),
    OWNER_ERROR(109, "作者才能修改自己的作品"),
    UUID_NO_FOUND(110, "UUID没有找到"),
    XML_LOADING_ERROR(111, "XML加载失败"),
    FILE_UPLOAD_ERROR(112, "文件上传失败"),
    
    /**
     * 账户错误信息
     */
    ACCOUNT_RULE_ERROR(200, "用户角色错误"),
    ACCOUNT_NO_EXIST(201, "用户不存在"),
    ACCOUNT_DISABLE(202, "账户禁用"),
    ACCOUNT_PASSWORD_ERROR(203, "密码错误"),
    ACCOUNT_NAME_IS_EXISTED(204, "用户名已经存在"),
    ACCOUNT_PARAMETER_ERROR(205, "用户名或者密码错误"),
    ACCOUNT_CHECKNAME_NULL(206, "用户名不能为空"),
    
    /**
     * 商家错误信息
     */
    SHOP_NO_EXIST(301, "商家不存在"),
    SHOP_NAME_EXIST(302, "店家名已存在"),
    
    /**
     * 花型错误信息
     */
    FLOWERTYPE_FILE_NO_EXIST(402, "文件未上传"),
    FLOWERTYPE_EMPTY(403, "花型为空"),
    FLOWERTYPE_TAG_LOSE(404, "缺少花型标签参数"),
    FLOWERTYPE_TAG_NAME_NO_EXIST(405, "花型标签名不存在"),
    FLOWERTYPE_TAG_NAME_IS_EXIST(406, "花型标签名已经存在"),
    FLOWERTYPE_TAG_TYPE_NO_EXIST(407, "花型标签类型不存在"),
    FLOWERTYPE_FILE_NO_LOSE(408, "花型文件丢失"),
    FLOWERTYPE_TAG_COLOR_NO_EXIST(410, "花型标签(颜色)不存在"),
    FLOWERTYPE_TAG_STYLE_NO_EXIST(411, "花型标签(风格)不存在"),
    FLOWERTYPE_TAG_RECOMMEND_NO_EXIST(412, "花型标签(推荐)不存在"),
    FLOWERTYPE_TAG_ElEMENTS_NO_EXIST(413, "花型标签(元素)不存在"),
    FLOWERTYPE_TAG_PRODUCTS_NO_EXIST(414, "花型标签(产品)不存在"),
    /**
     * 方案错误信息
     */
    SCHEME_FILE_NO_EXIST(501, "方案文件未上传"),
    SCHEME_TAG_ID_NO_EXIST(502, "方案标签ID不存在"),
    SCHEME_ID_NO_EXIST(503, "方案ID不存在"),
    SCHEME_MAIN_FLOWER_NO_EXIST(504, "方案主花型ID不存在或者无权限"),
    SCHEME_FILE_NO_LOSE(505, "方案文件丢失"),
    SCHEME_ID_OR_NOPERMISSION(506, "方案ID不存在或无权限"),
    SCHEME_SETENABLE_ERROR(507, "set error"),
    SCHEME_SCENE_NO_EXIST(508, "方案场景ID不存在或者无权限"),
    SCHEME_FLOWERTYPE_ID_NO_EXIST_OR_NOPERMISSION(509, "方案包含的花型不存在或者无权限"),
    SCHEME_XML_ERROR(510, "方案XML解析出错"),
    
    /**
     * 花型标签错误信息
     */
    FLOWERTYPESTYLE_NAME_NO_EXIST(601, "花型风格名不存在"),
    
    /**
     * 场景错误信息
     */
    SCENE_FILE_NO_EXIST(701, "文件未上传"),
    SCENE_ID_NO_EXIST(702, "场景ID不存在或无权限"),
    /**
     * 订单错误信息
     */
    ORDER_PARAMETERS_NO_EXIST(801, "订单参数不全"),
    ORDER_SKETCH_NO_EXIST(802, "订单的效果图缺失"),
    ORDER_SKETCH_FILE_NO_LOSE(803, "订单的效果图丢失"),
    /**
     * 授权错误信息
     */
    GRANTTABLE_REPEAT(902, "重复授权"),
    GRANTABLE_NO_EXIST(903, "授权表不存在"),
    GRANTABLE_REJECT(904, "无权限授权"),
    GRANTABLE_GRANTSELF(905, "无法授权给自己"),
    /**
     * 系统错误信息
     */
    OPERATOR_ERROR(16, "操作失败"),
    SESSION_ID_NO_EXISTE(15, "无效会话ID"),
    NOPERMIDDION_OR_INVALID_ID(14, "ID不存在或者权限不够"),
    LOGIN_ERROR(13, "未登陆或登陆超时"),
    NOPERMISSIONS(12, "权限不足"),
    SYSTEM_JSON_PARSER_ERROR(16, "数据解析出错"),
    SYSTEM_ERROR(11, "系统错误"),
    SYSTEM_DB_ERROR(10, "数据库错误");
    
    
    private int code;
    
    private String message;
    
    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
