package com.sumflower.apploglib;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * 计算File大小的工具类
 * 可以计算给定的文件夹的大小，给定文件的大小
 */

public class FileSizeUtil {
    private static final String TAG = "FileSizeUtil";
    public static final short SIZE_TYPE_B = 1;//获取文件大小单位为B的double值
    public static final short SIZE_TYPE_KB = 2;//获取文件大小单位为KB的double值
    public static final short SIZE_TYPE_MB = 3;//获取文件大小单位为MB的double值
    public static final short SIZE_TYPE_GB = 4;//获取文件大小单位为GB的double值

    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */
    public static double getFileSize(String filePath, int sizeType) {
        File file = new File(filePath);
        long size = 0;
        try {
            if (file.isDirectory()) {
                size = getDirSize(file);
            } else {
                size = getFileSize(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "获取失败!");
        }
        return formatFileSize(size, sizeType);
    }

    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @param filePath 文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static String getFileSize(String filePath) {
        File file = new File(filePath);
        long size = 0;
        try {
            if (file.isDirectory()) {
                size = getDirSize(file);
            } else {
                size = getFileSize(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "获取失败!");
        }
        return formatFileSize(size);
    }

    /**
     * 获取指定文件大小
     *
     * @param f
     * @return
     * @throws Exception
     */
    private static long getFileSize(File file) throws IOException {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            size = fis.available();
        } else {
            Log.e(TAG, "文件不存在!");
        }
        return size;
    }

    /**
     * 获取指定文件夹的大小
     *
     * @param f
     * @return
     * @throws Exception
     */
    private static long getDirSize(File f) throws IOException {
        long size = 0;
        File fList[] = f.listFiles();
        for (int i = 0; i < fList.length; i++) {
            if (fList[i].isDirectory()) {
                size = size + getDirSize(fList[i]);
            } else {
                size = size + getFileSize(fList[i]);
            }
        }
        return size;
    }

    /**
     * 转换文件大小
     *
     * @param fileSize
     * @return
     */
    private static String formatFileSize(long fileSize) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileSize == 0) {
            return wrongSize;
        }
        if (fileSize < 1024) {
            fileSizeString = df.format((double) fileSize) + "B";
        } else if (fileSize < 1048576) {
            fileSizeString = df.format((double) fileSize / 1024) + "KB";
        } else if (fileSize < 1073741824) {
            fileSizeString = df.format((double) fileSize / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileSize / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileSize
     * @param type
     * @return
     */
    private static double formatFileSize(long fileSize, int type) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (type) {
            case SIZE_TYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileSize));
                break;
            case SIZE_TYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileSize / 1024));
                break;
            case SIZE_TYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileSize / 1048576));
                break;
            case SIZE_TYPE_GB:
                fileSizeLong = Double.valueOf(df.format((double) fileSize / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }
}
