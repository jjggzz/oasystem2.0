package com.nnxy.jgz.oasystem.mapper;

import com.nnxy.jgz.oasystem.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author JGZ
 * CreateTime 2020/1/12 10:09
 * Email 1945282561@qq.com
 */
@Mapper
public interface CommentMapper {

    /**
     * 通过文章id获取评论列表
     * @param articleId
     * @return
     */
    List<Comment> selectCommentByArticleId(String articleId);

    /**
     * 添加评论
     * @param comment
     */
    void insert(Comment comment);
}
