/**
 * @(#)MethodCacheInterceptor.java, 2013-8-26. 
 * 
 */
package fabric.cache;


import java.io.Serializable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * @author likaihua
 */
public class MethodCacheInterceptor implements MethodInterceptor,InitializingBean{

    protected  final Logger logger = LoggerFactory.getLogger(getClass());

    private Cache cache;
    public MethodCacheInterceptor() {   
        super();   
    }   
    /**
     * @param cache
     */
    public void setCache(Cache cache) {
        this.cache = cache;
    }

    public Object invoke(MethodInvocation invocation) throws Throwable{
        String targetName = invocation.getThis().getClass().getName();
        String methodName = invocation.getMethod().getName();
        Object[] arguments = invocation.getArguments();
        Object result;
        
        logger.info("Find object from cache is " + cache.getName());   
        
        String cacheKey = getCacheKey(targetName, methodName, arguments);
        Element element= cache.get(cacheKey);
        if(element == null){
            logger.info("Hold up method , Get method result and create cache........!");
            result = invocation.proceed();
            if(result == null){
                logger.info("result:null");
                return result;
            }
            element = new Element(cacheKey, result);
            cache.put(element);
        }else{
            logger.info("Get result={} from cache with key:{}.", 
                new Object[]{element.getObjectValue(), cacheKey});
        }
        return element.getObjectValue();
    }

    protected String getCacheKey(String targetName, String methodName,
        Object[] arguments) {
        StringBuffer sb = new StringBuffer();
        sb.append(targetName).append(".").append(methodName);
        if ((arguments != null) && (arguments.length != 0)) {
            for (int nLoop = 0; nLoop < arguments.length; ++nLoop) {
                sb.append(".").append(arguments[nLoop]);
            }
        }
        return sb.toString();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(cache, "Need a cache. Please use setCache(Cache) create it."); 
    }
}
