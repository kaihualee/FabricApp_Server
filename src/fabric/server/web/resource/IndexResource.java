/**
 * Copyright (c) www.webjvm.com 2009.<br>
 */
package fabric.server.web.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.lang.StringUtils;
import org.restlet.data.Form;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import fabric.server.entity.FlowerType;
import fabric.server.manager.FlowerTypeManager;


@Deprecated
public class IndexResource extends ServerResource {

    @Autowired
    private FreeMarkerConfig freemarkerConfig;
    
    @Autowired
    private FlowerTypeManager ftManager;
    
    private String action;

    @Get("html")
    public Representation index() {
    	List<FlowerType> lft = ftManager.getAll();
    	Map<Object, Object> dataModel = new HashMap<Object, Object>();
    	int nCount = 0;
    	for(FlowerType item : lft){
    		item = lft.get(nCount++);
    		dataModel.put("List"+nCount, item);
    	}
    	FlowerType ft = lft.get(0);
        //dataModel.put("list", lft);        
        return new JsonRepresentation(ft);
        
    	
    }
    
    @Put
    public String updateFlowerType(Representation entity){
    	Form form = new Form(entity);
    	FlowerType ft = new FlowerType();
    	if(!StringUtils.isNotEmpty(form.getFirstValue("flowerTypeName"))){
    		ft.setName(	form.getFirstValue("FlowerTypeName"));
    	}
    	return String.valueOf(ft.getId());
    }

}
