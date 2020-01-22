package com.nnxy.jgz.oasystem.controller;

import com.nnxy.jgz.oasystem.entity.NoticeFile;
import com.nnxy.jgz.oasystem.service.NoticeFileService;
import com.nnxy.jgz.oasystem.utils.FileUtils;
import com.nnxy.jgz.oasystem.utils.ResponseMessage;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

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
    @RequiresPermissions(value={"admin:all","file:get"},logical= Logical.OR)
    @GetMapping("/noticeFile/download/{fileId}")
    public void downLoadFile(@PathVariable("fileId") String fileId,
                                        HttpServletResponse response){
        try {
            NoticeFile noticeFile = noticeFileService.getNoticeFileById(fileId);
            //获取要下载文件在硬盘中存储的位置
            String fileAddress = noticeFile.getFileAddress();
            //文件下载
            FileUtils.downloadFile(fileAddress,response);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件
     * @param fileId
     * @return
     */
    @RequiresPermissions(value={"admin:all","file:del"},logical= Logical.OR)
    @DeleteMapping("/noticeFile/{fileId}")
    public ResponseMessage delFile(@PathVariable("fileId") String fileId){
        try {
            noticeFileService.delFile(fileId);
            return new ResponseMessage("0","删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","删除失败");
        }
    }

    /**
     * 获取文件列表
     * @return
     */
    @RequiresPermissions(value={"admin:all","file:list"},logical= Logical.OR)
    @GetMapping("/noticeFile/noticeFileList")
    public ResponseMessage fileList(){
        try {
            List<NoticeFile> noticeFileList = noticeFileService.noticeFileList();
            ResponseMessage responseMessage = new ResponseMessage("0","获取成功");
            responseMessage.getData().put("noticeFileList",noticeFileList);
            return responseMessage;
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","获取失败");
        }

    }

}
