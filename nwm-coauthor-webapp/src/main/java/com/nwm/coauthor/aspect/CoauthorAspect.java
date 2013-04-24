package com.nwm.coauthor.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nwm.coauthor.util.CustomETMLogger;

import etm.core.aggregation.BufferedTimedAggregator;
import etm.core.aggregation.RootAggregator;
import etm.core.configuration.BasicEtmConfigurator;
import etm.core.configuration.EtmAggregatorConfig;
import etm.core.configuration.EtmManager;
import etm.core.configuration.EtmMonitorConfig;
import etm.core.configuration.EtmMonitorFactory;
import etm.core.monitor.EtmMonitor;
import etm.core.monitor.EtmPoint;
import etm.core.renderer.SimpleTextRenderer;

@Aspect
public class CoauthorAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoauthorAspect.class);
    private static EtmMonitor etmMonitor = null;
    
    static{
//        EtmAggregatorConfig root = new EtmAggregatorConfig();
//        root.setAggregatorClass(RootAggregator.class);
//
//        EtmAggregatorConfig log4j = new EtmAggregatorConfig();
//        log4j.setAggregatorClass(CustomETMLogger.class);
//        log4j.addProperty("logName", "etm.log");
//        
//        EtmMonitorConfig config = new EtmMonitorConfig();
//        config.setAggregatorRoot(root);
//        config.appendAggregator(log4j);
//        config.setMonitorType("nested");
        
        BasicEtmConfigurator.configure();
        
        try {
            etmMonitor = EtmManager.getEtmMonitor();
            etmMonitor.start();
        } catch (Exception e) {
            
        }
    }
    
    @Around("execution(* com.nwm.coauthor.service.controller..*.*(..))"/* + "@annotation(com.nwm.coauthor.service.aspect.AspectAnnotation)" */)
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
//        if(!LOGGER.isDebugEnabled()){
//            return pjp.proceed();
//        }
        
        EtmPoint etmPoint = null;
        Object result = null;

        // input sanity test
        if (pjp == null) {
            throw new RuntimeException("aop error, join point is null for collectMetricsForMethod");
        }

        try {
            // start timing here. We don't give the point a name because we're
            // waiting to see if it succeeds.
            etmPoint = etmMonitor.createPoint(null);

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
                etmPoint.collect();
                etmMonitor.render(new SimpleTextRenderer());
            }
        }

        // if no exception, return whatever value the profiled method returned.
        return result;
    }
}
