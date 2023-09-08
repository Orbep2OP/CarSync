package util;

import org.apache.log4j.Logger;

public class LogUtil {
    private static final Logger logger = Logger.getLogger(LogUtil.class);

    public static void debug(String message) {
        logger.debug(message);
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void warn(String message) {
        logger.warn(message);
    }

    public static void error(String message) {
        logger.error(message);
    }

    public static void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    public static void fatal(String message) {
        logger.fatal(message);
    }

    public static void fatal(String message, Throwable throwable) {
        logger.fatal(message, throwable);
    }
}