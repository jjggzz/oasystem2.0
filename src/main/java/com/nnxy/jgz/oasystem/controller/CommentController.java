package com.nnxy.jgz.oasystem.controller;

import com.nnxy.jgz.oasystem.entity.Comment;
import com.nnxy.jgz.oasystem.service.CommentService;
import com.nnxy.jgz.oasystem.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 评论控制层
 * @author JGZ
 * CreateTime 2020/1/12 13:58
 * Email 1945282561@qq.com
 */
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 添加评论
     * @param comment
     * @return
     */
    @PostMapping("/comment")
    public ResponseMessage addComment(@RequestBody Comment comment){
        try {
            commentService.insert(comment);
            return new ResponseMessage("0","回复成功");
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","回复失败");
        }
    }

}
