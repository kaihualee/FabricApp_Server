/**
 * @(#)WebParametersFactory.java, 2013-7-1. 
 * 
 */
package fabric.common.web;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author likaihua
 */
@Service
public class WebParametersFactory {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static String PARAMETERS_NAME = "Parameters";

    public WebParameters createWebParameters(Representation entity)
        throws Exception {
        if (entity == null) {
            return null;
        }
        Form form = new Form(entity);
        return new WebParameters(new JsonWebParameters(form));
    }

    public ArrayParameters createArrayParameters(WebParameters parameters,
        String key) {
        if (parameters == null) {
            logger.debug("param is empty");
            return null;
        }
        if (key == null || key.isEmpty()) {
            logger.debug("key is empty");
            return null;
        }
        String value = parameters.getString(key);
        if (value == null) {
            logger.debug("create ArrayParameters require {} .", value);
        }
        ArrayParameters result;
        try {
            result = new ArrayParameters(value);
        } catch (Exception e) {
            logger.debug("create ArrayParameters failed.");
            result = null;
        }
        return result;
    }

    class JsonWebParameters implements WebParametersHelper {

        private JSONObject jo;

        public JsonWebParameters(Form form) throws JSONException {
            String parameters = form.getFirstValue(PARAMETERS_NAME);
            try {
                jo = new JSONObject(parameters);
            } catch (JSONException e) {
                logger.warn("Parser json data error, {}", parameters);
                throw e;
            }
        }

        public JsonWebParameters() {}

        @Override
        public String getString(String key) {
            try {
                return jo.getString(key);
            } catch (JSONException e) {
                logger.info("getString: " + key + " failed.");
                return null;
            }
        }

        @Override
        public Long getLong(String key) {
            try {
                return jo.getLong(key);
            } catch (JSONException e) {
                logger.info("getString: " + key + " failed.");
                return null;
            }
        }

    }

}
