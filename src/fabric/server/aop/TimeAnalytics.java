/**
 * @(#)TimeAnalytics.java, 2013-9-24. 
 * 
 */
package fabric.server.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author likaihua
 */
@Component("timeAnalytics")
@Aspect
public class TimeAnalytics {
    public TimeAnalytics() {}

    protected final Logger logger = LoggerFactory
        .getLogger(TimeAnalytics.class);

    @Pointcut("execution(public * fabric.server.dao.impl.*.*(..)) || execution(public * fabric.common.db.*.*(..))")
    public void timecost() {}

    @Around("timecost()")
    public Object watch(ProceedingJoinPoint joinpoint) {
        Long startTime = System.currentTimeMillis();
        Object ret = null;
        try {
            ret = joinpoint.proceed();
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Long endTime = System.currentTimeMillis();
        logger.info("Target:{};Execute method:{};Cost:{}ms", new Object[] {
            joinpoint.getTarget(), joinpoint.getSignature(),
            (endTime - startTime) });
        return ret;
    }

}
