package com.nnxy.jgz.oasystem.mapper;

import com.nnxy.jgz.oasystem.entity.Notice;
import org.apache.ibatis.annotations.Mapper;

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
}
