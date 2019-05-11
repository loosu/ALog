package com.loosu.alog.cache

import android.text.TextUtils
import com.loosu.alog.Level
import com.loosu.alog.CacheStrategy
import com.loosu.alog.Util
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

abstract class DiskCache(logDirPath: String) : CacheStrategy() {
    private val TAG = "TAG"

    private val DATA_FORMAT = SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", Locale.US)
    private val mCaches: ArrayList<String> = ArrayList()
    private val mLogDirPath: String = logDirPath

    override fun needSave(level: Level): Boolean {
        return when (level) {
            Level.D, Level.I, Level.W, Level.E -> true
            else -> false
        }
    }

    override fun onSave(level: Level, tagObj: Any?, msgObj: Any?, throwable: Throwable?) {

        // info about trace
        val traceElement = Thread.currentThread().stackTrace[4]
        val methodName = traceElement.methodName
        val fileName = traceElement.fileName
        val lineNumber = traceElement.lineNumber

        // tag str
        val tag = tagObj ?: TAG

        // msg str
        val msg = StringBuilder().append('(').append(fileName).append(':')
                .append(lineNumber).append(')')
                .append(methodName).append(" : ")
                .append(msgObj ?: "")
                .toString()

        // throwable str
        val exception = getTraceStr(throwable) ?: ""

        val cache = "${DATA_FORMAT.format(System.currentTimeMillis())} $level $tag $msg $exception"
        mCaches.add(cache)    // string format: "1990-01-01 00:00:00:00 D msgObj"

        if (startSaveCache(mCaches.size)) {
            val temp = ArrayList<String>()
            temp.addAll(mCaches)
            mCaches.clear()
            realSave(temp)
        }
    }

    /**
     * start to save memory log
     */
    abstract fun startSaveCache(cacheSize: Int): Boolean

    private fun realSave(logs: List<String>) {
        Observable.create<String> { emitter ->
            for (str in logs) {
                emitter.onNext(str)
            }
            emitter.onComplete()

        }.observeOn(Schedulers.io())
                .map { "$it\n" }
                .subscribe(object : Observer<String> {
                    var fos: FileOutputStream? = null
                    var bos: BufferedWriter? = null

                    override fun onSubscribe(d: Disposable) {
                        val dir = File(mLogDirPath)
                        if (!dir.exists()) {
                            dir.mkdirs()
                        }
                        val logFile = File("${mLogDirPath}log_${DATA_FORMAT.format(System.currentTimeMillis())}.txt")

                        try {
                            fos = FileOutputStream(logFile, true)
                            bos = BufferedWriter(OutputStreamWriter(fos))
                        } catch (e: Exception) {
                            //e.printStackTrace()
                            d.dispose()
                            Util.close(bos)
                            Util.close(fos)
                        }
                    }

                    override fun onNext(text: String) {
                        bos!!.write(text)
                    }

                    override fun onComplete() {
                        bos!!.flush()
                        Util.close(bos)
                        Util.close(fos)
                    }

                    override fun onError(e: Throwable) {
                        Util.close(bos)
                        Util.close(fos)
                    }
                })
    }

    private fun getTraceStr(throwable: Throwable?): String? {
        val traceStr = Util.getTraceStr(throwable)
        return if (TextUtils.isEmpty(traceStr)) {
            null
        } else {
            "\n\t$traceStr"
        }
    }
}