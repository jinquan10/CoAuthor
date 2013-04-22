package com.nwm.coauthor.service.util;


import etm.contrib.integration.spring.web.SpringEtmMonitorContextSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;

/**
 * This class will be called by Spring AFTER the applicationContext.xml is read. Here we will initialize the EtmMonitor
 * programmatic unless we disable performance monitoring in the properties file.
 * 
 * The whole idea here is to be able to enable/disable the Jetm Monitors using the properties files
 */
public class BeanPostProcessor implements BeanFactoryPostProcessor, ApplicationContextAware {
    private static final Logger LOG = LoggerFactory.getLogger(BeanPostProcessor.class);
    
    private String performanceMonitorEnabled;
    
    private ApplicationContext mainContext;

    public void postProcessBeanFactory(ConfigurableListableBeanFactory context) throws BeansException {
        BeanDefinitionRegistry registry = ((BeanDefinitionRegistry) context);

        /*
         * find the monitor bean name
         */
        String[] names = registry.getBeanDefinitionNames();
        String nestedMonitorBeanName = null;
        for (String beanName : names) {
            if (beanName.contains("NestedMonitor")) {
                nestedMonitorBeanName = beanName;
                break;
            }
        }
        
        if (nestedMonitorBeanName == null) {
            LOG.error("Cannot find the EtmMonitor bean name. The Service will run with the performance monitors turned off"); 
            EtmMonitorHolder.setEtmMonitor(null);            
            return;
        }
        

        
        try {
            EtmMonitorHolder.setEtmMonitor(SpringEtmMonitorContextSupport.locateEtmMonitor(mainContext, null));
        } catch (ServletException sEx) { 
                LOG.error("Cannot locate EtmMonitor. The Service will run with the performance monitors turned off", sEx); 
                EtmMonitorHolder.setEtmMonitor(null);            
        }
    }

    public void setApplicationContext(ApplicationContext mainContext) {
        this.mainContext = mainContext;
    }

    
    /**
     * @return the performanceMonitorEnabled
     */
    public String getPerformanceMonitorEnabled() {
        return performanceMonitorEnabled;
    }

    
    /**
     * @param performanceMonitorEnabled the performanceMonitorEnabled to set
     */
    public void setPerformanceMonitorEnabled(String performanceMonitorEnabled) {
        this.performanceMonitorEnabled = performanceMonitorEnabled;
    }
}