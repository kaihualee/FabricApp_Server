/**
 * @(#)JsonArrayParameters.java, 2013-7-11. 
 * 
 */
package fabric.common.web;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * @author likaihua
 */
public class ArrayParameters {

    private JSONArray jArr;

    public ArrayParameters(String parameters) throws JSONException {
        if (parameters == null || StringUtils.isEmpty(parameters)) {
            throw new JSONException("parameters error");
        }
        jArr = new JSONArray(parameters);
    }

    public Long getLong(int index) {
        try {
            return jArr.getLong(index);
        } catch (JSONException e) {
            return null;
        }
    }

    public String getString(int index){
        try {
            return jArr.getString(index);
        } catch (JSONException e) {
            return null;
        }
    }
    
    public int length() {
        return jArr.length();
    }

}
