package com.nnxy.jgz.oasystem.service.impl;

import com.nnxy.jgz.oasystem.entity.User;
import com.nnxy.jgz.oasystem.mapper.UserMapper;
import com.nnxy.jgz.oasystem.service.UserService;
import com.nnxy.jgz.oasystem.utils.EncryptionUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author JGZ
 * CreateTime 2019/12/13 17:26
 * Email 1945282561@qq.com
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByAccount(String userAccount) {
        //直接调用mapper查询数据
        return userMapper.getUserByAccount(userAccount);
    }

    @Override
    public void updatePasswordByAccount(User user) {
        //用账号做盐值
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getUserAccount());
        SimpleHash simpleHash = new SimpleHash("MD5", user.getUserPassword(), credentialsSalt, 1024);
        //设置加密过后的密码
        user.setUserPassword(simpleHash.toString());
        //执行修改
        userMapper.updateUserId(user);
    }

    @Override
    public User getUserByUserId(String userId) {

        return userMapper.getUserByUserId(userId);
    }

    @Override
    public List<User> userList() {

        return userMapper.userList();
    }

    @Override
    public void deleteById(String userId) {
        userMapper.deleteById(userId);
    }

    @Override
    public void update(User user) {
        //如果密码不为空
        if(user.getUserPassword()!=null){
            //如果账号也不为空
            if(user.getUserAccount()!=null){
                //加密密码
                user.setUserPassword(EncryptionUtils.
                        encryptionByMD5(user.getUserAccount(),user.getUserPassword(),1024));
            }
            else{
                //否则设置密码为空
                user.setUserPassword(null);
            }
        }
        //执行修改
        userMapper.updateUserId(user);
    }

    @Override
    public User addUser(User user) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        do {
            //清空字符串缓冲区
            stringBuilder.delete(0,stringBuilder.length());
            //产生1个10个随机数拼成的字符串
            for (int j = 0;j < 10 ;j++){
                int i = random.nextInt(10);
                stringBuilder.append(i);
            }
            User userByAccount = userMapper.getUserByAccount(stringBuilder.toString());
            //如果账号不存在跳出循环
            if(userByAccount == null){
                break;
            }
        }while (true);
        //设置id
        user.setUserId(UUID.randomUUID().toString().replace("-",""));
        if(user.getUserAccount() == null){
            //设置账号
            user.setUserAccount(stringBuilder.toString());
        }
        if (user.getUserPassword() == null){
            //加密并设置密码
            String s = EncryptionUtils.
                    encryptionByMD5(stringBuilder.toString(), "123456", 1024);
            user.setUserPassword(s);
        }
        user.setUserCreateTime(System.currentTimeMillis());
        userMapper.insert(user);
         //返回插入的用户信息
        return userMapper.getUserByAccount(stringBuilder.toString());
    }


}
