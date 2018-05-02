package com.chenli.androiddemo;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by Lenovo on 2018/4/28.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        if (throwable != null){
            throwable.printStackTrace(printWriter);
            Throwable cause = throwable.getCause();
        }
        //信息就在这里面
        String s = writer.toString();
    }
}
