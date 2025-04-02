package com.damika.emailclient.util;

import com.damika.emailclient.handler.GlobalExceptionHandler;

public class ExceptionHandlerUtil {
    public static void setupGlobalExceptionHandler() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(handler);
    }
}