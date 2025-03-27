package com.damika.emailclient.handler;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    @EnsuresNonNull({ "#1", "#2" })
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
        System.err.println("Uncaught exception in thread: " + t.getName());
        e.printStackTrace();
    }
}
