package com.nnxy.jgz.oasystem.mapper;

import com.nnxy.jgz.oasystem.entity.NoticeFile;
import org.apache.ibatis.annotations.Mapper;

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
}
