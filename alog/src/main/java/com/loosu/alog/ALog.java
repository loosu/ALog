package com.loosu.alog;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.loosu.alog.logger.LogcatLogger;

/**
 * A {@link Log} that can print more info.
 */
public final class ALog {
    private final List<Logger> loggers = new ArrayList<>();

    private static final ALog INSTANCE = new ALog();
    private boolean enable = true;

    private ALog() {
        loggers.add(new LogcatLogger());
    }

    public static ALog get() {
        return INSTANCE;
    }

    public static void v(@Nullable Object tag) {
        get().printLog(Level.V, tag, null, null);
    }

    public static void v(@Nullable Object tag, @Nullable Object msg) {
        get().printLog(Level.V, tag, msg, null);
    }

    public static void v(@Nullable Object tag, @Nullable Object msg, @Nullable Throwable tr) {
        get().printLog(Level.V, tag, msg, tr);
    }


    public static void d(@Nullable Object tag) {
        get().printLog(Level.D, tag, null, null);
    }

    public static void d(@Nullable Object tag, @Nullable Object msg) {
        get().printLog(Level.D, tag, msg, null);
    }

    public static void d(@Nullable Object tag, @Nullable Object msg, @Nullable Throwable tr) {
        get().printLog(Level.D, tag, msg, tr);
    }


    public static void i(@Nullable Object tag) {
        get().printLog(Level.I, tag, null, null);
    }

    public static void i(@Nullable Object tag, @Nullable Object msg) {
        get().printLog(Level.I, tag, msg, null);
    }

    public static void i(@Nullable Object tag, @Nullable Object msg, @Nullable Throwable tr) {
        get().printLog(Level.I, tag, msg, tr);
    }


    public static void w(@Nullable Object tag) {
        get().printLog(Level.W, tag, null, null);
    }

    public static void w(@Nullable Object tag, @Nullable Object msg) {
        get().printLog(Level.W, tag, msg, null);
    }

    public static void w(@Nullable Object tag, @Nullable Object msg, @Nullable Throwable tr) {
        get().printLog(Level.W, tag, msg, tr);
    }


    public static void e(@Nullable Object tag) {
        get().printLog(Level.E, tag, null, null);
    }

    public static void e(@Nullable Object tag, @Nullable Object msg) {
        get().printLog(Level.E, tag, msg, null);
    }

    public static void e(@Nullable Object tag, @Nullable Object msg, @Nullable Throwable tr) {
        get().printLog(Level.E, tag, msg, tr);
    }


    public static void wtf(@Nullable Object tag) {
        get().printLog(Level.WTF, tag, null, null);
    }

    public static void wtf(@Nullable Object tag, @Nullable Object msg) {
        get().printLog(Level.WTF, tag, msg, null);
    }

    public static void wtf(@Nullable Object tag, @Nullable Object msg, @Nullable Throwable tr) {
        get().printLog(Level.WTF, tag, msg, tr);
    }

    public boolean isEnable() {
        return enable;
    }

    /**
     * 打印 log, 并对打印信息做一系列处理
     *
     * @param level     log 等级
     * @param tagObj    tag 标签
     * @param msgObj    msg 信息
     * @param throwable throwable 信息
     */
    private void printLog(@NonNull Level level, @Nullable Object tagObj, @Nullable Object msgObj, @Nullable Throwable throwable) {
        if (!isEnable()) {
            return;
        }

        Utils.Companion.requireNonNull(level, "level is null");

        Logger[] logs = loggers.toArray(new Logger[0]);

        for (Logger log : logs) {
            log.log(level, tagObj, msgObj, throwable);
        }
    }

    public void addLogger(@NonNull Logger logger) {
        Utils.Companion.requireNonNull(logger, "logger is null");
        loggers.add(logger);
    }
}
