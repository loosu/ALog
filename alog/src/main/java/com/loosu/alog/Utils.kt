package com.loosu.alog

import android.util.Log
import java.io.Closeable
import java.io.IOException


internal class Utils private constructor() {

    companion object {
        /**
         * 空引用校验, 如果校验对象为空，抛出异常.
         * @param obj     需要校验对象
         * @param message 异常信息
         */
        fun <T> requireNonNull(obj: T?, message: String?): T {
            if (obj == null) {
                throw NullPointerException(message)
            }
            return obj
        }

        /**
         * 获取异常信息
         */
        fun getStackTraceString(tr: Throwable?): String? {
            return Log.getStackTraceString(tr)
        }

        /**
         * 关闭流
         */
        fun close(closeable: Closeable?) {
            try {
                closeable?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
