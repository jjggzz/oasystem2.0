package com.nnxy.jgz.oasystem.service.impl;

import com.nnxy.jgz.oasystem.entity.Flow;
import com.nnxy.jgz.oasystem.entity.FlowLine;
import com.nnxy.jgz.oasystem.entity.FlowNode;
import com.nnxy.jgz.oasystem.mapper.FlowMapper;
import com.nnxy.jgz.oasystem.mapper.FlowNodeLineMapper;
import com.nnxy.jgz.oasystem.mapper.FlowNodeMapper;
import com.nnxy.jgz.oasystem.service.FlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author JGZ
 * CreateTime 2019/12/20 14:36
 * Email 1945282561@qq.com
 */
@Service
public class FlowServiceImpl implements FlowService {

    @Autowired
    private FlowMapper flowMapper;

    @Autowired
    private FlowNodeMapper flowNodeMapper;

    @Autowired
    private FlowNodeLineMapper flowNodeLineMapper;

    @Override
    public List<Flow> flowList() {
        List<Flow> flows = flowMapper.flowList();
        //获取流程的所有节点
        for (Flow flow: flows) {
           List<FlowNode> flowNodeList = flowNodeMapper.selectFlowNodeByFlowId(flow.getFlowId());
            flow.setFlowNodeList(flowNodeList);
        }
        //对流程中的节点排序
        for (Flow flow: flows) {
            List<FlowNode> flowNodeList = shortFlow(flow.getFlowLineList(), flow.getFlowNodeList());
            flow.setFlowNodeList(flowNodeList);
        }

        return flows;
    }

    @Override
    public void update(Flow flow) {
        flowMapper.update(flow);
    }

    @Override
    public void deleteByFlowId(String flowId) {
        flowMapper.deleteByFlowId(flowId);
    }

    @Override
    public void addFlow(Flow flow) {
        //设置流程的id
        String flowId = UUID.randomUUID().toString().replace("-", "");
        flow.setFlowId(flowId);
        flow.setFlowState(0);
        //如果节点列表不为空（节点顺序已排好）
        if(flow.getFlowNodeList() !=null && flow.getFlowNodeList().size() != 0){
            //设置节点的id
            setFlowNodeId(flow);
            //根据节点列表生成流程线列表
            createFlowLineByFlowNode(flow);
        }
        //插入流程
        flowMapper.insert(flow);
        //插入流程节点
        flowNodeMapper.insert(flow.getFlowNodeList());
        //插入流程线
        flowNodeLineMapper.insert(flow.getFlowLineList());
    }

    /**
     * 设置节点的id
     * @param flow
     */
    private void setFlowNodeId(Flow flow) {
        for (FlowNode flowNode:flow.getFlowNodeList()) {
            Flow f = new Flow();
            f.setFlowId(flow.getFlowId());
            flowNode.setFlow(f);
            flowNode.setFlowNodeId(UUID.randomUUID().toString().replace("-",""));
        }
    }

    /**
     * 创建节点列表
     * @param flow
     */
    private void createFlowLineByFlowNode(Flow flow) {
        //将节点列表取出
        List<FlowNode> flowNodeList = flow.getFlowNodeList();
        List<FlowLine> flowLineList = new ArrayList<>();
        for (int i = 0;i < flowNodeList.size();i++){
            FlowLine fl = new FlowLine();
            //设置所属流程
            Flow f = new Flow();
            f.setFlowId(flow.getFlowId());
            fl.setFlow(f);
            fl.setFlowLineId(UUID.randomUUID().toString().replace("-",""));
            if(i == 0){
                //如果是第一个节点
                //前一节点设置为空
                fl.setPrevNode(new FlowNode());
                fl.setNextNode(flowNodeList.get(i));
            }
            if(i != 0){
                fl.setPrevNode(flowNodeList.get(i-1));
                fl.setNextNode(flowNodeList.get(i));
            }
            flowLineList.add(fl);
        }
        //添加结束线
        FlowLine fl = new FlowLine();
        Flow f = new Flow();
        f.setFlowId(flow.getFlowId());
        fl.setFlow(f);
        fl.setFlowLineId(UUID.randomUUID().toString().replace("-",""));
        fl.setPrevNode(flowNodeList.get(flowNodeList.size()-1));
        fl.setNextNode(new FlowNode());
        flowLineList.add(fl);
        //设置到流程对象中
        flow.setFlowLineList(flowLineList);
    }

    /**
     * 对节点排序
     * @param flowLineList
     * @param flowNodeList
     * @return
     */
    private List<FlowNode> shortFlow(List<FlowLine> flowLineList, List<FlowNode> flowNodeList){
        List<FlowNode> flowNodes = new ArrayList<>();
        //如果流程节点为空直接结束
        if(flowNodeList.size() == 0){
            return flowNodes;
        }
        //如果流程线为空直接结束
        if(flowLineList.size() == 0){
            return flowNodes;
        }
        //获取第一个节点
        FlowNode fist = null;
        for (FlowLine f:flowLineList) {
            //前一节点为空则
            if(f.getPrevNode()==null){
                fist = f.getNextNode();
                //添加到数组中
                flowNodes.add(fist);
                break;
            }
        }
        if(fist == null){
            return flowNodes;
        }
        while (true){
            for (FlowLine f:flowLineList) {
                //如果节点线中的前一节点的id等于当前节点
                if(f.getPrevNode() == null){
                    continue;
                }
                if(f.getPrevNode().getFlowNodeId().equals(fist.getFlowNodeId())){
                    if(f.getNextNode()!=null){
                        //将后一节点放入数组中
                        flowNodes.add(f.getNextNode());
                        fist = f.getNextNode();
                    }else{
                        return flowNodes;
                    }
                }
            }
        }


    }

}
