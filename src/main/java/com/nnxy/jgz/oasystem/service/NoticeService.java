package com.nnxy.jgz.oasystem.service;

import com.nnxy.jgz.oasystem.entity.Notice;
import com.nnxy.jgz.oasystem.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author JGZ
 * CreateTime 2019/12/29 10:57
 * Email 1945282561@qq.com
 */
public interface NoticeService {

    /**
     * 没有文件的发送通知
     * @param user
     * @param notice
     * @param file
     * @throws IOException
     */
    void addNotice(User user,Notice notice, MultipartFile file) throws IOException;

    /**
     * 有文件的发送通知
     * @param user
     * @param notice
     */
    void addNotice(User user,Notice notice);

    /**
     * 获取已读通知列表
     * @param userId
     * @return
     */
    List<Notice> getReadNoticeList(String userId);

    /**
     * 获取未读通知列表
     * @param userId
     * @return
     */
    List<Notice> getUnReadNoticeList(String userId);

    /**
     * 通过noticeId获取通知详情
     * @param noticeId
     * @return
     */
    Notice getNoticeByNoticeId(String noticeId);
}
