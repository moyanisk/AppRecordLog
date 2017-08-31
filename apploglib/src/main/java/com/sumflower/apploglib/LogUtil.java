package com.sumflower.apploglib;

import android.util.Log;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * 自定义Log打印工具类
 * 对系统的Log添加一层包装，可以设置是否为Debug模式，以及根据LogRecordManager中的的设置来控制是否记录Log到文件
 * Created by liu xuefei on 2017/8/29.
 */

public class LogUtil {

    private static boolean sDebug = false;

    public static void isDebug(boolean debug) {
        sDebug = debug;
        Log.d("LogUtil", "isDebug: app set debug status is : " + debug);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static void v(String tag, String message) {
        if (!sDebug) {
            return;
        }

        Log.v(tag, message);

        if (LogRecordManager.getRecordToFile()) {
            LogRecordManager.recordToFile(Log.VERBOSE, tag, message);
        }
    }

    public static void d(String tag, String message) {
        if (!sDebug) {
            return;
        }

        Log.d(tag, message);

        if (LogRecordManager.getRecordToFile()) {
            LogRecordManager.recordToFile(Log.DEBUG, tag, message);
        }
    }

    public static void d(String message) {
        if (!sDebug) {
            return;
        }

        Logger.d(message);

        if (LogRecordManager.getRecordToFile()) {
            LogRecordManager.recordToFile(Log.DEBUG, "", message);
        }
    }

    public static void i(String tag, String message) {
        if (!sDebug) {
            return;
        }

        Log.i(tag, message);

        if (LogRecordManager.getRecordToFile()) {
            LogRecordManager.recordToFile(Log.INFO, tag, message);
        }
    }

    public static void w(String tag, String message) {
        if (!sDebug) {
            return;
        }

        Log.w(tag, message);
        if (LogRecordManager.getRecordToFile()) {
            LogRecordManager.recordToFile(Log.WARN, tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (!sDebug) {
            return;
        }

        Log.e(tag, message);
        if (LogRecordManager.getRecordToFile()) {
            LogRecordManager.recordToFile(Log.ERROR, tag, message);
        }
    }
}
