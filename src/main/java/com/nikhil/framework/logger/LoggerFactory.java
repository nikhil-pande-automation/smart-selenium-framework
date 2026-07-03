package com.nikhil.framework.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class LoggerFactory {

    /**
     * Provides Log4j2 logger instances.
     */

    private LoggerFactory() {
    }

    //No Singleton required because Log4j already manages logger instances internally.
    public static Logger getLogger(Class<?> clazz) {
        return LogManager.getLogger(clazz);
    }
}
