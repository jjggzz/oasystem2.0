package com.nnxy.jgz.oasystem.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author JGZ
 * CreateTime 2019/12/13 10:11
 * Email 1945282561@qq.com
 */
@Data
public class Article implements Serializable,Cloneable {
    /**
     * 基础属性
     */
    private String articleId;
    private String articleTitle;
    private String articleContent;
    private Integer articleType;
    private Long articleCreateTime;
    private Integer articleCommentCount;
    private Integer articlePageView;
    private Integer articleState;
    /**
     * 外键属性
     */
    private User user;
    /**
     * 一对多
     */
    private List<Comment> commentList;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
