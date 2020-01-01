package com.nnxy.jgz.oasystem.controller;

import com.nnxy.jgz.oasystem.entity.Notice;
import com.nnxy.jgz.oasystem.entity.NoticeRead;
import com.nnxy.jgz.oasystem.entity.User;
import com.nnxy.jgz.oasystem.service.NoticeReadService;
import com.nnxy.jgz.oasystem.utils.ResponseMessage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JGZ
 * CreateTime 2020/1/1 11:07
 * Email 1945282561@qq.com
 */
@RestController
public class NoticeReadController {

    @Autowired
    private NoticeReadService noticeReadService;
    /**
     * 阅读通知
     * @param noticeId
     * @return
     */
    @PostMapping("/noticeRead/{noticeId}")
    public ResponseMessage noticeRead(@PathVariable("noticeId") String noticeId){
        try {
            //获取登录的用户
            Subject subject = SecurityUtils.getSubject();
            if(subject.isAuthenticated()){
                User user = (User) subject.getPrincipal();
                Notice notice = new Notice();
                notice.setNoticeId(noticeId);
                //阅读通知
                noticeReadService.readNotice(user,notice);
                return new ResponseMessage("0","成功");
            }
            else {
                return new ResponseMessage("-2","用户未登录");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","阅读失败");
        }
    }
}
