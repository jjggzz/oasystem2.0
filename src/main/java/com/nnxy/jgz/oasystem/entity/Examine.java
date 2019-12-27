package com.nnxy.jgz.oasystem.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author JGZ
 * CreateTime 2019/12/13 10:11
 * Email 1945282561@qq.com
 */
@Data
public class Examine implements Serializable,Cloneable {
    /**
     * 基础属性
     */
    private String examineId;
    private String userName;
    private Boolean examineStatus;
    private String examineInfo;
    private Long examineDate;
    /**
     * 外键属性
     */
    private Apply apply;
    private FlowNode flowNode;
    private User user;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
