package com.nnxy.jgz.oasystem.controller;

import com.nnxy.jgz.oasystem.entity.Permission;
import com.nnxy.jgz.oasystem.service.PermissionService;
import com.nnxy.jgz.oasystem.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 权限控制层
 * @author JGZ
 * CreateTime 2019/12/19 15:28
 * Email 1945282561@qq.com
 */
@RestController
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    /**
     * 权限列表
     * @return
     */
    @GetMapping("/permission/permissionList")
    public ResponseMessage permissionList(){
        try {
            List<Permission> permissionList = permissionService.permissionList();
            ResponseMessage responseMessage = new ResponseMessage("0","获取权限列表成功");
            responseMessage.getData().put("permissionList",permissionList);
            return responseMessage;
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","获取权限列表失败");
        }

    }

}
