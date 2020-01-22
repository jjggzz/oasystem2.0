package com.nnxy.jgz.oasystem.service.impl;

import com.nnxy.jgz.oasystem.entity.Apply;
import com.nnxy.jgz.oasystem.entity.Examine;
import com.nnxy.jgz.oasystem.entity.FlowLine;
import com.nnxy.jgz.oasystem.mapper.ExamineMapper;
import com.nnxy.jgz.oasystem.mapper.FlowNodeLineMapper;
import com.nnxy.jgz.oasystem.service.ApplyService;
import com.nnxy.jgz.oasystem.service.ExamineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * 审批业务实现类
 * @author JGZ
 * CreateTime 2020/1/21 8:43
 * Email 1945282561@qq.com
 */
@Service
public class ExamineServiceImpl implements ExamineService {

    @Autowired
    private ApplyService applyService;

    @Autowired
    private ExamineMapper examineMapper;

    @Autowired
    private FlowNodeLineMapper flowNodeLineMapper;

    @Override
    public List<Examine> getExamineListByApplyId(String applyId) {
        return examineMapper.getExamineListByApplyId(applyId);
    }


    @Transactional(isolation = Isolation.REPEATABLE_READ,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public void examine(Examine examine) {
        //获取任务信息
        Apply apply = applyService.getApply(examine.getApply().getApplyId());
        if(examine.getExamineStatus()){
            //如果同意则
            FlowLine flowLine = null;
            if(apply.getApplyCurrentNode() == null){
                //如果任务的当前节点为空（第一个）
                flowLine = flowNodeLineMapper.selectPrevNodeIsNull(apply.getFlow().getFlowId());
            }
            else{
                //如果不为空按照正常方式查询
                flowLine = flowNodeLineMapper.selectFlowLineByPrevNode(apply.getApplyCurrentNode().getFlowNodeId());
            }
            //设置当前节点
            apply.setApplyCurrentNode(flowLine.getNextNode());
            examine.setFlowNode(flowLine.getNextNode());
            FlowLine flag = flowNodeLineMapper.selectFlowLineByPrevNode(apply.getApplyCurrentNode().getFlowNodeId());
            if ( flag.getNextNode() == null){
                //如果后一节点的后一节点为空则直接改变任务的状态(结束)
                apply.setApplyState(2);
            }
        }
        else{
            //拒绝则
            apply.setApplyState(2);
        }
        //修改
        applyService.update(apply);
        //插入审批记录
        examine.setExamineId(UUID.randomUUID().toString().replace("-",""));
        examine.setExamineDate(System.currentTimeMillis());
        examineMapper.insert(examine);


    }

    @Override
    public List<Examine> getExamineListByUserId(String userId) {
        List<Examine> examineList = examineMapper.getExamineListByUserId(userId);
        for (Examine examine:examineList) {
            Apply apply = applyService.getApply(examine.getApply().getApplyId());
            examine.setApply(apply);
        }
        return examineList;
    }
}
