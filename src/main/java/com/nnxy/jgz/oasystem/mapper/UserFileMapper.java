package com.nnxy.jgz.oasystem.mapper;

import com.nnxy.jgz.oasystem.entity.UserFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author JGZ
 * CreateTime 2020/1/10 11:35
 * Email 1945282561@qq.com
 */
@Mapper
public interface UserFileMapper {

    /**
     * 插入用户文件
     * @param userFile
     */
    void insert(UserFile userFile);

    /**
     * 通过用户id,获取用户文件列表
     * @param userId
     * @return
     */
    List<UserFile> getUserFileListByUserId(String userId);

    /**
     * 通过文件id查找文件
     * @param fileId
     * @return
     */
    UserFile getUserFileByFileId(String fileId);

    /**
     * 通过id删除用户文件
     * @param fileId
     */
    void deleteUserFileByFileId(String fileId);
}
