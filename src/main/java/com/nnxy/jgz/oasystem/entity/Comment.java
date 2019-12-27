package com.nnxy.jgz.oasystem.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author JGZ
 * CreateTime 2019/12/13 10:11
 * Email 1945282561@qq.com
 */
@Data
public class Comment implements Serializable,Cloneable {
    /**
     * 基础属性
     */
    private String commentId;
    private String commentContent;
    private Long commentCreateTime;
    private String commentParent;
    /**
     * 外键属性
     */
    private User user;
    private Article article;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
