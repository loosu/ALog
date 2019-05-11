package com.loosu.alog;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;


import java.util.Objects;

/**
 * log 工具类，为了保证调用栈的深度该类使用Java代码编写(Kotlin 静态方法 在Java调用与在Kotlin调用时栈的深度是不一样的)
 */
public final class ALog {
    private static final String TAG = "ALog";

    private static String sLogDef = TAG;   // 默认 tag
    private static String sMsgDef = "";


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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(level, "level is null");
        }

        // info about trace
        final StackTraceElement traceElement = Thread.currentThread().getStackTrace()[4];
        final String methodName = traceElement.getMethodName();
        final String fileName = traceElement.getFileName();
        final int lineNumber = traceElement.getLineNumber();

        // tag
        final String tag = tagObj == null ? sLogDef : tagObj.toString();

        // msg
        final String msg = new StringBuilder().append('(').append(fileName).append(':')
                .append(lineNumber).append(')')
                .append(methodName).append(" : ")
                .append(msgObj == null ? sMsgDef : msgObj.toString())
                .toString();

        switch (level) {
            case V:
                Log.v(tag, msg, throwable);
                break;
            case D:
                Log.d(tag, msg, throwable);
                break;
            case I:
                Log.i(tag, msg, throwable);
                break;
            case W:
                Log.w(tag, msg, throwable);
                break;
            case E:
                Log.e(tag, msg, throwable);
                break;
            case WTF:
                Log.wtf(tag, msg, throwable);
                break;
            default:
                throw new IllegalArgumentException("no such level: " + level);
        }
    }
}
