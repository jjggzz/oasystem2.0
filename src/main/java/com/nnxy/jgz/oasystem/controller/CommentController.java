package com.nnxy.jgz.oasystem.controller;

import com.nnxy.jgz.oasystem.entity.Comment;
import com.nnxy.jgz.oasystem.service.CommentService;
import com.nnxy.jgz.oasystem.utils.ResponseMessage;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @RequiresPermissions(value={"admin:all","comment:add"},logical= Logical.OR)
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

    /**
     * 删除评论
     * @param commentId
     * @return
     */
    @RequiresPermissions(value={"admin:all","comment:del"},logical= Logical.OR)
    @DeleteMapping("/comment/{commentId}")
    public ResponseMessage delComment(@PathVariable("commentId") String commentId){
        try {
            commentService.deleteCommentByCommentId(commentId);
            return new ResponseMessage("0","删除成功");
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","删除失败");
        }
    }
}
