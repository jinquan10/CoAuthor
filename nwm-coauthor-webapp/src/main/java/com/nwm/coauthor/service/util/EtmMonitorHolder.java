package com.nwm.coauthor.service.util;

import etm.core.monitor.EtmMonitor;
import etm.core.monitor.EtmPoint;

/**
 * We store the configured EtmMonitor here so we can share the reference in all classes
 *
 */
public class EtmMonitorHolder {
    private static EtmMonitor etmMonitor = null;

    public static void setEtmMonitor(EtmMonitor mon) {
        etmMonitor = mon;
    }

    public static EtmMonitor getMonitor() {
        return etmMonitor;
    }

    public static EtmPoint startMonitorPoint(String point) {
        EtmMonitor monitor = EtmMonitorHolder.getMonitor();
        if (monitor == null) {
            return null;
        }

        return monitor.createPoint(point);
    }

    public static void collectMonitorPoint(EtmPoint point) {
        if (point == null) {
            return;
        }
        point.collect();
    }
}
