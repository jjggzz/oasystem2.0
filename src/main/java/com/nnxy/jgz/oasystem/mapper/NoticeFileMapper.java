package com.nnxy.jgz.oasystem.mapper;

import com.nnxy.jgz.oasystem.entity.NoticeFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author JGZ
 * CreateTime 2019/12/29 13:49
 * Email 1945282561@qq.com
 */
@Mapper
public interface NoticeFileMapper {

    /**
     * 插入通知文件
     * @param noticeFile
     */
    void insert(NoticeFile noticeFile);

    /**
     * 获取通知文件通过id
     * @param fileId
     * @return
     */
    NoticeFile getNoticeFileById(String fileId);

    /**
     * 通过id删除通知文件信息
     * @param fileId
     */
    void deleteNoticeFileById(String fileId);

    /**
     * 获取通知文件列表
     * @return
     */
    List<NoticeFile> noticeFileList();
}
