package com.sumflower.apploglib;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 记录Log
 * 把Log写入到File中
 * <p>
 * Created by liu xuefei on 2017/8/29.
 */

class RecordLog {
    private static final String TAG = "RecordLog";
    static final String FILE_NAME = "log1";

    private File mLogFile;
    private BufferedWriter mBufferedWriter;

    RecordLog(String cacheDir) {
        mLogFile = new File(cacheDir, "log1");
        if (!mLogFile.exists()) {
            try {
                mLogFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void startRecord() {
        try {
            mBufferedWriter = new BufferedWriter(new FileWriter(mLogFile, true));
        } catch (IOException e) {
            Log.d(TAG, "writeToFile: write fail!!!!  " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void stopRecord() {
        if (mBufferedWriter != null) {
            try {
                mBufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void writeToFile(int logType, String tag, String message) {
        try {
            mBufferedWriter = new BufferedWriter(new FileWriter(mLogFile, true));

            DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();
            StringBuilder stringBuilder = new StringBuilder();
            String dateString = dateFormat.format(new Date());
            stringBuilder.append(dateString).append("   ");

            switch (logType) {
                case Log.VERBOSE:
                    stringBuilder.append("V/");
                    break;
                case Log.DEBUG:
                    stringBuilder.append("D/");
                    break;
                case Log.INFO:
                    stringBuilder.append("I/");
                    break;
                case Log.WARN:
                    stringBuilder.append("W/");
                    break;
                case Log.ERROR:
                    stringBuilder.append("E/");
                    break;
                default:
                    stringBuilder.append("D/");
                    break;
            }
            stringBuilder.append(tag);
            stringBuilder.append("  ").append(message).append("\n");

            mBufferedWriter.write(stringBuilder.toString());
            Log.d(TAG, "writeToFile: write successfully!!!!");
        } catch (IOException e) {
            Log.d(TAG, "writeToFile: write fail!!!!  " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (mBufferedWriter != null) {
                try {
                    mBufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
