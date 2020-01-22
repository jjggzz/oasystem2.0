package com.nnxy.jgz.oasystem.service.impl;

import com.nnxy.jgz.oasystem.entity.*;
import com.nnxy.jgz.oasystem.mapper.ApplyFileMapper;
import com.nnxy.jgz.oasystem.mapper.ApplyMapper;
import com.nnxy.jgz.oasystem.mapper.FlowNodeLineMapper;
import com.nnxy.jgz.oasystem.mapper.FlowNodeMapper;
import com.nnxy.jgz.oasystem.service.ApplyFileService;
import com.nnxy.jgz.oasystem.service.ApplyService;
import com.nnxy.jgz.oasystem.service.ExamineService;
import com.nnxy.jgz.oasystem.service.UserService;
import com.nnxy.jgz.oasystem.utils.FileUtils;
import com.nnxy.jgz.oasystem.utils.ProjectConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author JGZ
 * CreateTime 2020/1/16 12:04
 * Email 1945282561@qq.com
 */
@EnableConfigurationProperties(ProjectConfig.class)
@Service
public class ApplyServiceImpl implements ApplyService {

    @Autowired
    private ApplyMapper applyMapper;

    @Autowired
    private ApplyFileMapper applyFileMapper;

    @Autowired
    private ApplyFileService applyFileService;

    @Autowired
    private ExamineService examineService;

    @Autowired
    private UserService userService;

    @Autowired
    private FlowNodeMapper flowNodeMapper;

    @Autowired
    private FlowNodeLineMapper flowNodeLineMapper;

    @Autowired
    private ProjectConfig projectConfig;

    @Transactional(isolation = Isolation.REPEATABLE_READ,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public void addApply(Apply apply, MultipartFile file) throws IOException {
        //设置任务id
        apply.setApplyId(UUID.randomUUID().toString().replace("-", ""));
        apply.setApplyState(0);
        applyMapper.insert(apply);
        //获取后缀名
        String fileLastWord = FileUtils.getFileLastWord(file.getOriginalFilename());
        //文件名
        String fileName = UUID.randomUUID().toString().replace("-","");
        //当前时间
        Long time = System.currentTimeMillis();
        //设置文件的存储路径
        File f = new File(projectConfig.getApplyFileAddress() + fileName + time + "." + fileLastWord);
        ApplyFile applyFile = new ApplyFile();
        //设置文件id
        applyFile.setFileId(UUID.randomUUID().toString().replace("-",""));
        //设置文件存储地址
        applyFile.setFileAddress(projectConfig.getApplyFileAddress() + fileName + time + "." + fileLastWord);
        //设置文件真名
        applyFile.setFileRealityName(fileName + time + "." + fileLastWord);
        //设置文件名
        applyFile.setFileName(file.getOriginalFilename());
        //设置文件大小
        applyFile.setFileSize(file.getSize());
        //设置所属任务
        applyFile.setApply(apply);
        //插入记录
        applyFileMapper.insert(applyFile);
        //写入文件
        file.transferTo(f);
    }


    @Override
    public List<Apply> getApplyByUserId(String userId) {
        return applyMapper.getApplyByUserId(userId);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public void deleteByApplyId(String applyId) {
        //获取任务信息
       Apply apply = applyMapper.select(applyId);
       if (apply != null && apply.getFileList().size()>0){
           //删除文件
            applyFileService.deleteByList(apply.getFileList());
       }
       //删除记录
       applyMapper.delete(applyId);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public void alterApply(Apply apply) {
        applyMapper.update(apply);
    }

    @Override
    public Apply getApply(String applyId) {
        //查任务
        Apply apply = applyMapper.select(applyId);
        //获取该任务的审批列表
        List<Examine> examineList = examineService.getExamineListByApplyId(applyId);
        apply.setExamineList(examineList);
        return apply;
    }


    @Override
    public List<Apply> getUserExamineApplyList(String userId) {
        //获取用户信息
        User user = userService.getUserByUserId(userId);
        //获取用户能够审核的节点列表
        List<FlowNode> flowNodeList = flowNodeMapper.
                selectFlowNodeByPositionId(user.getPosition().getPositionId());
        //通过流程id查询任务列表
        List<Apply> applyList = applyMapper.getApplyByFlowId(flowNodeList);
        //获取流程线列表
        List<FlowLine> flowLineList = flowNodeLineMapper.
                selectFlowLineByNextNode(flowNodeList);
        List<Apply> newApplyList = new ArrayList<>();
        for (FlowLine flowLine:flowLineList) {
            for (Apply apply:applyList) {
                //如果该任务属于同一条审批流，并且当前节点等于遍历出的流程线的前一节点（有可能未空）
                if(apply.getFlow().getFlowId().equals(flowLine.getFlow().getFlowId())){
                    if(apply.getApplyCurrentNode()==null && flowLine.getPrevNode() == null){
                        newApplyList.add(apply);
                    }
                    else if(apply.getApplyCurrentNode() == null && flowLine.getPrevNode() != null){
                        continue;
                    }
                    else if(apply.getApplyCurrentNode()!=null && flowLine.getPrevNode() == null){
                        continue;
                    }
                    else if(apply.getApplyCurrentNode().getFlowNodeId().equals(flowLine.getPrevNode().getFlowNodeId())){
                        newApplyList.add(apply);
                    }
                    else{
                        continue;
                    }


                }
            }
        }
        return newApplyList;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public void update(Apply apply) {
        applyMapper.update(apply);
    }
}
