package com.loosu.alog;

import android.support.annotation.NonNull;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class FileLogger extends Logger {
    private static final String TAG = "FileLogger";

    private static long MIN_FILE_LEN = 100;
    private static long DEF_FILE_LEN = 2 * 1024 * 1024; // 2M

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", Locale.US);
    private final File baseDir;
    private final long maxFileLen;

    @NonNull
    private File curFile;
    private BufferedWriter writer;

    private FileLogger(File baseDir, long maxFileLen) {
        this.baseDir = baseDir;
        this.maxFileLen = maxFileLen < MIN_FILE_LEN ? MIN_FILE_LEN : maxFileLen;
        createNewIO();
    }

    @Override
    public void log(@NotNull Level level, @Nullable Object tagObj, @Nullable Object msgObj, @Nullable Throwable throwable) {
        try {
            final long fileLen = curFile.length();
            if (fileLen >= maxFileLen) {
                createNewIO();
            }

            StringBuilder sb = new StringBuilder()
                    .append(dateFormat.format(System.currentTimeMillis())).append('\t')
                    .append(level).append('/').append(tagObj).append('\t')
                    .append(msgObj).append('\n')
                    .append(Log.getStackTraceString(throwable));
            writer.write(sb.toString());
            writer.flush();

        } catch (Throwable ignore) {
            ignore.printStackTrace();
        }
    }

    private void createNewIO() {
        closeOldIO();
        try {
            if (!baseDir.exists()) {
                baseDir.mkdirs();
            }

            curFile = new File(baseDir, System.currentTimeMillis() + ".txt");
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(curFile)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            close(writer);
        }
    }

    private void closeOldIO() {
        final BufferedWriter old = writer;
        if (old != null) {
            try {
                old.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close(old);
            }
        }
    }

    private void close(@Nullable Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Builder {
        private File baseDir;
        private long maxFileLen = DEF_FILE_LEN;

        public Builder setBaseDir(@NonNull File baseDir) {
            this.baseDir = baseDir;
            return this;
        }

        public Builder setMaxFileLen(long maxFileLen) {
            this.maxFileLen = maxFileLen;
            return this;
        }

        public FileLogger build() {
            return new FileLogger(baseDir, maxFileLen);
        }
    }
}
