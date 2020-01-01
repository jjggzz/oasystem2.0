package com.nnxy.jgz.oasystem.service.impl;

import com.nnxy.jgz.oasystem.entity.NoticeFile;
import com.nnxy.jgz.oasystem.mapper.NoticeFileMapper;
import com.nnxy.jgz.oasystem.service.NoticeFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author JGZ
 * CreateTime 2020/1/1 10:33
 * Email 1945282561@qq.com
 */
@Service
public class NoticeFileServiceImpl implements NoticeFileService {

    @Autowired
    private NoticeFileMapper noticeFileMapper;

    @Override
    public NoticeFile getNoticeFileById(String fileId) {
        return noticeFileMapper.getNoticeFileById(fileId);
    }
}
