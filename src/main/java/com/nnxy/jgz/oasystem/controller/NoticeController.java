package com.nnxy.jgz.oasystem.controller;

import com.nnxy.jgz.oasystem.entity.Notice;
import com.nnxy.jgz.oasystem.entity.User;
import com.nnxy.jgz.oasystem.service.NoticeService;
import com.nnxy.jgz.oasystem.utils.ResponseMessage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 通知控制层
 * @author JGZ
 * CreateTime 2019/12/28 13:23
 * Email 1945282561@qq.com
 */
@RestController
public class NoticeController {

    @Autowired
    private NoticeService noticeService;
    /**
     * 发送通知时选择文件会调用此方法
     * @param notice
     * @param file
     * @return
     */
    @PostMapping("/noticeFile")
    public ResponseMessage addNotice(Notice notice, MultipartFile file){
        try{
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            if(user != null){
                noticeService.addNotice(user,notice, file);
                return new ResponseMessage("0","发送成功");
            }
            else{
                return new ResponseMessage("-2","用户未登录");
            }

        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","发送失败");
        }
    }

    /**
     * 未选择文件时会调用此方法
     * @param notice
     * @return
     */
    @PostMapping("/notice")
    public ResponseMessage addNotice(@RequestBody Notice notice){
        try{
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            if(user != null){
            noticeService.addNotice(user,notice);
            return new ResponseMessage("0","发送成功");
            }
            else{
                return new ResponseMessage("-2","用户未登录");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","发送失败");
        }
    }


}
