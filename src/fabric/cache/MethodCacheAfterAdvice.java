/**
 * @(#)MethodCacheAfterAdvice.java, 2013-8-26. 
 * 
 */
package fabric.cache;

import java.lang.reflect.Method;
import java.util.List;


import net.sf.ehcache.Cache; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * @author likaihua
 */
public class MethodCacheAfterAdvice implements InitializingBean,
    AfterReturningAdvice {

    private static final Logger logger = LoggerFactory
        .getLogger(MethodCacheAfterAdvice.class);

    private Cache cache;

    /**
     * @param cache
     *            the cache to set
     */
    public void setCache(Cache cache) {
        this.cache = cache;
    }

    public MethodCacheAfterAdvice() {   
        super();   
    }   
    
    @Override
    public void afterReturning(Object arg0, Method arg1, Object[] arg2,
        Object arg3) throws Throwable {
       String className = arg3.getClass().getName();
       List list = cache.getKeys();
       for(int nLoop =0; nLoop < list.size(); ++nLoop){
           String cacheKey = String.valueOf(list.get(nLoop));
           if(cacheKey.startsWith(className)){
               cache.remove(cacheKey);
               logger.debug("remove cache" + cacheKey);
           }
       }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(cache,
            "Need a cache. Please use setCache(Cache) create it.");
    }

}
