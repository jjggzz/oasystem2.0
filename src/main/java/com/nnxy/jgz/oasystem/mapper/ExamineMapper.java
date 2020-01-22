package com.nnxy.jgz.oasystem.mapper;

import com.nnxy.jgz.oasystem.entity.Examine;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author JGZ
 * CreateTime 2020/1/21 8:48
 * Email 1945282561@qq.com
 */
@Mapper
public interface ExamineMapper {
    /**
     * 通过任务id获取审批列表
     * @param applyId
     * @return
     */
    List<Examine> getExamineListByApplyId(String applyId);

    /**
     * 插入审批记录
     * @param examine
     */
    void insert(Examine examine);

    /**
     * 获取用户的已审批列表
     * @param userId
     * @return
     */
    List<Examine> getExamineListByUserId(String userId);
}
