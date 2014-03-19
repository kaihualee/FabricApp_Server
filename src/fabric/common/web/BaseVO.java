/**
 * @(#)BaseVO.java, 2013-6-30. Copyright 2013 Netease, Inc. All rights reserved.
 *                  NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license
 *                  terms.
 */
package fabric.common.web;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fabric.common.utils.BeanUtils;

/**
 * @author nisonghai
 */
public class BaseVO {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    
    
    public Representation representation()  { 
        return new JsonRepresentation(new JSONObject(this));
    }
        /*  {
    }
       
        Map<String, Object> map = new HashMap<String, Object>();
        Class<?> clazz = this.getClass();
        Method method = null;
        try{
        for (Class<?> superClass = clazz; superClass != BaseVO.class; superClass = superClass.getSuperclass()) {
            logger.info("superClass=" + superClass.getName());
            List<String> propertys = BeanUtils.getPropertyNames(superClass);
               for (String propertyName: propertys) {
                   Object propertyValue = BeanUtils.forceGetProperty(this,
                       propertyName);
                   String key = propertyName.substring(0, 1).toUpperCase()
                       + propertyName.substring(1, propertyName.length());
                   map.put(key, propertyValue);
               }
        }
        }catch(Exception e){
            logger.info("BaseVO Representation failed", e);
            return new ErrorVO(ErrorCode.SYSTEM_ERROR).getRepresentation();
        }
        
        return new JsonRepresentation(new JSONObject(map));
    }
    */

}
