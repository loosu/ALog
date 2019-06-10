package com.loosu.alog.printer

import android.util.Log
import com.loosu.alog.Level
import com.loosu.alog.LogPrinter

class LogcatPrinter : LogPrinter() {

    private val mLogDef = "ALog"   // 默认 tag
    private val mMsgDef = ""

    override fun println(level: Level, tagObj: Any?, msgObj: Any?, throwable: Throwable?) {
        // info about trace
        val traceElement = Thread.currentThread().stackTrace[5]
        val methodName = traceElement.methodName
        val fileName = traceElement.fileName
        val lineNumber = traceElement.lineNumber

        // tag
        val tag = tagObj?.toString() ?: mLogDef

        // msg
        val msg = StringBuilder().append('(').append(fileName).append(':')
                .append(lineNumber).append(')')
                .append(methodName).append(" : ")
                .append(msgObj?.toString() ?: mMsgDef)
                .toString()

        when (level) {
            Level.V -> Log.v(tag, msg, throwable)
            Level.D -> Log.d(tag, msg, throwable)
            Level.I -> Log.i(tag, msg, throwable)
            Level.W -> Log.w(tag, msg, throwable)
            Level.E -> Log.e(tag, msg, throwable)
            Level.WTF -> Log.wtf(tag, msg, throwable)
            else -> throw IllegalArgumentException("no such level: $level")
        }
    }
}
