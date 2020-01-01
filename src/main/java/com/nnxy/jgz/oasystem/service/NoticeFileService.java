package com.nnxy.jgz.oasystem.service;

import com.nnxy.jgz.oasystem.entity.NoticeFile;

/**
 * @author JGZ
 * CreateTime 2020/1/1 10:21
 * Email 1945282561@qq.com
 */
public interface NoticeFileService {

    /**
     * 通过id获取通知文件
     * @param fileId
     * @return
     */
    NoticeFile getNoticeFileById(String fileId);
}
