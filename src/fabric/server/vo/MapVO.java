/**
 * @(#)MapVo.java, 2013-6-30. Copyright 2013 Netease, Inc. All rights reserved.
 *                 NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license
 *                 terms.
 */
package fabric.server.vo;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;

import fabric.common.web.BaseVO;

/**
 * @author nisonghai
 */
public class MapVO extends BaseVO {

    protected Map<String, Object> map;

    public MapVO() {
        map = new HashMap<String, Object>();
    }

    public MapVO(String key, Object value) {
        map = new HashMap<String, Object>();
        map.put(key, value);
    }

    public void put(String key, Object value) {
        map.put(key, value);
    }

    @Override
    public Representation representation() {
        return new JsonRepresentation(new JSONObject(map));
    }

}
