package com.nnxy.jgz.oasystem.realm;

import com.nnxy.jgz.oasystem.entity.User;
import com.nnxy.jgz.oasystem.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户认证
 * @author JGZ
 * @Classname UserRealm
 * @Date 2019/11/4 18:43
 * @Email 1945282561@qq.com
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {


        return null;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //转换为UsernamePasswordToken
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        //从数据库中获取
        //这里的userName实际是userAccount
        User user = userService.getUserByAccount(usernamePasswordToken.getUsername());
        if (user == null){
            //如果对象为空则用户不存在
            throw new UnknownAccountException("用户不存在");
        }
        if (!user.getUserStatus()){
            //如果为UserStatus为0代表用户已锁定
            throw new LockedAccountException("用户已被锁定");
        }

        //盐值 以用户名作为盐值
        //配置类中标明用MD5加密了1024次
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getUserAccount());
        //返回认证消息
        return new SimpleAuthenticationInfo(user,user.getUserPassword(),credentialsSalt,this.getName());
    }

//    @Test
//    public void func(){
//        String fun = "MD5";
//        String pwd = "123456";
//        ByteSource credentialsSalt = ByteSource.Util.bytes("admin");
//        int i = 1024;
//        SimpleHash simpleHash = new SimpleHash(fun, pwd, credentialsSalt, i);
//        System.out.println(simpleHash);
//    }


}
