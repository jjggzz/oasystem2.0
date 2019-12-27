package com.nnxy.jgz.oasystem.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author JGZ
 * CreateTime 2019/12/13 10:13
 * Email 1945282561@qq.com
 */
@Data
public class Position implements Serializable,Cloneable {
    /**
     * 基础属性
     */
    private String positionId;
    private String positionName;
    /**
     * 一对多
     */
    private List<Permission> permissionList;
    private List<User> userList;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
