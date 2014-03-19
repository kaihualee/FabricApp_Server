/**
 * @(#)BaseResource.java, 2013-6-20. Copyright 2013 Netease, Inc. All rights
 *                        reserved. NETEASE PROPRIETARY/CONFIDENTIAL. Use is
 *                        subject to license terms.
 */
package fabric.common.web;

import org.apache.commons.lang.StringUtils;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import fabric.common.db.BaseEntity;
import fabric.common.db.BaseManager;
import fabric.common.utils.BeanUtils;

/**
 * Restlet base resource
 * 
 * @author nisonghai
 */
public abstract class BaseResource<T extends BaseEntity, M extends BaseManager<T>>
    extends ServerResource {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected WebParametersFactory webParametersFactory;

    @Get
    public Representation doGet() {
        String action = getAction();
        BaseVO vo = doAction(action, null);
        if (vo instanceof ErrorVO) {
            logger.info(
                "Do get action error, method:{}, excecute:{},  result:{}",
                new Object[] { this.getClass().getSimpleName(), action, vo });
        } else {
            logger.info(
                "Do get action success, method:{}, excecute:{},  result:{}",
                new Object[] { this.getClass().getSimpleName(), action, vo });
        }
        return vo.representation();
    }

    @Post
    public Representation doPost(Representation entity) {
        String action = getAction();
        WebParameters webParameters;
        try {
            webParameters = webParametersFactory.createWebParameters(entity);
        } catch (Exception e) {
            /**
             * 客户端如果使用post(null),自动定向委get()
             */
            webParameters = null;
            logger.info(
                "Do post action method:{}, excecute:{} ,without parameters",
                new Object[] { this.getClass().getSimpleName(), action });

            //return new ErrorVO(ErrorCode.SYSTEM_JSON_PARSER_ERROR)
            //    .representation();
        }
        Long startTime = System.currentTimeMillis();
        BaseVO vo = doAction(action, webParameters);
        Long endTime = System.currentTimeMillis();
        if (vo instanceof ErrorVO) {
            logger.info(
                "Do post action error, method:{}, excecute:{},  result:{}, Cost:{}ms.",
                new Object[] { this.getClass().getSimpleName(), action, vo, endTime-startTime });
        } else {
            logger.info(
                "Do get action success, method:{}, excecute:{},  result:{}, Cost:{}ms.",
                new Object[] { this.getClass().getSimpleName(), action, vo ,endTime-startTime });
        }
        return vo.representation();
    }

    protected String getAction() {
        String action = "list";
        if (StringUtils.isNotEmpty((String) getRequest().getAttributes().get(
            "action"))) {
            action = (String) getRequest().getAttributes().get("action");
        }
        return action;
    }

    protected BaseVO doAction(String action, WebParameters webParameters) {
        try {
            Object result = null;
            if (webParameters == null) {
                result = BeanUtils.invokePrivateMethod(this, action);
            } else {
                result = BeanUtils.invokePrivateMethod(this, action,
                    webParameters);
            }

            if (result == null) {
                return new ErrorVO(ErrorCode.SYSTEM_ERROR);
            }
            return (BaseVO) result;
        } catch (NoSuchMethodException e) {
            logger.error("Do action function error, action = " + action, e);
            return new ErrorVO(ErrorCode.SYSTEM_ERROR);
        }
    }

    protected int getPageNum() {
        int pageNum = 1;
        if (StringUtils.isNotEmpty((String) getRequest().getAttributes().get(
            "pageNum"))) {
            pageNum = Integer.valueOf(((String) getRequest().getAttributes()
                .get("pageNum")));
        }
        return pageNum;
    }

    /*
     * protected Representation list() { int pageNum = getPageNum(); PageBean
     * pageBean = new PageBean(); pageBean.setPageNum(pageNum);
     * pageBean.setMaxResults(10); List<T> list = getManager().getAll(pageBean);
     * JSONArray jsonArray = new JSONArray(); for (T t: list) { JSONObject
     * jsonObject = new JSONObject(t); jsonArray.put(jsonObject); } return new
     * JsonRepresentation(jsonArray); }
     */
    protected abstract M getManager();

}
