/**
 * @(#)ListFlowerVO.java, 2013-7-19. 
 * 
 */
package fabric.server.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;

import fabric.common.web.BaseVO;
import fabric.server.entity.TagTypeEnum;

/**
 *
 * @author likaihua
 *
 */
public class ListFlowerVO extends BaseVO {
    protected List<FlowerTypeVO> array;
    
    public ListFlowerVO() {
        array = new ArrayList();
    }

    public void add(FlowerTypeVO obj) {
        array.add(obj);
    }

    @Override
    public Representation representation() {
        JSONArray jArr = new JSONArray();
        Map map;
        for (FlowerTypeVO item: array) {
            JSONObject jObj = new JSONObject(item);
            try {
                jObj.accumulate("styleIDs", item.listTag(TagTypeEnum.STYLE));
                jObj.accumulate("colorIDs", item.listTag(TagTypeEnum.COLOR));
                jObj.accumulate("elementIDs", item.listTag(TagTypeEnum.ElEMENTS));
                jObj.accumulate("recommendIDs", item.listTag(TagTypeEnum.RECOMMEND));
                jObj.accumulate("productIDs", item.listTag(TagTypeEnum.PRODUCT));
            } catch (JSONException e) {
                logger.info("Rep: 添加花型标签列表出错");
                e.printStackTrace();
            }
            jArr.put(jObj);
        }
        return new JsonRepresentation(jArr);
    }
}
