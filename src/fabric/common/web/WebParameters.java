package fabric.common.web;


public class WebParameters {
    
    private WebParametersHelper helper;
    
    public WebParameters(WebParametersHelper helper) {
        this.helper = helper;
    }
    
    public String getString(String key) {
        String result = helper.getString(key);
        if("null".equals(result))
            return null;
        return result;
    }
    public Long getLong(String key){
        return helper.getLong(key);
    }
}
