package com.nnxy.jgz.oasystem.mapper;

import com.nnxy.jgz.oasystem.entity.FlowLine;
import com.nnxy.jgz.oasystem.entity.FlowNode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author JGZ
 * CreateTime 2019/12/25 13:44
 * Email 1945282561@qq.com
 */
@Mapper
public interface FlowNodeLineMapper {
    /**
     * 插入流程线
     * @param flowLineList
     */
    void insert(List<FlowLine> flowLineList);

    /**
     * 通过流程获取流程线表
     * @param flowNodeList
     * @return
     */
    List<FlowLine> selectFlowLineByNextNode(List<FlowNode> flowNodeList);

    /**
     * 查询前节点为空的线段
     * @param flowId
     * @return
     */
    FlowLine selectPrevNodeIsNull(String flowId);

    /**
     * 通过前一节点查询线段
     * @param flowNodeId
     * @return
     */
    FlowLine selectFlowLineByPrevNode(String flowNodeId);
}
