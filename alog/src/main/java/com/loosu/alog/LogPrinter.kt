package com.loosu.alog


abstract class LogPrinter {
    fun isLogable(): Boolean = true

    fun printLog(level: Level, tagObj: Any?, msgObj: Any?, throwable: Throwable?) {
        if (isLogable()) {
            println(level, tagObj, msgObj, throwable)
        }
    }

    abstract fun println(level: Level, tagObj: Any?, msgObj: Any?, throwable: Throwable?)
}