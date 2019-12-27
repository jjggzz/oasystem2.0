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
public class Permission implements Serializable,Cloneable {
    /**
     * 基础属性
     */
    private String permissionId;
    private String permissionName;
    /**
     * 一对多
     */
    private List<Position> positionList;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
