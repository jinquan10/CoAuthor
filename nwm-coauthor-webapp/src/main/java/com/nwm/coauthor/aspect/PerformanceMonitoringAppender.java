package com.nwm.coauthor.aspect;

import java.io.Writer;

import org.apache.log4j.RollingFileAppender;

public class PerformanceMonitoringAppender extends RollingFileAppender{
    public Writer getWriter(){
        return qw;
    }
}
