package com.nnxy.jgz.oasystem.service;

import com.nnxy.jgz.oasystem.entity.Permission;

import java.util.List;

/**
 * @author JGZ
 * CreateTime 2019/12/19 15:29
 * Email 1945282561@qq.com
 */
public interface PermissionService {

    /**
     * 获取权限列表
     * @return
     */
    List<Permission> permissionList();

    /**
     * 通过职位id获取权限列表
     * @param positionId
     * @return
     */
    List<Permission> getPermissionByPositionId(String positionId);
}
