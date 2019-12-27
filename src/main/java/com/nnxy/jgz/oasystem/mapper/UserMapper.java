package com.nnxy.jgz.oasystem.mapper;

import com.nnxy.jgz.oasystem.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author JGZ
 * CreateTime 2019/12/13 17:27
 * Email 1945282561@qq.com
 */
@Mapper
public interface UserMapper {

    /**
     * 通过userAccount查询账户信息
     * @param userAccount
     * @return
     */
    User getUserByAccount(String userAccount);

    /**
     * 修改用户信息
     * @param user
     */
    void updateUserId(User user);

    /**
     * 插入用户
     * @param user
     */
    void insert(User user);

    /**
     * 通过userId获取用户信息
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
}
