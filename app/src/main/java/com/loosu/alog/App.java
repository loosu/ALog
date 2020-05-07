package com.loosu.alog;

import android.app.Application;
import android.os.Environment;

import java.io.File;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FileLogger logger = new FileLogger.Builder()
                //.setBaseDir(getFilesDir())
                .setBaseDir(new File(Environment.getExternalStorageDirectory(), "alog"))
                .setMaxFileLen(1024 * 50)
                .build();
        ALog.get().addLogger(logger);
    }
}
