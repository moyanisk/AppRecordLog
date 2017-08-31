package com.sumflower.apploglib;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Log控制的管理类，设置LogManager的参数
 * <p>
 * Created by liu xuefei on 2017/8/29.
 */

public class LogRecordManager {

    private static final String TAG = "LogRecordManager";
    private static final String CACHE_DIR_NAME = "cache_app_logs";
    private static final int CACHE_DIR_SIZE = 20;

    private String mCacheDirPath;//Cache的缓存目录
    private int mCacheDirSize;//Cache目录设置的大小

    private static boolean sRecordToFile = false;

    private static LogRecordManager sLogRecordManager;
    private RecordLog mRecordLog;

    private LogRecordManager(Context context, LogManagerBuilder builder) {
        this.mCacheDirPath = builder.cacheDirPath;
        this.mCacheDirSize = builder.cacheDirSize;
        if (mCacheDirPath == null || "".equals(mCacheDirPath)) {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                mCacheDirPath = context.getExternalCacheDir().getPath();
            } else {
                mCacheDirPath = context.getCacheDir().getPath();
                Log.d(TAG, "LogRecordManager: storage is unuseable, please check environment");
            }
        }
        mCacheDirPath += File.separator + LogRecordManager.CACHE_DIR_NAME;

        mCacheDirSize = builder.cacheDirSize;
        if (mCacheDirSize < 0) {
            mCacheDirSize = LogRecordManager.CACHE_DIR_SIZE;
        }

        File cacheDir = new File(mCacheDirPath);
        if (!cacheDir.exists()) {
            boolean result = cacheDir.mkdirs();
            if (result) {
                LogUtil.d(TAG, "LogRecordManager: make dir success");
            } else {
                LogUtil.e(TAG, "LogRecordManager: make dir fail");
            }

        }
        LogUtil.d(TAG, "LogRecordManager: cacheDirPath size is : " + FileSizeUtil.getFileSize(mCacheDirPath));
        //配置缓存存放的大小，如果大于20M，就自动删除文件
        if (FileSizeUtil.getFileSize(mCacheDirPath, FileSizeUtil.SIZE_TYPE_MB) > CACHE_DIR_SIZE) {
            Log.d(TAG, "LogRecordManager: logfile is too larger , we will remove and recreate it");
            File[] files = cacheDir.listFiles();
            boolean deleteResult = true;
            try {
                for (File sunFile : files) {
                    deleteResult = sunFile.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
                deleteResult = false;
            }
            LogUtil.d(TAG, "LogRecordManager: delete file result is : " + deleteResult);
        }

        LogUtil.i(TAG, "cache dir cacheDirPath: " + mCacheDirPath + " Usable Space is : " + cacheDir.getUsableSpace() / 1024 / 1024);
        mRecordLog = new RecordLog(mCacheDirPath);
    }

    /**
     * 获取LogManager保存在内存的实例
     *
     * @return sLogRecordManager
     */
    public static LogRecordManager getInstance() {
        if (sLogRecordManager == null) {
            Log.e(TAG, "getInstance: LogRecordManager instance is null, please init.....");
            throw new NullPointerException("please init");
        }

        return sLogRecordManager;
    }

    /**
     * 返回记录log文件的路径地址
     *
     * @return String
     */
    public String getRecordLogFilePath() {
        return mCacheDirPath + File.separator + RecordLog.FILE_NAME;
    }

    /**
     * 控制是否把Log记录如文件
     *
     * @param flag true or false   true表示记录   false表示不记录
     */
    public static void isRecordToFile(boolean flag) {
        sRecordToFile = flag;
//        if (sRecordToFile) {
//            getInstance().mRecordLog
//        } else {
//
//        }
    }

    public static boolean getRecordToFile() {
        return sRecordToFile;
    }

    public static void recordToFile(int logType, String tag, String message) {
        if (LogRecordManager.getInstance() != null)
            LogRecordManager.getInstance().mRecordLog.writeToFile(logType, tag, message);
    }

    /**
     * 构建LogRecordManager的Builder
     */
    public static class LogManagerBuilder {
        private String cacheDirPath;
        private int cacheDirSize;

        public LogManagerBuilder setCacheDirPath(String path) {
            this.cacheDirPath = path;
            return this;
        }

        /**
         * 设置缓存文件件的大小 默认是20M  设置的数值的单位是MB
         *
         * @param size
         * @return
         */
        public LogManagerBuilder setCacheDirSize(int size) {
            this.cacheDirSize = size;
            return this;
        }

        /**
         * 创建LogRecordManager
         *
         * @param context 运行的上下文环境
         * @return 返回LogRecordManager对象
         */
        public LogRecordManager build(Context context) {
            sLogRecordManager = new LogRecordManager(context, this);
            return sLogRecordManager;
        }
    }

    /**
     * 获取LogManager的Builder对象去构建LogManager
     *
     * @return
     */
    public static LogManagerBuilder getInstanceBuilder() {

        return new LogManagerBuilder();
    }

}
