package com.nnxy.jgz.oasystem.service.impl;

import com.nnxy.jgz.oasystem.entity.Notice;
import com.nnxy.jgz.oasystem.entity.NoticeRead;
import com.nnxy.jgz.oasystem.entity.User;
import com.nnxy.jgz.oasystem.mapper.NoticeReadMapper;
import com.nnxy.jgz.oasystem.service.NoticeReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author JGZ
 * CreateTime 2020/1/1 11:15
 * Email 1945282561@qq.com
 */
@Service
public class NoticeReadServiceImpl implements NoticeReadService {

    @Autowired
    private NoticeReadMapper noticeReadMapper;

    @Transactional(isolation = Isolation.REPEATABLE_READ,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public void readNotice(User user, Notice notice) {
        //设置数据
        NoticeRead noticeRead = new NoticeRead();
        noticeRead.setUser(user);
        noticeRead.setNotice(notice);
        noticeRead.setReadTime(System.currentTimeMillis());
        noticeReadMapper.insert(noticeRead);
    }
}
