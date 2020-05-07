package com.loosu.alog


abstract class Logger {

    abstract fun log(level: Level, tagObj: Any?, msgObj: Any?, throwable: Throwable?)
}