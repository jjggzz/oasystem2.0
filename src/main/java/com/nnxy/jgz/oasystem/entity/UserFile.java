package com.nnxy.jgz.oasystem.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author JGZ
 * CreateTime 2019/12/13 10:12
 * Email 1945282561@qq.com
 */
@Data
public class UserFile implements Serializable,Cloneable {
    /**
     * 外键属性
     */
    private String fileId;
    private String fileName;
    private String fileRealityName;
    private String fileAddress;
    private Long fileSize;
    private Long uploadTime;
    /**
     * 外键属性
     */
    private User user;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
