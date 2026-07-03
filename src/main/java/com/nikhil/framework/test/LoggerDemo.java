package com.nikhil.framework.test;

import com.nikhil.framework.logger.LoggerFactory;
import org.apache.logging.log4j.Logger;

public class LoggerDemo {

    private static final Logger logger =
            LoggerFactory.getLogger(LoggerDemo.class);

    public static void main(String[] args) {

        logger.info("Framework Started");

        logger.error("Sample Error");

    }

}
