package com.nnxy.jgz.oasystem.controller;

import ch.qos.logback.core.util.FileUtil;
import com.nnxy.jgz.oasystem.entity.User;
import com.nnxy.jgz.oasystem.service.UserService;
import com.nnxy.jgz.oasystem.utils.FileUtils;
import com.nnxy.jgz.oasystem.utils.ResponseMessage;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@RestController
public class UserController {

    @Autowired
    private UserService userService;

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
                    "http://localhost:8081" + request.getContextPath() + "/file/headPortraits/" + user.getUserHeadPortrait());
            return responseMessage;
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1", "获取失败");
        }
    }

    @PostMapping("/user/uploadPortrait/{userId}")
    public ResponseMessage uploadPortrait(@PathVariable("userId") String userId,MultipartFile file) {
        //如果上传的文件不为空
        if(file != null){
            User user = new User();
            user.setUserId(userId);
            //获取文件后缀名
            String fileLastWord = FileUtils.getFileLastWord(file.getOriginalFilename());
            //随机字符串
            String fileName = UUID.randomUUID().toString().replace("-", "");
            //获取毫秒值
            Long time = System.currentTimeMillis();
            //设置文件的存储路径
            File f = new File("D:/file/headPortraits/" + fileName + time + "." + fileLastWord);
            try {
                //写入文件
                file.transferTo(f);
                //设置文件的存储路径位置
                user.setUserHeadFilePath("D:/file/headPortraits/" + fileName + time + "." + fileLastWord);
                //设置头像名
                user.setUserHeadPortrait(fileName + time + "." + fileLastWord);
                //更新数据库
                userService.update(user);
                return new ResponseMessage("0", "上传成功");
            } catch (Exception e) {
                e.printStackTrace();
                //出现异常,若文件存在则删除(针对于写入文件后sql报错)
                if(f.exists()){
                    f.delete();
                }
                return new ResponseMessage("-1", "上传失败");
            }
        }
        else{
            return new ResponseMessage("-2", "请选择文件");
        }

    }

    @DeleteMapping("/user/{userId}")
    public ResponseMessage deleteUser(@PathVariable("userId") String userId){
        try {
            userService.deleteById(userId);
            return new ResponseMessage("0","删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","删除失败");
        }

    }



}
