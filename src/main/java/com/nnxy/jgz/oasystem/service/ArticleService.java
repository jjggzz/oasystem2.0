package com.nnxy.jgz.oasystem.service;

import com.nnxy.jgz.oasystem.entity.Article;

import java.util.List;

/**
 * @author JGZ
 * CreateTime 2019/12/26 14:28
 * Email 1945282561@qq.com
 */
public interface ArticleService {
    /**
     * 获取文章列表
     * @return
     */
    List<Article> articleList();

    /**
     * 修改文章
     * @param article
     */
    void update(Article article);

    /**
     * 删除文章
     * @param articleId
     */
    void delete(String articleId);
}
