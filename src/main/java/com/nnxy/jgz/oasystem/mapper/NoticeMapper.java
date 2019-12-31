package com.nnxy.jgz.oasystem.mapper;

import com.nnxy.jgz.oasystem.entity.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author JGZ
 * CreateTime 2019/12/29 11:06
 * Email 1945282561@qq.com
 */
@Mapper
public interface NoticeMapper {

    /**
     * 插入通知
     * @param notice
     */
    void insert(Notice notice);

    /**
     * 获取通知列表
     * @param userId
     * @return
     */
    List<Notice> getReadNoticeListByUserId(String userId);

    /**
     * 获取用户未读通知
     * @param userId
     * @return
     */
    List<Notice> getUnreadNoticeListByUserId(String userId);

    /**
     * 通过noticeId获取通知
     * @param noticeId
     * @return
     */
    Notice getNoticeByNoticeId(String noticeId);
}
