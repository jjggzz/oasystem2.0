package com.nnxy.jgz.oasystem.service.impl;

import com.nnxy.jgz.oasystem.entity.Apply;
import com.nnxy.jgz.oasystem.entity.ApplyFile;
import com.nnxy.jgz.oasystem.mapper.ApplyFileMapper;
import com.nnxy.jgz.oasystem.mapper.ApplyMapper;
import com.nnxy.jgz.oasystem.service.ApplyFileService;
import com.nnxy.jgz.oasystem.service.ApplyService;
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
 * CreateTime 2020/1/16 12:04
 * Email 1945282561@qq.com
 */
@EnableConfigurationProperties(ProjectConfig.class)
@Service
public class ApplyServiceImpl implements ApplyService {

    @Autowired
    private ApplyMapper applyMapper;

    @Autowired
    private ApplyFileMapper applyFileMapper;

    @Autowired
    private ApplyFileService applyFileService;

    @Autowired
    private ProjectConfig projectConfig;

    @Override
    public void addApply(Apply apply, MultipartFile file) throws IOException {
        //设置任务id
        apply.setApplyId(UUID.randomUUID().toString().replace("-", ""));
        apply.setApplyState(0);
        applyMapper.insert(apply);
        //获取后缀名
        String fileLastWord = FileUtils.getFileLastWord(file.getOriginalFilename());
        //文件名
        String fileName = UUID.randomUUID().toString().replace("-","");
        //当前时间
        Long time = System.currentTimeMillis();
        //设置文件的存储路径
        File f = new File(projectConfig.getApplyFileAddress() + fileName + time + "." + fileLastWord);
        ApplyFile applyFile = new ApplyFile();
        //设置文件id
        applyFile.setFileId(UUID.randomUUID().toString().replace("-",""));
        //设置文件存储地址
        applyFile.setFileAddress(projectConfig.getApplyFileAddress() + fileName + time + "." + fileLastWord);
        //设置文件真名
        applyFile.setFileRealityName(fileName + time + "." + fileLastWord);
        //设置文件名
        applyFile.setFileName(file.getOriginalFilename());
        //设置文件大小
        applyFile.setFileSize(file.getSize());
        //设置所属任务
        applyFile.setApply(apply);
        //插入记录
        applyFileMapper.insert(applyFile);
        //写入文件
        file.transferTo(f);
    }

    @Override
    public List<Apply> getApplyByUserId(String userId) {
        return applyMapper.getApplyByUserId(userId);
    }

    @Override
    public void deleteByApplyId(String applyId) {
        //获取任务信息
       Apply apply = applyMapper.select(applyId);
       if (apply != null && apply.getFileList().size()>0){
           //删除文件
            applyFileService.deleteByList(apply.getFileList());
       }
       //删除记录
       applyMapper.delete(applyId);
    }
}
