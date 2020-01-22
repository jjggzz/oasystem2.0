package com.nnxy.jgz.oasystem.controller;

import com.nnxy.jgz.oasystem.dto.ApplyDto;
import com.nnxy.jgz.oasystem.entity.Apply;
import com.nnxy.jgz.oasystem.entity.ApplyFile;
import com.nnxy.jgz.oasystem.entity.Flow;
import com.nnxy.jgz.oasystem.entity.User;
import com.nnxy.jgz.oasystem.service.ApplyService;
import com.nnxy.jgz.oasystem.utils.ErrorEnum;
import com.nnxy.jgz.oasystem.utils.ProjectConfig;
import com.nnxy.jgz.oasystem.utils.ResponseMessage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 审批控制层
 * @author JGZ
 * CreateTime 2020/1/16 11:46
 * Email 1945282561@qq.com
 */
@EnableConfigurationProperties(ProjectConfig.class)
@RestController
public class ApplyController {

    @Autowired
    private ApplyService applyService;

    @Autowired
    private ProjectConfig projectConfig;
    /**
     * 添加任务
     * @param applyDto
     * @param file
     * @return
     */
    @RequiresPermissions(value={"admin:all","apply:add"},logical= Logical.OR)
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
    @RequiresPermissions(value={"admin:all","apply:del"},logical= Logical.OR)
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
     * 修改任务
     * @param applyId
     * @param apply
     * @return
     */
    @RequiresPermissions(value={"admin:all","apply:alter"},logical= Logical.OR)
    @PutMapping("/apply/{applyId}")
    public ResponseMessage alterApply(@PathVariable("applyId") String applyId,
                                      @RequestBody Apply apply){
        try {
            //数据封装
            apply.setApplyId(applyId);
            apply.setApplySubmitTime(System.currentTimeMillis());
            //修改
            applyService.alterApply(apply);
            return new ResponseMessage("0","修改成功");
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","修改失败");
        }
    }

    /**
     * 获取任务信息
     * @param applyId
     * @return
     */
    @RequiresPermissions(value={"admin:all","apply:get"},logical= Logical.OR)
    @GetMapping("/apply/{applyId}")
    public ResponseMessage getApply(@PathVariable("applyId") String applyId){
        try {
            Apply apply = applyService.getApply(applyId);
            if(apply!=null && apply.getFileList().size()>0){
                for (ApplyFile applyFile:apply.getFileList()) {
                    applyFile.setFileAddress(projectConfig.getServerAddress()+
                            projectConfig.getProjectName()+projectConfig.getApplyFile()+applyFile.getFileRealityName());
                }
            }
            ResponseMessage responseMessage = new ResponseMessage("0","获取成功");
            responseMessage.getData().put("apply",apply);
            return responseMessage;
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","获取失败");
        }
    }

    /**
     * 获取某个用户的任务列表
     * @param userId
     * @return
     */
    @RequiresPermissions(value={"admin:all","apply:list"},logical= Logical.OR)
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

    /**
     * 获取用户能审批的任务
     * @param userId
     * @return
     */
    @RequiresPermissions(value={"admin:all","apply:list"},logical= Logical.OR)
    @GetMapping("/examineApplyList/{userId}")
    public ResponseMessage getUserExamineApplyList(@PathVariable("userId") String userId){
        try {
            List<Apply> examineApplyList = applyService.getUserExamineApplyList(userId);
            ResponseMessage responseMessage = new ResponseMessage("0","获取成功");
            responseMessage.getData().put("examineApplyList",examineApplyList);
            return responseMessage;
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","获取失败");
        }
    }


}
