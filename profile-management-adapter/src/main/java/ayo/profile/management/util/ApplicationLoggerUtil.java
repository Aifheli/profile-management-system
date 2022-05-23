package ayo.profile.management.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Aifheli Maganya on 2022/05/19.
 */
public class ApplicationLoggerUtil {

    private static ConcurrentHashMap<Class, Logger> cachedLoggers = new ConcurrentHashMap<Class,Logger>();
    public static ThreadLocal<String> xCorrelationID = new ThreadLocal<String>();

    private static String formatString = "application=%s, event=%s, message=%s, xCorrelationID=%s";
    private static String formatString_fields = "application=%s event=%s, message=%s,  %s, xCorrelationID=%s";
    private static String errorString = "application=%s event=%s, xCorrelationID=%s";
    private static String errorStringRes = "application=%s event=%s, traceId=%s, responseErrors=%s";

    public static void info(Class clazz, String system, String message, String event) {
        getLogger(clazz).info(String.format(formatString,system,event,message, getxCorrelationID()));
    }

    public static void info(Class clazz, String system, String message, String fields, String event) {
        getLogger(clazz).info(String.format(formatString_fields,system, event, message, fields, getxCorrelationID()));
    }

    public static void debug(Class clazz,  String system, String message, String event){
        getLogger(clazz).debug(String.format(formatString, system, event,message, getxCorrelationID()));
    }

    public static void trace(Class clazz,String message,String event) {
        getLogger(clazz).trace(String.format(formatString,event,message, getxCorrelationID()));
    }

    public static void warn(Class clazz, String system, String message,String event) {
        getLogger(clazz).warn(String.format(formatString,system, event, message, getxCorrelationID()));
    }

    public static void error(Class clazz, String system, String event, String obj, Throwable throwable) {
        getLogger(clazz).error(String.format(errorStringRes, system, event, getxCorrelationID(), obj), throwable);
    }

    public static void error(Class clazz, String system, String event, Throwable throwable) {
        getLogger(clazz).error(String.format(errorString, system, event, getxCorrelationID()) ,throwable);
    }

    private static Logger getLogger(Class clazz) {
        Logger result = cachedLoggers.putIfAbsent(clazz, LoggerFactory.getLogger(clazz));
        result = (result==null)? cachedLoggers.get(clazz):result;
        return result;
    }

    public static String getxCorrelationID() {
        return xCorrelationID.get();
    }

    public static void setxCorrelationID(String suid) {
        xCorrelationID.set(suid);
    }
}

