package com.nnxy.jgz.oasystem.service;

import com.nnxy.jgz.oasystem.entity.ApplyFile;

import java.util.List;

/**
 * @author JGZ
 * CreateTime 2020/1/17 9:47
 * Email 1945282561@qq.com
 */
public interface ApplyFileService {
    /**
     * 删除任务文件
     * @param fileList
     */
    void deleteByList(List<ApplyFile> fileList);

    /**
     * 通过id获取任务文件
     * @param fileId
     * @return
     */
    ApplyFile getApplyFileById(String fileId);
}
