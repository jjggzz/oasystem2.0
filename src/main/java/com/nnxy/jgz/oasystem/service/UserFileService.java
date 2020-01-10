package com.nnxy.jgz.oasystem.service;

import com.nnxy.jgz.oasystem.entity.User;
import com.nnxy.jgz.oasystem.entity.UserFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author JGZ
 * CreateTime 2020/1/10 11:33
 * Email 1945282561@qq.com
 */
public interface UserFileService {

    /**
     * 上传用户文件
     * @param user
     * @param file
     * @throws IOException
     */
    void uploadFile(User user, MultipartFile file) throws IOException;

    /**
     * 获取用户的文件列表
     * @param userId
     * @return
     */
    List<UserFile> getUserFileListByUserId(String userId);

    /**
     * 通过id删除用户文件
     * @param fileId
     */
    void deleteUserFileByFileId(String fileId);

    /**
     * 通过文件id获取文件信息
     * @param fileId
     * @return
     */
    UserFile getUserFileByFileId(String fileId);
}
