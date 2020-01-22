package com.nnxy.jgz.oasystem.controller;

import com.nnxy.jgz.oasystem.entity.NoticeFile;
import com.nnxy.jgz.oasystem.entity.User;
import com.nnxy.jgz.oasystem.entity.UserFile;
import com.nnxy.jgz.oasystem.service.UserFileService;
import com.nnxy.jgz.oasystem.utils.ErrorEnum;
import com.nnxy.jgz.oasystem.utils.FileUtils;
import com.nnxy.jgz.oasystem.utils.ResponseMessage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * 用户文件列表
 * @author JGZ
 * CreateTime 2020/1/10 11:23
 * Email 1945282561@qq.com
 */
@RestController
public class UserFileController {

    @Autowired
    UserFileService userFileService;
    /**
     * 文件上传
     * @param file
     * @return
     */
    @RequiresPermissions(value={"admin:all","file:add"},logical= Logical.OR)
    @PostMapping("/userFile")
    public ResponseMessage uploadFile(MultipartFile file){
        try {
            Subject subject = SecurityUtils.getSubject();
            if(subject.isAuthenticated()){
                //如果用户登录
                User user = (User) subject.getPrincipal();
                //写入文件
                userFileService.uploadFile(user,file);
                return new ResponseMessage("0","成功");
            }
            else {
                //返回用户未登录
                return new ResponseMessage(ErrorEnum.E_UNAUTHENTICATED);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","上传失败");
        }
    }

    /**
     * 获取用户的文件列表
     * @param userId
     * @return
     */
    @RequiresPermissions(value={"admin:all","file:list"},logical= Logical.OR)
    @GetMapping("/userFile/userFileList/{userId}")
    public ResponseMessage userFileList(@PathVariable("userId") String userId){
        try {
           List<UserFile> userFileList = userFileService.getUserFileListByUserId(userId);
           ResponseMessage responseMessage = new ResponseMessage("0","获取成功");
           responseMessage.getData().put("userFileList",userFileList);
           return responseMessage;
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","获取失败");
        }
    }

    /**
     * 删除用户文件
     * @param fileId
     * @return
     */
    @RequiresPermissions(value={"admin:all","file:del"},logical= Logical.OR)
    @DeleteMapping("/userFile/{fileId}")
    public ResponseMessage deleteUserFile(@PathVariable("fileId") String fileId){
        try{
            userFileService.deleteUserFileByFileId(fileId);
            return new ResponseMessage("0","删除成功");
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","删除失败");
        }
    }

    @RequiresPermissions(value={"admin:all","file:get"},logical= Logical.OR)
    @GetMapping("/userFile/download/{fileId}")
    public void downloadFile(@PathVariable("fileId") String fileId,
                             HttpServletResponse response){
        try {
            UserFile userFile = userFileService.getUserFileByFileId(fileId);
            //获取要下载文件在硬盘中存储的位置
            String fileAddress = userFile.getFileAddress();
            //文件下载
            FileUtils.downloadFile(fileAddress,response);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

}
