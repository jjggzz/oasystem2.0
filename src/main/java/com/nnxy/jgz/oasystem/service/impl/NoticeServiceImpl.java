package com.nnxy.jgz.oasystem.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.nnxy.jgz.oasystem.controller.WebSocketServer;
import com.nnxy.jgz.oasystem.entity.Notice;
import com.nnxy.jgz.oasystem.entity.NoticeFile;
import com.nnxy.jgz.oasystem.entity.User;
import com.nnxy.jgz.oasystem.mapper.NoticeFileMapper;
import com.nnxy.jgz.oasystem.mapper.NoticeMapper;
import com.nnxy.jgz.oasystem.service.NoticeService;
import com.nnxy.jgz.oasystem.utils.FileUtils;
import com.nnxy.jgz.oasystem.utils.WebSocketMessageEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author JGZ
 * CreateTime 2019/12/29 11:04
 * Email 1945282561@qq.com
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private NoticeFileMapper noticeFileMapper;

    @Override
    public void addNotice(User user,Notice notice, MultipartFile file)  {

        //插入通知
        addNotice(user,notice);
        //获取文件后缀名
        String fileLastWord = FileUtils.getFileLastWord(file.getOriginalFilename());
        //随机字符串
        String fileName = UUID.randomUUID().toString().replace("-", "");
        //获取毫秒值
        Long time = System.currentTimeMillis();
        //设置文件的存储路径
        File f = new File("D:/file/notice/" + fileName + time + "." + fileLastWord);
        NoticeFile noticeFile =new NoticeFile();
        //设置文件id
        noticeFile.setFileId(UUID.randomUUID().toString().replace("-",""));
        //设置文件的真名
        noticeFile.setFileRealityName(fileName + time + "." + fileLastWord);
        //设置文件的存储路径
        noticeFile.setFileAddress("D:/file/notice/" + fileName + time + "." + fileLastWord);
        //设置文件大小
        noticeFile.setFileSize(file.getSize());
        //设置文件的名字
        noticeFile.setFileName(file.getOriginalFilename());
        //设置关联的通知
        noticeFile.setNotice(notice);
        //插入文件
        noticeFileMapper.insert(noticeFile);
        try {
            //写入文件
            file.transferTo(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNotice(User user,Notice notice) {
        //设置发送用户
        notice.setUser(user);
        //设置id
        notice.setNoticeId(UUID.randomUUID().toString().replace("-",""));
        //设置发送时间
        notice.setNoticeSendTime(System.currentTimeMillis());
        //设置类型
        notice.setNoticeType(1);
        noticeMapper.insert(notice);
        //推送消息提示
        pushTips(notice.getUser());
    }

    /**
     * 消息推送
     */
    private void pushTips(User user){
        WebSocketMessageEntity webSocketMessageEntity = new WebSocketMessageEntity();
        //设置发送用户id
        webSocketMessageEntity.setUserId(user.getUserId());
        //设置发送用户名
        webSocketMessageEntity.setUserName(user.getUserName());
        //设置内容
        webSocketMessageEntity.setContent("您有一则新通知");
        //设置类型
        webSocketMessageEntity.setType(1);
        webSocketMessageEntity.setIsBroadcast(true);
        webSocketMessageEntity.setTargetId("public");
        //转成json
        String message = JSONObject.toJSONString(webSocketMessageEntity);
        WebSocketServer.broadcast(message,"public");
    }
}
