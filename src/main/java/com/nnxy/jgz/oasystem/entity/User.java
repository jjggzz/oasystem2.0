package com.nnxy.jgz.oasystem.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author JGZ
 * CreateTime 2019/12/13 10:13
 * Email 1945282561@qq.com
 */
@Data
public class User implements Serializable,Cloneable {
    /**
     * 基础属性
     */
    private String userId;
    private String userAccount;
    private String userPassword;
    private String userName;
    private Long userCreateTime;
    private String userPhone;
    private String userEmail;
    private String userHeadFilePath;
    private String userHeadPortrait;
    private Boolean userStatus;
    /**
     * 外键属性
     */
    private Department department;
    private Position position;
    /**
     * 一对多
     */
    private List<Apply> applyList;
    private List<Examine> examineList;
    private List<Notice> noticeList;
    private List<NoticeRead> noticeReadList;
    private List<Article> articleList;
    private List<Comment> commentList;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
