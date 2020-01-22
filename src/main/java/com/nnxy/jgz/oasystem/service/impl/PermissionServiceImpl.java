package com.nnxy.jgz.oasystem.service.impl;

import com.nnxy.jgz.oasystem.entity.Permission;
import com.nnxy.jgz.oasystem.mapper.PermissionMapper;
import com.nnxy.jgz.oasystem.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author JGZ
 * CreateTime 2019/12/19 15:33
 * Email 1945282561@qq.com
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> permissionList() {

        return permissionMapper.permissionList();
    }

    @Override
    public List<Permission> getPermissionByPositionId(String positionId) {
        return permissionMapper.getPermissionByPositionId(positionId);
    }
}
