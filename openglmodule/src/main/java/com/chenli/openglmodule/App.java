package com.chenli.openglmodule;

import android.app.Application;

import com.chenli.commonlib.util.mainutil.Utils;

/**
 * Created by Lenovo on 2018/4/28.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
