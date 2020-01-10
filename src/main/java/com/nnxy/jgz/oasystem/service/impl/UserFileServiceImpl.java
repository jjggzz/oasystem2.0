package com.nnxy.jgz.oasystem.service.impl;

import com.nnxy.jgz.oasystem.entity.User;
import com.nnxy.jgz.oasystem.entity.UserFile;
import com.nnxy.jgz.oasystem.mapper.UserFileMapper;
import com.nnxy.jgz.oasystem.service.UserFileService;
import com.nnxy.jgz.oasystem.utils.FileUtils;
import com.nnxy.jgz.oasystem.utils.ProjectConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author JGZ
 * CreateTime 2020/1/10 11:34
 * Email 1945282561@qq.com
 */
@EnableConfigurationProperties(ProjectConfig.class)
@Service
public class UserFileServiceImpl implements UserFileService {

    @Autowired
    UserFileMapper userFileMapper;

    @Autowired
    ProjectConfig projectConfig;

    @Override
    public void uploadFile(User user, MultipartFile file) throws IOException {
        //获取文件后缀名
        String fileLastWord = FileUtils.getFileLastWord(file.getOriginalFilename());
        //随机字符串
        String fileName = UUID.randomUUID().toString().replace("-", "");
        //获取毫秒值
        Long time = System.currentTimeMillis();
        //设置文件的存储路径
        File f = new File(projectConfig.getUserFileAddress() + fileName + time + "." + fileLastWord);
        //插入数据到数据库
        UserFile userFile = new UserFile();
        //设置文件id
        userFile.setFileId(UUID.randomUUID().toString().replace("-",""));
        //设置文件的真名
        userFile.setFileRealityName(fileName + time + "." + fileLastWord);
        //设置文件的存储路径
        userFile.setFileAddress(projectConfig.getUserFileAddress() + fileName + time + "." + fileLastWord);
        //设置文件大小
        userFile.setFileSize(file.getSize());
        //设置文件的名字
        userFile.setFileName(file.getOriginalFilename());
        //设置时间
        userFile.setUploadTime(System.currentTimeMillis());
        //设置所属用户
        userFile.setUser(user);
        userFileMapper.insert(userFile);
        //写入文件
        file.transferTo(f);
    }

    @Override
    public List<UserFile> getUserFileListByUserId(String userId) {
        return userFileMapper.getUserFileListByUserId(userId);
    }

    @Override
    public void deleteUserFileByFileId(String fileId) {
       UserFile userFile = userFileMapper.getUserFileByFileId(fileId);
       if (userFile!=null){
           //如果文件存在
           userFileMapper.deleteUserFileByFileId(fileId);
           File file = new File(userFile.getFileAddress());
           if (file.exists()){
               file.delete();
           }
       }
    }

    @Override
    public UserFile getUserFileByFileId(String fileId) {
        return userFileMapper.getUserFileByFileId(fileId);
    }
}
