package com.loosu.alog

import java.io.Closeable
import java.io.IOException
import java.io.PrintWriter
import java.io.StringWriter

/**
 * module internal util class
 */
internal class Util private constructor(/* util class no instance */) {
    companion object {
        fun close(closeable: Closeable?) {
            try {
                closeable?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        fun getTraceStr(t: Throwable?): String? {
            if (t == null) {
                return null
            }

            val stringWrite = StringWriter()
            val printWrite = PrintWriter(stringWrite)
            t.printStackTrace(printWrite)
            return stringWrite.buffer.toString()
        }
    }
}