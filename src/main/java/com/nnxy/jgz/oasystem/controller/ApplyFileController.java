package com.nnxy.jgz.oasystem.controller;

import com.nnxy.jgz.oasystem.entity.ApplyFile;
import com.nnxy.jgz.oasystem.service.ApplyFileService;
import com.nnxy.jgz.oasystem.utils.FileUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 任务文件控制层
 * @author JGZ
 * CreateTime 2020/1/18 10:36
 * Email 1945282561@qq.com
 */
@RestController
public class ApplyFileController {

    @Autowired
    private ApplyFileService applyFileService;

    /**
     * 下载任务文件
     * @param fileId
     * @param response
     */
    @RequiresPermissions(value={"admin:all","file:get"},logical= Logical.OR)
    @GetMapping("/applyFile/download/{fileId}")
    public void downloadFile(@PathVariable("fileId") String fileId, HttpServletResponse response){
        try{
           ApplyFile applyFile = applyFileService.getApplyFileById(fileId);
            String fileAddress = applyFile.getFileAddress();
            //下载文件
            FileUtils.downloadFile(fileAddress,response);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
