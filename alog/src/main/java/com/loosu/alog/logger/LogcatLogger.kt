package com.loosu.alog.logger

import android.util.Log
import com.loosu.alog.Level
import com.loosu.alog.Logger

internal class LogcatLogger : Logger() {

    private val mLogDef = "ALog"   // 默认 tag
    private val mMsgDef = ""

    override fun log(level: Level, tagObj: Any?, msgObj: Any?, throwable: Throwable?) {
        // info about trace
        val traceElement = Thread.currentThread().stackTrace[5]
        val fileName = traceElement.fileName
        val lineNumber = traceElement.lineNumber

        // tag
        val tag = tagObj?.toString() ?: mLogDef

        // msg
        val msg = StringBuilder()
                .append(msgObj?.toString() ?: mMsgDef)
                .append('(').append(fileName).append(':').append(lineNumber).append(')')
                .toString()

        when (level) {
            Level.V -> Log.v(tag, msg, throwable)
            Level.D -> Log.d(tag, msg, throwable)
            Level.I -> Log.i(tag, msg, throwable)
            Level.W -> Log.w(tag, msg, throwable)
            Level.E -> Log.e(tag, msg, throwable)
            else -> Log.wtf(tag, msg, throwable)
        }
    }
}
