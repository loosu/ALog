package com.loosu.alog

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ALog.d(TAG, "hello!!")

        findViewById<View>(R.id.btn_log).setOnClickListener {
            ALog.i(TAG, "hello!!")
        }
    }
}
