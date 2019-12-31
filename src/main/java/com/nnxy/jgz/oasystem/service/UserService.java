package com.nnxy.jgz.oasystem.service;

import com.nnxy.jgz.oasystem.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author JGZ
 * CreateTime 2019/12/13 17:02
 * Email 1945282561@qq.com
 */
public interface UserService {
    /**
     * 通过用户的账号获取用户的信息
     * @param userAccount
     * @return
     */
    User getUserByAccount(String userAccount);

    /**
     * 修改用户信息
     * @param user
     */
    void update(User user);

    /**
     * 创建用户
     * @param user
     * @return
     */
    User addUser(User user);

    /**
     * 通过账号修改密码
     * @param user
     */
    void updatePasswordByAccount(User user);

    /**
     * 通过用户id获取用户
     * @param userId
     * @return
     */
    User getUserByUserId(String userId);

    /**
     * 获取用户列表
     * @return
     */
    List<User> userList();

    /**
     * 通过id删除用户
     * @param userId
     */
    void deleteById(String userId);

    /**
     * 修改用户头像
     * @param user
     * @param file
     */
    void update(User user, MultipartFile file) throws IOException;
}
