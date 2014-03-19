/**
 * @(#)StyleVO.java, 2013-7-2. 
 * 
 */
package fabric.server.vo;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;

import fabric.common.db.StyleEntityImpl;
import fabric.common.utils.BeanUtils;
import fabric.common.web.BaseVO;
import fabric.common.web.ErrorCode;
import fabric.common.web.ErrorVO;
import fabric.server.entity.FlowerType;
import fabric.server.entity.FlowerTypeTagType;

/**
 *
 * @author likaihua
 *
 */
public class StyleVO extends BaseVO{
    /**
     * ID
     */
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 版本号
     */
    private int version;
    
    public StyleVO(){}
    public StyleVO(FlowerType entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.version = entity.getVersion();
    }
    public StyleVO(FlowerTypeTagType entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.version = 0;
    }
    
    public StyleVO(StyleEntityImpl entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.version = entity.getVersion();
    }
    /**
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @return
     */
    public int getVersion() {
        return version;
    }

    @Override
    public Representation representation() {
        Map map = new HashMap<String, Object>();
        try {
            List<String> propertys = BeanUtils.getPropertyNames(getClass());
            for (String propertyName: propertys) {
                Object propertyValue = BeanUtils.forceGetProperty(this,
                    propertyName);
                String key = propertyName.substring(0, 1).toUpperCase()
                    + propertyName.substring(1, propertyName.length());
                map.put(key, propertyValue);
            }

        } catch (Exception e) {
            return new ErrorVO(ErrorCode.SYSTEM_ERROR).representation();
        }

        return new JsonRepresentation(new JSONObject(map));
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @param version
     */
    public void setVersion(int version) {
        this.version = version;
    }
}
