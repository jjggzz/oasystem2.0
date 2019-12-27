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
public class FlowNode implements Serializable,Cloneable {
    /**
     * 基础属性
     */
    private String flowNodeId;
    private String flowNodeName;
    private String flowNodeDescription;
    /**
     * 外键属性
     */
    private Flow flow;
    private Position position;
    /**
     * 一对多
     */
    private List<Apply> applyList;
    private List<Examine> examineList;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
