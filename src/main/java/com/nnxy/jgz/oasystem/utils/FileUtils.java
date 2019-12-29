package com.nnxy.jgz.oasystem.utils;

/**
 * 文件工具类
 * @author JGZ
 * CreateTime 2019/12/29 10:58
 * Email 1945282561@qq.com
 */
public class FileUtils {

    /**
     * 获取文件后缀名
     * @param fileName
     * @return
     */
    public static String getFileLastWord(String fileName){
        String[] split = fileName.split("\\.");
        return split[split.length-1];
    }

}
