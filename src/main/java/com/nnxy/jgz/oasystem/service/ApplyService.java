package com.nnxy.jgz.oasystem.service;

import com.nnxy.jgz.oasystem.entity.Apply;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author JGZ
 * CreateTime 2020/1/16 11:58
 * Email 1945282561@qq.com
 */
public interface ApplyService {

    /**
     * 添加任务
     * @param apply
     * @param file
     * @throws IOException
     */
    void addApply(Apply apply, MultipartFile file) throws IOException;

    /**
     * 获取某个用户的任务列表
     * @param userId
     * @return
     */
    List<Apply> getApplyByUserId(String userId);

    /**
     * 通过id删除任务
     * @param applyId
     */
    void deleteByApplyId(String applyId);
}
