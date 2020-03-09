package com.loosu.alog;

import android.util.Log;

import com.loosu.alog.printer.LogcatPrinter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * A {@link Log} that can print more info.
 */
public final class ALog {

    private static List<LogPrinter> sLogs = new ArrayList<>();

    static {
        sLogs.add(new LogcatPrinter());
    }

    private ALog() {
        // util class no instance.
    }

    public static void v() {
        printLog(Level.V, null, null, null);
    }

    public static void v(@Nullable Object tag) {
        printLog(Level.V, tag, null, null);
    }

    public static void v(@Nullable Object tag, @Nullable Object msg) {
        printLog(Level.V, tag, msg, null);
    }

    public static void v(@Nullable Object tag, @Nullable Object msg, @Nullable Throwable tr) {
        printLog(Level.V, tag, msg, tr);
    }


    public static void d() {
        printLog(Level.D, null, null, null);
    }

    public static void d(@Nullable Object tag) {
        printLog(Level.D, tag, null, null);
    }

    public static void d(@Nullable Object tag, @Nullable Object msg) {
        printLog(Level.D, tag, msg, null);
    }

    public static void d(@Nullable Object tag, @Nullable Object msg, @Nullable Throwable tr) {
        printLog(Level.D, tag, msg, tr);
    }


    public static void i() {
        printLog(Level.I, null, null, null);
    }

    public static void i(@Nullable Object tag) {
        printLog(Level.I, tag, null, null);
    }

    public static void i(@Nullable Object tag, @Nullable Object msg) {
        printLog(Level.I, tag, msg, null);
    }

    public static void i(@Nullable Object tag, @Nullable Object msg, @Nullable Throwable tr) {
        printLog(Level.I, tag, msg, tr);
    }


    public static void w() {
        printLog(Level.W, null, null, null);
    }

    public static void w(@Nullable Object tag) {
        printLog(Level.W, tag, null, null);
    }

    public static void w(@Nullable Object tag, @Nullable Object msg) {
        printLog(Level.W, tag, msg, null);
    }

    public static void w(@Nullable Object tag, @Nullable Object msg, @Nullable Throwable tr) {
        printLog(Level.W, tag, msg, tr);
    }


    public static void e() {
        printLog(Level.E, null, null, null);
    }

    public static void e(@Nullable Object tag) {
        printLog(Level.E, tag, null, null);
    }

    public static void e(@Nullable Object tag, @Nullable Object msg) {
        printLog(Level.E, tag, msg, null);
    }

    public static void e(@Nullable Object tag, @Nullable Object msg, @Nullable Throwable tr) {
        printLog(Level.E, tag, msg, tr);
    }


    public static void wtf() {
        printLog(Level.WTF, null, null, null);
    }

    public static void wtf(@Nullable Object tag) {
        printLog(Level.WTF, tag, null, null);
    }

    public static void wtf(@Nullable Object tag, @Nullable Object msg) {
        printLog(Level.WTF, tag, msg, null);
    }

    public static void wtf(@Nullable Object tag, @Nullable Object msg, @Nullable Throwable tr) {
        printLog(Level.WTF, tag, msg, tr);
    }

    /**
     * 打印 log, 并对打印信息做一系列处理
     *
     * @param level     log 等级
     * @param tagObj    tag 标签
     * @param msgObj    msg 信息
     * @param throwable throwable 信息
     */
    private static void printLog(@NonNull Level level, @Nullable Object tagObj, @Nullable Object msgObj, @Nullable Throwable throwable) {
        Utils.Companion.requireNonNull(level, "level is null");

        LogPrinter[] logs = sLogs.toArray(new LogPrinter[0]);
        if (logs == null) {
            return;
        }

        for (LogPrinter log : logs) {
            log.printLog(level, tagObj, msgObj, throwable);
        }
    }
}
