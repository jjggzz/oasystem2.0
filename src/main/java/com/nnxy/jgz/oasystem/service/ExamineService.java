package com.nnxy.jgz.oasystem.service;

import com.nnxy.jgz.oasystem.entity.Examine;

import java.util.List;

/**
 * @author JGZ
 * CreateTime 2020/1/21 8:43
 * Email 1945282561@qq.com
 */
public interface ExamineService {

    /**
     * 通过任务id获取审批列表
     * @param applyId
     * @return
     */
    List<Examine> getExamineListByApplyId(String applyId);

    /**
     * 审批任务逻辑实现
     * @param examine
     */
    void examine(Examine examine);

    /**
     * 获取用户的已审批列表
     * @param userId
     * @return
     */
    List<Examine> getExamineListByUserId(String userId);
}
