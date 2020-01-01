package com.nnxy.jgz.oasystem.controller;

import com.nnxy.jgz.oasystem.entity.NoticeFile;
import com.nnxy.jgz.oasystem.service.NoticeFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 通知文件的控制层
 * @author JGZ
 * CreateTime 2020/1/1 10:15
 * Email 1945282561@qq.com
 */
@RestController
public class NoticeFileController {

    @Autowired
    private NoticeFileService noticeFileService;
    /**
     * 文件下载
     * @param fileId
     * @param response
     * @return
     */
    @GetMapping("/noticeFile/download/{fileId}")
    public void downLoadFile(@PathVariable("fileId") String fileId,
                                        HttpServletResponse response){
        try {
            NoticeFile noticeFile = noticeFileService.getNoticeFileById(fileId);
            //获取要下载文件在硬盘中存储的位置
            String fileAddress = noticeFile.getFileAddress();
            File file = new File(fileAddress);
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
        }catch (Exception e) {
            e.printStackTrace();
        }


    }
}
