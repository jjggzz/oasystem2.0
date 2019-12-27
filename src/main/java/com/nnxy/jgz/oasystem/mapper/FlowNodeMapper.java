package com.nnxy.jgz.oasystem.mapper;

import com.nnxy.jgz.oasystem.entity.FlowNode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author JGZ
 * CreateTime 2019/12/20 16:35
 * Email 1945282561@qq.com
 */
@Mapper
public interface FlowNodeMapper {
    /**
     * 通过流程id获取所有节点
     * @param flowId
     * @return
     */
    List<FlowNode> selectFlowNodeByFlowId(String flowId);

    /**
     * 插入流程节点
     * @param flowNodeList
     */
    void insert(List<FlowNode> flowNodeList);
}
