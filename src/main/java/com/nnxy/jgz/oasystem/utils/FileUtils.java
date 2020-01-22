package com.nnxy.jgz.oasystem.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

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

    /**
     * 文件下载
     * @param path
     * @param response
     */
    public static void downloadFile(String path, HttpServletResponse response){
        File file = new File(path);
        if (file.exists()){
            //如果文件存在则下载
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                //从响应中拿到输出流对象
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1){
                    os.write(buffer,0,i);
                    //循环读取文件内容
                    i = bis.read(buffer);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                //关闭流
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
