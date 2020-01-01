package com.nnxy.jgz.oasystem.controller;

import com.nnxy.jgz.oasystem.entity.Notice;
import com.nnxy.jgz.oasystem.entity.NoticeFile;
import com.nnxy.jgz.oasystem.entity.User;
import com.nnxy.jgz.oasystem.service.NoticeService;
import com.nnxy.jgz.oasystem.utils.ProjectConfig;
import com.nnxy.jgz.oasystem.utils.ResponseMessage;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 通知控制层
 * @author JGZ
 * CreateTime 2019/12/28 13:23
 * Email 1945282561@qq.com
 */

@EnableConfigurationProperties(ProjectConfig.class)
@RestController
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private ProjectConfig projectConfig;

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

    /**
     * 获取已读通知列表
     * @return
     */
    @GetMapping("/notice/readNoticeList/{userId}")
    public ResponseMessage getReadNoticeList(@PathVariable("userId") String userId){
        try {
           List<Notice> noticeList = noticeService.getReadNoticeList(userId);
           ResponseMessage responseMessage = new ResponseMessage("0","获取已读通知列表成功");
           responseMessage.getData().put("noticeList",noticeList);
           return responseMessage;
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","获取已读通知列表失败");
        }
    }

    /**
     * 获取未读通知列表失败
     * @return
     */
    @GetMapping("/notice/unReadNoticeList/{userId}")
    public ResponseMessage getUnReadNoticeList(@PathVariable("userId") String userId){
        try {
            List<Notice> noticeList = noticeService.getUnReadNoticeList(userId);
            ResponseMessage responseMessage = new ResponseMessage("0","获取未读通知列表成功");
            responseMessage.getData().put("noticeList",noticeList);
            return responseMessage;
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","获取未读通知列表失败");
        }
    }

    /**
     * 获取通知的详细信息
     * @param noticeId
     * @return
     */
    @GetMapping("/notice/{noticeId}")
    public ResponseMessage getNotice(@PathVariable("noticeId") String noticeId, HttpServletRequest request){
        try {
           Notice notice = noticeService.getNoticeByNoticeId(noticeId);
           //封装文件路径
           if(notice.getNoticeFileList().size()>0){
               for (NoticeFile noticeFile :notice.getNoticeFileList()) {
                   noticeFile.setFileAddress(projectConfig.getServerAddress() +
                           request.getContextPath() + projectConfig.getNoticeFile() + noticeFile.getFileRealityName());
               }
           }
           ResponseMessage responseMessage = new ResponseMessage("0","获取通知详情成功");
           responseMessage.getData().put("notice",notice);
           return responseMessage;
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","获取通知详情失败");
        }
    }

    /**
     * 获取该用户所发送的通知列表
     * @param userId
     * @return
     */
    @GetMapping("/notice/userNoticeList/{userId}")
    public ResponseMessage getUserNoticeList(@PathVariable("userId") String userId){
        try {
            List<Notice> noticeList = noticeService.getUserNoticeList(userId);
            ResponseMessage responseMessage = new ResponseMessage("0","获取未读通知列表成功");
            responseMessage.getData().put("noticeList",noticeList);
            return responseMessage;
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","获取未读通知列表失败");
        }
    }

    /**
     * 根据id删除通知
     * @param noticeId
     * @return
     */
    @DeleteMapping("/notice/{noticeId}")
    public ResponseMessage delNotice(@PathVariable("noticeId") String noticeId){
        try {
            noticeService.deleteById(noticeId);
            return new ResponseMessage("0","删除通知成功");
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","删除通知失败");
        }

    }

}
