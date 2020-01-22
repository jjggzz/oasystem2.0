package com.nnxy.jgz.oasystem.controller;

import com.nnxy.jgz.oasystem.dto.PositionDto;
import com.nnxy.jgz.oasystem.entity.Permission;
import com.nnxy.jgz.oasystem.entity.Position;
import com.nnxy.jgz.oasystem.service.PositionService;
import com.nnxy.jgz.oasystem.utils.ResponseMessage;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 职位控制层
 * @author JGZ
 * CreateTime 2019/12/19 16:38
 * Email 1945282561@qq.com
 */
@RestController
public class PositionController {

    @Autowired
    private PositionService positionService;

    /**
     * 获取职位列表
     * @return
     */
    @RequiresPermissions(value={"admin:all","position:list"},logical= Logical.OR)
    @GetMapping("/position/positionList")
    public ResponseMessage positionList(){
        try {
           List<Position> positionList = positionService.positionList();
           ResponseMessage responseMessage = new ResponseMessage("0","获取职位列表成功");
           responseMessage.getData().put("positionList",positionList);
           return responseMessage;
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","获取职位列表失败");
        }
    }

    /**
     * 添加职位
     * @param positionDto
     * @return
     */
    @RequiresPermissions(value={"admin:all","position:add"},logical= Logical.OR)
    @PostMapping("/position")
    public ResponseMessage addPosition(@RequestBody PositionDto positionDto){
        //数据封装
        Position position = new Position();
        //设置id
        position.setPositionId(UUID.randomUUID().toString().replace("-",""));
        //设置职位名
        position.setPositionName(positionDto.getPositionName());
        //设置权限
        List<Permission> permissionList = new ArrayList<>();
        for (String permissionId:positionDto.getPermissionId()) {
            Permission permission = new Permission();
            permission.setPermissionId(permissionId);
            permissionList.add(permission);
        }
        position.setPermissionList(permissionList);
        try {
            positionService.addPosition(position);
            return new ResponseMessage("0","创建职位成功");
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("0","创建职位失败");
        }
    }

    @RequiresPermissions(value={"admin:all","position:del"},logical= Logical.OR)
    @DeleteMapping("/position/{positionId}")
    public ResponseMessage delPosition(@PathVariable("positionId") String positionId){
        try {
            positionService.delPositionById(positionId);
            return new ResponseMessage("0","删除成功");
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","删除失败");
        }
    }

    @RequiresPermissions(value={"admin:all","position:alter"},logical= Logical.OR)
    @PutMapping("/position/{positionId}")
    public ResponseMessage alterPosition(@PathVariable("positionId") String positionId,
                                         @RequestBody PositionDto positionDto){
        //数据封装
        Position position = new Position();
        //设置id
        position.setPositionId(positionId);
        //设置职位名
        position.setPositionName(positionDto.getPositionName());
        //设置权限
        List<Permission> permissionList = new ArrayList<>();
        for (String permissionId:positionDto.getPermissionId()) {
            Permission permission = new Permission();
            permission.setPermissionId(permissionId);
            permissionList.add(permission);
        }
        position.setPermissionList(permissionList);
        try {
            positionService.alterPosition(position);
            return new ResponseMessage("0","修改成功");
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","修改失败");
        }


    }


}
