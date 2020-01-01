package com.nnxy.jgz.oasystem.mapper;

import com.nnxy.jgz.oasystem.entity.NoticeRead;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author JGZ
 * CreateTime 2020/1/1 11:16
 * Email 1945282561@qq.com
 */
@Mapper
public interface NoticeReadMapper {
    /**
     * 插入通知阅读记录
     * @param noticeRead
     */
    void insert(NoticeRead noticeRead);
}
