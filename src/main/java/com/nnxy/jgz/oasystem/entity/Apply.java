package com.nnxy.jgz.oasystem.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author JGZ
 * CreateTime 2019/12/13 10:10
 * Email 1945282561@qq.com
 */
@Data
public class Apply implements Serializable,Cloneable {
    /**
     * 基础属性
     */
    private String applyId;
    private String applyTitle;
    private String applyDescription;
    private Long applySubmitTime;
    private Integer applyState;
    /**
     * 外键属性
     */
    private Flow flow;
    private User user;
    private FlowNode applyCurrentNode;
    /**
     * 一对多
     */
    private List<ApplyFile> fileList;
    private List<Examine> examineList;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
