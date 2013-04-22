package com.nwm.coauthor.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import etm.contrib.aggregation.log.Log4jAggregator;
import etm.core.aggregation.Aggregator;
import etm.core.monitor.EtmPoint;
import etm.core.renderer.SimpleTextRenderer;

public class CustomETMLogger extends Log4jAggregator{
    private static final Logger logger = LoggerFactory.getLogger(CustomETMLogger.class);
    
    public CustomETMLogger(Aggregator aAggregator) {
        super(aAggregator);
    }

    @Override
    protected void logMeasurement(EtmPoint aPoint) {
        if(logger.isDebugEnabled()){
            delegate.render(new SimpleTextRenderer());
            super.logMeasurement(aPoint);
        }
    }
}
