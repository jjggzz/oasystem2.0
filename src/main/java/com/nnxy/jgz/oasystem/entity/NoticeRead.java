package com.nnxy.jgz.oasystem.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author JGZ
 * CreateTime 2019/12/13 10:13
 * Email 1945282561@qq.com
 */
@Data
public class NoticeRead implements Serializable,Cloneable {
    /**
     * 基础属性
     */
    private Long readTime;
    /**
     * 外键属性
     */
    private Notice notice;
    private User user;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
