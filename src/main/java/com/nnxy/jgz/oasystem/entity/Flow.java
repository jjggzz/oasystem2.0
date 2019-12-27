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
public class Flow implements Serializable,Cloneable {
    /**
     * 基础属性
     */
    private String flowId;
    private String flowName;
    private String flowDescription;
    private Integer flowState;
    /**
     * 一对多
     */
    private List<Apply> applyList;
    private List<FlowNode> flowNodeList;
    private List<FlowLine> flowLineList;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
