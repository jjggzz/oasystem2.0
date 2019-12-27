package com.nnxy.jgz.oasystem.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author JGZ
 * CreateTime 2019/12/13 10:12
 * Email 1945282561@qq.com
 */
@Data
public class Notice implements Serializable,Cloneable {
    /**
     * 基础属性
     */
    private String noticeId;
    private Long noticeSendTime;
    private String noticeTitle;
    private String noticeDescription;
    private Integer noticeType;
    /**
     * 外键属性
     */
    private User user;
    /**
     * 一对多
     */
    private List<NoticeFile> noticeFileList;
    private List<NoticeRead> noticeReadList;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
