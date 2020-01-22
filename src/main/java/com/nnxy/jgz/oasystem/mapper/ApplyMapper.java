package com.nnxy.jgz.oasystem.mapper;

import com.nnxy.jgz.oasystem.entity.Apply;
import com.nnxy.jgz.oasystem.entity.FlowNode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * @author JGZ
 * CreateTime 2020/1/16 12:07
 * Email 1945282561@qq.com
 */
@Mapper
public interface ApplyMapper {

    /**
     * 插入任务记录
     * @param apply
     */
    void insert(Apply apply);

    /**
     * 通过用户id获取任务记录
     * @param userId
     * @return
     */
    List<Apply> getApplyByUserId(String userId);

    /**
     * 通过id查询任务信息
     * @param applyId
     * @return
     */
    Apply select(String applyId);

    /**
     * 通过id删除任务
     * @param applyId
     */
    void delete(String applyId);

    /**
     * 修改任务
     * @param apply
     */
    void update(Apply apply);

    /**
     * 通过流程id查询任务列表
     * @param list
     * @return
     */
    List<Apply> getApplyByFlowId(List<FlowNode> list);
}
