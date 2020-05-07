package com.loosu.alog

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import java.lang.RuntimeException

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ALog.d(TAG, "hello!!")

        val testError = RuntimeException("test error...")
        findViewById<View>(R.id.btn_log).setOnClickListener {
            repeat(3) {
                ALog.i(TAG, "hello!! $it", testError)
            }
        }
    }
}
