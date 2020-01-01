package com.nnxy.jgz.oasystem.service;

import com.nnxy.jgz.oasystem.entity.Notice;
import com.nnxy.jgz.oasystem.entity.User;

/**
 * @author JGZ
 * CreateTime 2020/1/1 11:11
 * Email 1945282561@qq.com
 */
public interface NoticeReadService {

    /**
     * 阅读通知
     * @param user
     * @param notice
     */
    void readNotice(User user, Notice notice);
}
