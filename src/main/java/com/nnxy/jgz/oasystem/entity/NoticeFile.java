package com.nnxy.jgz.oasystem.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author JGZ
 * CreateTime 2019/12/13 10:12
 * Email 1945282561@qq.com
 */
@Data
public class NoticeFile implements Serializable,Cloneable {
    /**
     * 外键属性
     */
    private String fileId;
    private String fileName;
    private String fileRealityName;
    private String fileAddress;
    private Long fileSize;
    /**
     * 外键属性
     */
    private Notice notice;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
