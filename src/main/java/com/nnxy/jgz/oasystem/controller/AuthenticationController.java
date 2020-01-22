package com.nnxy.jgz.oasystem.controller;

import com.nnxy.jgz.oasystem.entity.User;
import com.nnxy.jgz.oasystem.service.UserService;
import com.nnxy.jgz.oasystem.utils.ErrorEnum;
import com.nnxy.jgz.oasystem.utils.ProjectConfig;
import com.nnxy.jgz.oasystem.utils.ResponseMessage;
import com.nnxy.jgz.oasystem.utils.SuccessEnum;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户登录的控制层
 * @author JGZ
 * CreateTime 2019/12/17 10:39
 * Email 1945282561@qq.com
 */
@RestController
@RequestMapping("/authentication")
@EnableConfigurationProperties(ProjectConfig.class)
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectConfig projectConfig;

    /**
     * 用户登录
     * @param user
     * @return
     */
    @PostMapping("/userLogin")
    public ResponseMessage userLogin(@RequestBody User user, HttpServletRequest request){
        Subject subject = SecurityUtils.getSubject();
        if(!subject.isAuthenticated()){
            //如果用户未登录
            try{
                UsernamePasswordToken usernamePasswordToken =
                        new UsernamePasswordToken(user.getUserAccount(),user.getUserPassword());
                //认证提交
                subject.login(usernamePasswordToken);
                //返回登录成功信息
                ResponseMessage responseMessage = new ResponseMessage(SuccessEnum.S_LOGIN_SUCCESS);
                //获取用户的信息
                User principal = (User) subject.getPrincipal();
                responseMessage.getData().put("position",principal.getPosition().getPositionName());
                responseMessage.getData().put("userName",principal.getUserName());
                responseMessage.getData().put("userPhone",principal.getUserPhone());
                responseMessage.getData().put("userEmail",principal.getUserEmail());
                responseMessage.getData().put("userId",principal.getUserId());
                responseMessage.getData().put("userAccount",principal.getUserAccount());
                if(principal.getDepartment() != null){
                    responseMessage.getData().put("userDepartmentId",principal.getDepartment().getDepartmentId());
                    responseMessage.getData().put("userDepartmentName",principal.getDepartment().getDepartmentName());
                }
                responseMessage.getData().put("portrait",
                        projectConfig.getServerAddress()+request.getContextPath()+projectConfig.getHeadPortraits()+principal.getUserHeadPortrait());
                return responseMessage;
            }
            catch (UnknownAccountException e){
                //如果用户不存在
                e.printStackTrace();
                return new ResponseMessage(ErrorEnum.E_UNKNOWN_ACCOUNT);
            }
            catch (LockedAccountException e){
                //如果用户被锁定
                e.printStackTrace();
                return new ResponseMessage(ErrorEnum.E_LOCKED_ACCOUNT);
            }
            catch (AuthenticationException e){
                //如果密码错误
                e.printStackTrace();
                return new ResponseMessage(ErrorEnum.E_PASSWORD_ERROR);
            }
        }
        else{
            //如果用户已经登录则无需执行登录轮逻辑，直接返回登录成功
            return new ResponseMessage(SuccessEnum.S_LOGINED);
        }

    }

    /**
     * 用户登出
     * @return
     */
    @GetMapping("/userLogout")
    public ResponseMessage logout(){
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            return  new ResponseMessage("0","安全退出成功");
        }catch (Exception e){
            e.printStackTrace();
            return  new ResponseMessage("-1","安全退出失败");
        }
    }

    /**
     * 异步校验密码
     * @param user
     * @return
     */
    @PostMapping("/checkPassword")
    public ResponseMessage checkPassword(@RequestBody User user){
        Subject subject = SecurityUtils.getSubject();
        //获取登录用户
        if(subject.isAuthenticated()){
            //如果用户是登录状态
            User principal = (User) subject.getPrincipal();
            ByteSource credentialsSalt = ByteSource.Util.bytes(principal.getUserAccount());
            SimpleHash simpleHash = new SimpleHash("MD5", user.getUserPassword(), credentialsSalt, 1024);
            if (simpleHash.toString().equals(principal.getUserPassword())){
                return new ResponseMessage("0","密码正确");
            }
            else{
                return new ResponseMessage("-1","密码错误");
            }
        }
        else {
            return new ResponseMessage("-2","用户未登录");
        }
    }


    /**
     * 修改密码
     * @param user
     * @return
     */
    @PutMapping("/updatePassword")
    public ResponseMessage updatePassword(@RequestBody User user){
        Subject subject = SecurityUtils.getSubject();
        //获取登录用户
        if(subject.isAuthenticated()){
            User principal = (User) subject.getPrincipal();
            try{
                //设置用户id
                user.setUserId(principal.getUserId());
                //设置用户账号
                user.setUserAccount(principal.getUserAccount());
                //修改用户密码
                userService.updatePasswordByAccount(user);
                //用户登出
                subject.logout();
                return new ResponseMessage("0","修改成功");
            }catch (Exception e){
                e.printStackTrace();
                return new ResponseMessage("-1","修改失败");
            }
        }else{
            return new ResponseMessage("-2","用户未登录");
        }
    }
}
