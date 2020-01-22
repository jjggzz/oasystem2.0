package com.nnxy.jgz.oasystem.service.impl;

import com.nnxy.jgz.oasystem.entity.ApplyFile;
import com.nnxy.jgz.oasystem.mapper.ApplyFileMapper;
import com.nnxy.jgz.oasystem.service.ApplyFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * @author JGZ
 * CreateTime 2020/1/17 10:00
 * Email 1945282561@qq.com
 */
@Service
public class ApplyFileServiceImpl implements ApplyFileService {

    @Autowired
    private ApplyFileMapper applyFileMapper;

    @Override
    public void deleteByList(List<ApplyFile> fileList) {
        //删除数据库中记录
        applyFileMapper.deleteByList(fileList);
        //删除文件
        for (ApplyFile applyfile:fileList) {
            File file = new File(applyfile.getFileAddress());
            if (file.exists()){
                file.delete();
            }
        }
    }

    @Override
    public ApplyFile getApplyFileById(String fileId) {
        return applyFileMapper.selectById(fileId);
    }
}
