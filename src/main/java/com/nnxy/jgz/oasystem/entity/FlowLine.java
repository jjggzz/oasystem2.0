package com.nnxy.jgz.oasystem.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author JGZ
 * CreateTime 2019/12/13 10:12
 * Email 1945282561@qq.com
 */
@Data
public class FlowLine implements Serializable,Cloneable {
    /**
     * 基础属性
     */
    private String flowLineId;
    private String flowLineDescription;
    /**
     * 外键属性
     */
    private Flow flow;
    private FlowNode prevNode;
    private FlowNode nextNode;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
