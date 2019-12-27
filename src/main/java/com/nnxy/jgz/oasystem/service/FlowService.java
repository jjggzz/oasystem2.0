package com.nnxy.jgz.oasystem.service;

import com.nnxy.jgz.oasystem.entity.Flow;

import java.util.List;

/**
 * @author JGZ
 * CreateTime 2019/12/20 14:33
 * Email 1945282561@qq.com
 */
public interface FlowService {
    /**
     * 获取流程列表
     * @return
     */
    List<Flow> flowList();

    /**
     * 修改流程
     * @param flow
     */
    void update(Flow flow);

    /**
     * 通过id删除流程
     * @param flowId
     */
    void deleteByFlowId(String flowId);

    /**
     * 添加流程
     * @param flow
     */
    void addFlow(Flow flow);
}
