package com.nnxy.jgz.oasystem.service.impl;

import com.nnxy.jgz.oasystem.entity.Article;
import com.nnxy.jgz.oasystem.entity.Comment;
import com.nnxy.jgz.oasystem.mapper.ArticleMapper;
import com.nnxy.jgz.oasystem.mapper.CommentMapper;
import com.nnxy.jgz.oasystem.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author JGZ
 * CreateTime 2020/1/12 14:04
 * Email 1945282561@qq.com
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public void insert(Comment comment) {
        //设置id
        comment.setCommentId(UUID.randomUUID().toString().replace("-",""));
        //设置评论时间
        comment.setCommentCreateTime(System.currentTimeMillis());
        //插入记录
        commentMapper.insert(comment);
        //修改文章信息
        Article article = articleMapper.selectByArticleId(comment.getArticle().getArticleId());
        article.setArticleCommentCount(article.getArticleCommentCount()+1);
        articleMapper.update(article);
    }

    @Override
    public void deleteCommentByCommentId(String commentId) {
        //查询该评论的文章id
        String articleId = commentMapper.selectArticleIdByCommentId(commentId);
        //查询该评论的子评论条数
        List<Comment> commentList = commentMapper.selectCommentByParent(commentId);
        //删除
        commentMapper.delete(commentId);
        if(commentList.size()>0){
            commentMapper.deleteByParent(commentId);
        }
        //修改文章信息
        Article article = articleMapper.selectByArticleId(articleId);
        //评论数减去1 再减去该评论的所有子评论数
        article.setArticleCommentCount((article.getArticleCommentCount()-1-commentList.size()));
        articleMapper.update(article);
    }
}
