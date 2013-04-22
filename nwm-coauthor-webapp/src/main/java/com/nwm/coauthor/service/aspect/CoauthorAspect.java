package com.nwm.coauthor.service.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import com.nwm.coauthor.service.util.EtmMonitorHolder;

import etm.core.monitor.EtmPoint;

@Aspect
public class CoauthorAspect {
    @Around("execution(* *(..))"/* + "@annotation(com.nwm.coauthor.service.aspect.AspectAnnotation)" */)
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        EtmPoint etmPoint = null;
        Object result = null;

        // input sanity test
        if (pjp == null) {
            throw new RuntimeException("aop error, join point is null for collectMetricsForMethod");
        }

        try {
            // start timing here. We don't give the point a name because we're
            // waiting to see if it succeeds.
            etmPoint = EtmMonitorHolder.startMonitorPoint(null);

            // this is calling the method we're profiling.
            result = pjp.proceed();

            // if we get here, there was no exception thrown, so we add the word
            // "success" to the
            // mbean name.
            etmPoint.alterName(pjp.toShortString() + " (success)");
        } catch (Throwable t) {

            // if we get here, presumably the method we're profiling threw an
            // exception, so we add
            // the word "failure" to the mbean name
            if (etmPoint != null) {
                etmPoint.alterName(pjp.toShortString() + " (failure)");
            }

            // profiling should not affect program execution, so we rethrow the
            // exception we got
            // to keep behavior the same.
            throw t;
        } finally {

            // finishing profiling is put in a finally block, this works whether
            // or not an exception occurs.
            if (etmPoint != null) {
                EtmMonitorHolder.collectMonitorPoint(etmPoint);
            }
        }

        // if no exception, return whatever value the profiled method returned.
        return result;
    }
}
