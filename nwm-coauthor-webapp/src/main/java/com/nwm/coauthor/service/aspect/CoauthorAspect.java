package com.nwm.coauthor.service.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class CoauthorAspect {
    @Around("execution(* *(..)) &&" + "@annotation(com.nwm.coauthor.service.aspect.AspectAnnotation)")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        Object retVal = pjp.proceed();
        
        System.out.println(System.currentTimeMillis());
        
        return retVal;
    }
}
