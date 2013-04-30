package com.nwm.coauthor.aspect;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Locale;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;

import etm.core.renderer.SimpleTextRenderer;

public class SimpleTextRendererLog extends SimpleTextRenderer {
    private static final Logger LOGGER = Logger.getLogger(SimpleTextRendererLog.class);
    private static Writer writer = null;
    private static boolean isLog4jWriter = false;

    static {
        if (LOGGER != null) {
            Enumeration<Appender> e = Logger.getRootLogger().getAllAppenders();

            while (e.hasMoreElements()) {
                Appender a = e.nextElement();

                if (a instanceof PerformanceMonitoringAppender) {
                    writer = ((PerformanceMonitoringAppender) a).getWriter();
                    isLog4jWriter = true;
                    break;
                }
            }

        }
    }

    public SimpleTextRendererLog() {
        super(initWriter(), Locale.getDefault());
    }

    protected static Writer initWriter() {
        if (writer == null) {
            return new OutputStreamWriter(System.out);
        } else {
            return writer;
        }
    }

    public boolean isLog4jWriter() {
        return isLog4jWriter;
    }
}
