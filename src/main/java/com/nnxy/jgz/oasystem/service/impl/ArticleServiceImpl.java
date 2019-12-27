package com.nnxy.jgz.oasystem.service.impl;

import com.nnxy.jgz.oasystem.entity.Article;
import com.nnxy.jgz.oasystem.mapper.ArticleMapper;
import com.nnxy.jgz.oasystem.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文章业务层
 * @author JGZ
 * CreateTime 2019/12/26 14:29
 * Email 1945282561@qq.com
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<Article> articleList() {
        return articleMapper.articleList();
    }

    @Override
    public void update(Article article) {
        articleMapper.update(article);
    }

    @Override
    public void delete(String articleId) {
        articleMapper.deleteById(articleId);
    }
}
