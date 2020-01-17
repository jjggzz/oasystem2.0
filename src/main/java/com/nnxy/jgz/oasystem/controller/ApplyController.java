package com.nnxy.jgz.oasystem.controller;

import com.nnxy.jgz.oasystem.dto.ApplyDto;
import com.nnxy.jgz.oasystem.entity.Apply;
import com.nnxy.jgz.oasystem.entity.Flow;
import com.nnxy.jgz.oasystem.entity.User;
import com.nnxy.jgz.oasystem.service.ApplyService;
import com.nnxy.jgz.oasystem.utils.ErrorEnum;
import com.nnxy.jgz.oasystem.utils.ResponseMessage;
import org.apache.ibatis.annotations.Delete;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 审批控制层
 * @author JGZ
 * CreateTime 2020/1/16 11:46
 * Email 1945282561@qq.com
 */
@RestController
public class ApplyController {

    @Autowired
    private ApplyService applyService;
    /**
     * 添加任务
     * @param applyDto
     * @param file
     * @return
     */
    @PostMapping("/apply")
    public ResponseMessage addApply(ApplyDto applyDto, MultipartFile file){
        try {
            if(!SecurityUtils.getSubject().isAuthenticated()){
                return new ResponseMessage(ErrorEnum.E_UNAUTHENTICATED);
            }
            //封装数据
            Apply apply = new Apply();
            apply.setApplyTitle(applyDto.getApplyTitle());
            apply.setApplyDescription(applyDto.getApplyDescription());
            Flow flow = new Flow();
            flow.setFlowId(applyDto.getFlowId());
            apply.setFlow(flow);
            apply.setUser((User) SecurityUtils.getSubject().getPrincipal());
            //数据写入
            applyService.addApply(apply,file);
            return new ResponseMessage("0","添加成功");
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","添加失败");
        }
    }

    /**
     * 删除任务
     * @param applyId
     * @return
     */
    @DeleteMapping("/apply/{applyId}")
    public ResponseMessage delApply(@PathVariable("applyId") String applyId){
        try {
            applyService.deleteByApplyId(applyId);
            return new ResponseMessage("0","删除成功");
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","删除失败");
        }
    }

    /**
     * 获取某个用户的任务列表
     * @param userId
     * @return
     */
    @GetMapping("/applyList/{userId}")
    public ResponseMessage getUserApplyList(@PathVariable("userId") String userId){
        try {
            List<Apply> applyList = applyService.getApplyByUserId(userId);
            ResponseMessage responseMessage = new ResponseMessage("0","获取成功");
            responseMessage.getData().put("applyList",applyList);
            return responseMessage;
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","获取失败");
        }
    }


}
