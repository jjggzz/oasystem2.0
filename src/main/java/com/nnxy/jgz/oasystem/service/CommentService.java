package com.nnxy.jgz.oasystem.service;

import com.nnxy.jgz.oasystem.entity.Comment;

/**
 * @author JGZ
 * CreateTime 2020/1/12 14:03
 * Email 1945282561@qq.com
 */
public interface CommentService {

    /**
     * 添加评论
     * @param comment
     */
    void insert(Comment comment);

    /**
     * 通过id删除评论
     * @param commentId
     */
    void deleteCommentByCommentId(String commentId);
}
