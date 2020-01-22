package com.nnxy.jgz.oasystem.controller;

import com.nnxy.jgz.oasystem.entity.Examine;
import com.nnxy.jgz.oasystem.entity.User;
import com.nnxy.jgz.oasystem.service.ExamineService;
import com.nnxy.jgz.oasystem.utils.ErrorEnum;
import com.nnxy.jgz.oasystem.utils.ResponseMessage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;


/**
 * @author JGZ
 * CreateTime 2020/1/22 9:31
 * Email 1945282561@qq.com
 */
@RestController
public class ExamineController {


    @Autowired
    private ExamineService examineService;
    /**
     * 添加审批信息
     * @return
     */
    @PostMapping("/examine")
    public ResponseMessage addExamine(@RequestBody Examine examine){
        System.out.println(examine);
        try {
            Subject subject = SecurityUtils.getSubject();
            if(subject.isAuthenticated()){
                User user = (User) subject.getPrincipal();
                examine.setUser(user);
                examineService.examine(examine);
                return new ResponseMessage("0","审批成功");
            }
            else{
                //如果用户未登录
                return new ResponseMessage(ErrorEnum.E_UNAUTHENTICATED);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","审批失败");
        }
    }

    /**
     * 获取审批i列表
     * @param userId
     * @return
     */
    @GetMapping("/examineList/{userId}")
    public ResponseMessage examineList(@PathVariable("userId") String userId){
        try {
            List<Examine> examineList = examineService.getExamineListByUserId(userId);
            ResponseMessage responseMessage = new ResponseMessage("0","获取成功");
            responseMessage.getData().put("examineList",examineList);
            return responseMessage;
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","获取失败");
        }
    }
}
