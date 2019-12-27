package com.nnxy.jgz.oasystem.mapper;

import com.nnxy.jgz.oasystem.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author JGZ
 * CreateTime 2019/12/19 15:33
 * Email 1945282561@qq.com
 */
@Mapper
public interface PermissionMapper {

    /**
     * 获取权限列表
     * @return
     */
    List<Permission> permissionList();
}
