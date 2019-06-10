package com.loosu.alog.printer

import com.loosu.alog.Level
import com.loosu.alog.LogPrinter
import com.loosu.alog.Utils
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

class FilePrinter(logDirPath: String) : LogPrinter() {

    private val mLogDirPath: String = logDirPath

    private val mLogDef = "TAG"   // 默认 tag
    private val mMsgDef = ""

    private val DATA_FORMAT = SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", Locale.US)

    override fun println(level: Level, tagObj: Any?, msgObj: Any?, throwable: Throwable?) {

        // info about trace
        val traceElement = Thread.currentThread().stackTrace[4]
        val methodName = traceElement.methodName
        val fileName = traceElement.fileName
        val lineNumber = traceElement.lineNumber

        // tag str
        val tag = tagObj ?: mLogDef

        // msg str
        val msg = StringBuilder().append('(').append(fileName).append(':')
                .append(lineNumber).append(')')
                .append(methodName).append(" : ")
                .append(msgObj ?: "")
                .toString()


    }

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
                            Utils.close(bos)
                            Utils.close(fos)
                        }
                    }

                    override fun onNext(text: String) {
                        bos!!.write(text)
                    }

                    override fun onComplete() {
                        bos!!.flush()
                        Utils.close(bos)
                        Utils.close(fos)
                    }

                    override fun onError(e: Throwable) {
                        Utils.close(bos)
                        Utils.close(fos)
                    }
                })
    }
}