package com.nnxy.jgz.oasystem.service;

import com.nnxy.jgz.oasystem.entity.Article;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    /**
     * 根据文章类型获取文章列表
     * @param articleType
     * @return
     */
    List<Article> articleListByArticleType(Integer articleType);

    /**
     * 插入文章
     * @param article
     * @param file
     */
    void insert(Article article, MultipartFile file) throws IOException;
}
