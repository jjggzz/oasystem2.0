package com.nnxy.jgz.oasystem.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.nnxy.jgz.oasystem.controller.WebSocketServer;
import com.nnxy.jgz.oasystem.entity.Notice;
import com.nnxy.jgz.oasystem.entity.NoticeFile;
import com.nnxy.jgz.oasystem.entity.User;
import com.nnxy.jgz.oasystem.mapper.NoticeFileMapper;
import com.nnxy.jgz.oasystem.mapper.NoticeMapper;
import com.nnxy.jgz.oasystem.service.NoticeFileService;
import com.nnxy.jgz.oasystem.service.NoticeService;
import com.nnxy.jgz.oasystem.utils.FileUtils;
import com.nnxy.jgz.oasystem.utils.ProjectConfig;
import com.nnxy.jgz.oasystem.utils.WebSocketMessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author JGZ
 * CreateTime 2019/12/29 11:04
 * Email 1945282561@qq.com
 */
@EnableConfigurationProperties(ProjectConfig.class)
@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private NoticeFileMapper noticeFileMapper;

    @Autowired
    private ProjectConfig projectConfig;

    @Autowired
    private NoticeFileService noticeFileService;

    @Override
    public void addNotice(User user,Notice notice, MultipartFile file) throws IOException {
        //插入通知
        addNotice(user,notice);
        //获取文件后缀名
        String fileLastWord = FileUtils.getFileLastWord(file.getOriginalFilename());
        //随机字符串
        String fileName = UUID.randomUUID().toString().replace("-", "");
        //获取毫秒值
        Long time = System.currentTimeMillis();
        //设置文件的存储路径
        File f = new File(projectConfig.getNoticeFileAddress() + fileName + time + "." + fileLastWord);
        NoticeFile noticeFile =new NoticeFile();
        //设置文件id
        noticeFile.setFileId(UUID.randomUUID().toString().replace("-",""));
        //设置文件的真名
        noticeFile.setFileRealityName(fileName + time + "." + fileLastWord);
        //设置文件的存储路径
        noticeFile.setFileAddress(projectConfig.getNoticeFileAddress() + fileName + time + "." + fileLastWord);
        //设置文件大小
        noticeFile.setFileSize(file.getSize());
        //设置文件的名字
        noticeFile.setFileName(file.getOriginalFilename());
        //设置关联的通知
        noticeFile.setNotice(notice);
        //插入文件
        noticeFileMapper.insert(noticeFile);
        //写入文件
        file.transferTo(f);

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

    @Override
    public List<Notice> getReadNoticeList(String user) {
        return  noticeMapper.getReadNoticeListByUserId(user);
    }

    @Override
    public List<Notice> getUnReadNoticeList(String userId) {

        return noticeMapper.getUnreadNoticeListByUserId(userId);
    }

    @Override
    public Notice getNoticeByNoticeId(String noticeId) {
        return noticeMapper.getNoticeByNoticeId(noticeId);
    }

    @Override
    public List<Notice> getUserNoticeList(String userId) {
        return noticeMapper.getUserNoticeListByUserId(userId);
    }

    @Override
    public void deleteById(String noticeId) {
        //获取被删通知的信息
        Notice noticeByNoticeId = noticeMapper.getNoticeByNoticeId(noticeId);
        if(noticeByNoticeId.getNoticeFileList()!=null &&
                noticeByNoticeId.getNoticeFileList().size()!=0){
            //如果文件列表不为空
            //删除文件
            for (NoticeFile noticeFile:noticeByNoticeId.getNoticeFileList()) {
                noticeFileService.delFile(noticeFile.getFileId());
            }
        }
        noticeMapper.deleteById(noticeId);
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
