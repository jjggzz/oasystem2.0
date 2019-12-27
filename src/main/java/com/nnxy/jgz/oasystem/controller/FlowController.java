package com.nnxy.jgz.oasystem.controller;

import com.nnxy.jgz.oasystem.entity.Flow;
import com.nnxy.jgz.oasystem.service.FlowService;
import com.nnxy.jgz.oasystem.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程控制层
 * @author JGZ
 * CreateTime 2019/12/20 14:32
 * Email 1945282561@qq.com
 */
@RestController
public class FlowController {

    @Autowired
    private FlowService flowService;

    /**
     * 获取流程列表
     * @return
     */
    @GetMapping("/flow/flowList")
    public ResponseMessage flowList(){
        try {
            List<Flow> flowList = flowService.flowList();
            ResponseMessage responseMessage = new ResponseMessage("0", "流程列表获取成功");
            responseMessage.getData().put("flowList", flowList);
            return responseMessage;
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","流程列表获取失败");
        }
    }

    /**
     * 修改流程
     * @param flowId
     * @param flow
     * @return
     */
    @PutMapping("/flow/{flowId}")
     public ResponseMessage updateFlow(@PathVariable("flowId") String flowId,@RequestBody Flow flow){
        try{
            flow.setFlowId(flowId);
            flowService.update(flow);
            return new ResponseMessage("0","修改成功");
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","修改失败");
        }
     }

     @DeleteMapping("/flow/{flowId}")
     public ResponseMessage deleteFlow(@PathVariable("flowId") String flowId){
        try{
            flowService.deleteByFlowId(flowId);
            return new ResponseMessage("0","删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","删除失败");
        }
     }

     @PostMapping("/flow")
     public ResponseMessage addFlow(@RequestBody Flow flow){
         try{
             flowService.addFlow(flow);
             return new ResponseMessage("0","添加成功");
         }catch (Exception e){
             e.printStackTrace();
             return new ResponseMessage("-1","添加失败");
         }
     }

}
