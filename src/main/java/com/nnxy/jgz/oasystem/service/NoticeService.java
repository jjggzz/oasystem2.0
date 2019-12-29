package com.nnxy.jgz.oasystem.service;

import com.nnxy.jgz.oasystem.entity.Notice;
import com.nnxy.jgz.oasystem.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
     */
    void addNotice(User user,Notice notice, MultipartFile file) throws IOException;

    /**
     * 有文件的发送通知
     * @param user
     * @param notice
     */
    void addNotice(User user,Notice notice);
}
