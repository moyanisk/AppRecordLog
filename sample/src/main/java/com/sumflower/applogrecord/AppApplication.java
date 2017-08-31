package com.sumflower.applogrecord;

import android.app.Application;

import com.sumflower.apploglib.LogRecordManager;
import com.sumflower.apploglib.LogUtil;

/**
 * Created by yunlala-web on 2017/8/29.
 */

public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        LogUtil.isDebug(true);
        LogRecordManager.getInstanceBuilder().build(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
