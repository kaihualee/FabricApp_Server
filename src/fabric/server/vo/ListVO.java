/**
 * @(#)ListVO.java, 2013-7-2. 
 * 
 */
package fabric.server.vo;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;

import fabric.common.utils.BeanUtils;
import fabric.common.web.BaseVO;

/**
 * @author likaihua
 */
public class ListVO<T> extends BaseVO {

    protected List<T> array;

    public ListVO() {
        array = new ArrayList();
    }

    public void add(T obj) {
        array.add(obj);
    }

    @Override
    public Representation representation() {
        JSONArray jArr = new JSONArray();
        Map map;
        for (T item: array) {
            JSONObject jObj = new JSONObject(item);
            jArr.put(jObj);
        }
        return new JsonRepresentation(jArr);
    }

}
