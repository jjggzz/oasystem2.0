package com.nnxy.jgz.oasystem.controller;

import ch.qos.logback.core.util.FileUtil;
import com.nnxy.jgz.oasystem.entity.User;
import com.nnxy.jgz.oasystem.service.UserService;
import com.nnxy.jgz.oasystem.utils.FileUtils;
import com.nnxy.jgz.oasystem.utils.ProjectConfig;
import com.nnxy.jgz.oasystem.utils.ResponseMessage;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * 用户控制层
 * @author JGZ
 * CreateTime 2019/12/13 16:26
 * Email 1945282561@qq.com
 */
@EnableConfigurationProperties(ProjectConfig.class)
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectConfig projectConfig;

    @GetMapping("/user/userList")
    public ResponseMessage userList(){
        try {
            List<User> userList = userService.userList();
            for (User user : userList) {
                user.setUserPassword("");
            }
            ResponseMessage responseMessage = new ResponseMessage("0", "获取用户列表成功");
            responseMessage.getData().put("userList", userList);
            return responseMessage;
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1", "获取用户列表失败");
        }
    }

    /**
     * 添加用户
     * @return
     */
    @PostMapping("/user/add")
    public ResponseMessage addUser(@RequestBody User user){
        try {
            //直接调用方法创建
            User dbUser = userService.addUser(user);
            //返回新创建的用户
            ResponseMessage responseMessage = new ResponseMessage("0", "创建成功");
            responseMessage.getData().put("newUser", dbUser);
            return responseMessage;
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","创建失败");
        }
    }

    /**
     * 修改用户信息
     * @param userId
     * @param user
     * @return
     */
    @PutMapping("/user/{userId}")
    public ResponseMessage updateUser(@PathVariable("userId") String userId,@RequestBody User user){
        try{
            //设置userId
            user.setUserId(userId);
            //修改信息
            userService.update(user);
            return new ResponseMessage("0","修改成功");
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","修改失败");
        }
    }

    /**
     * 获取用户的公共信息
     * @param userId
     * @param request
     * @return
     */
    @GetMapping("/user/userPublicInfo/{userId}")
    public ResponseMessage getUserPublicInfo(@PathVariable("userId") String userId, HttpServletRequest request){
        try {
            User user = userService.getUserByUserId(userId);
            ResponseMessage responseMessage = new ResponseMessage("0", "获取成功");
            responseMessage.getData().put("position", user.getPosition().getPositionName());
            responseMessage.getData().put("userName", user.getUserName());
            responseMessage.getData().put("userPhone", user.getUserPhone());
            responseMessage.getData().put("userEmail", user.getUserEmail());
            responseMessage.getData().put("userId", user.getUserId());
            responseMessage.getData().put("userAccount", user.getUserAccount());
            if (user.getDepartment() != null) {
                responseMessage.getData().put("userDepartmentId", user.getDepartment().getDepartmentId());
                responseMessage.getData().put("userDepartmentName", user.getDepartment().getDepartmentName());
            }
            responseMessage.getData().put("portrait",
                    projectConfig.getServerAddress() + request.getContextPath() + projectConfig.getHeadPortraits() + user.getUserHeadPortrait());
            return responseMessage;
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1", "获取失败");
        }
    }

    /**
     * 上传头像
     * @param userId
     * @param file
     * @return
     */
    @PostMapping("/user/uploadPortrait/{userId}")
    public ResponseMessage uploadPortrait(@PathVariable("userId") String userId,MultipartFile file) {
        try {
            User user = new User();
            user.setUserId(userId);
            //修改头像
            userService.update(user,file);
            return new ResponseMessage("0", "上传成功");
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseMessage("-1", "上传失败");
        }
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @DeleteMapping("/user/{userId}")
    public ResponseMessage deleteUser(@PathVariable("userId") String userId){
        try {
            userService.deleteById(userId);
            return new ResponseMessage("0","删除用户成功");
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","删除用户失败");
        }

    }



}
