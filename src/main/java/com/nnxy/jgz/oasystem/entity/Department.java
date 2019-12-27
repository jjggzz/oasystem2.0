package com.nnxy.jgz.oasystem.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author JGZ
 * CreateTime 2019/12/13 10:11
 * Email 1945282561@qq.com
 */
@Data
public class Department implements Serializable,Cloneable {
    /**
     * 基础属性
     */
    private String departmentId;
    private String departmentName;
    private Integer departmentNumber;
    private Integer departmentLevel;
    /**
     * 外键属性
     */
    private Department departmentParent;
    /**
     * 一对多
     */
    private List<User> userList;
    private List<Department> childDepartmentList;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
