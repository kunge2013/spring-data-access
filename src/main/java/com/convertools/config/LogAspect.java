package com.convertools.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@Aspect
@EnableAspectJAutoProxy
public class LogAspect {

    private static Log logger = LogFactory.getLog(LogAspect.class);

    @Pointcut("within(com.convertools.handler.*)")
    public void pointCut() {

    }

    @Before("pointCut()")
    public void beforePointCut(JoinPoint point) {
        logger.info("exec before .." + point.getSignature());
        System.out.println(point);
    }

    @After("pointCut()")
    public void afterPointCut(JoinPoint point) {
        logger.info("exec after .." + point.getSignature());
        System.out.println(point);
    }
}
