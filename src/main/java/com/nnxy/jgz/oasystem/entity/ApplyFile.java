package com.nnxy.jgz.oasystem.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author JGZ
 * CreateTime 2019/12/13 10:11
 * Email 1945282561@qq.com
 */
@Data
public class ApplyFile implements Serializable,Cloneable {
    /**
     * 基础属性
     */
    private String fileId;
    private String fileName;
    private String fileRealityName;
    private String fileAddress;
    private Long fileSize;
    /**
     * 外键属性
     */
    private Apply apply;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
