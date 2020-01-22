package com.nnxy.jgz.oasystem.service.impl;

import com.nnxy.jgz.oasystem.entity.NoticeFile;
import com.nnxy.jgz.oasystem.mapper.NoticeFileMapper;
import com.nnxy.jgz.oasystem.service.NoticeFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

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

    @Transactional(isolation = Isolation.REPEATABLE_READ,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public void delFile(String fileId) {
        //获取通知文件的信息
        NoticeFile noticeFileById = noticeFileMapper.getNoticeFileById(fileId);
        if(noticeFileById!=null && noticeFileById.getFileAddress()!=null){
            //如果不为空
            File file = new File(noticeFileById.getFileAddress());
            //删除磁盘
            if(file.exists()){
                file.delete();
            }
            //删除数据库
            noticeFileMapper.deleteNoticeFileById(fileId);
        }

    }

    @Override
    public List<NoticeFile> noticeFileList() {

        return noticeFileMapper.noticeFileList();
    }
}
