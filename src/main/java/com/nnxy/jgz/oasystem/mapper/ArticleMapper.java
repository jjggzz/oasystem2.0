package com.nnxy.jgz.oasystem.mapper;

import com.nnxy.jgz.oasystem.entity.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author JGZ
 * CreateTime 2019/12/26 14:32
 * Email 1945282561@qq.com
 */
@Mapper
public interface ArticleMapper {
    /**
     * 获取文章列表
     * @return
     */
    public List<Article> articleList();

    /**
     * 修改文章
     * @param article
     */
    void update(Article article);

    /**
     * 通过id删除文章
     * @param articleId
     */
    void deleteById(String articleId);

    /**
     * 通过文章类型获取文章列表
     * @param articleType
     * @return
     */
    List<Article> articleListByArticleType(Integer articleType);

    /**
     * 插入信息
     * @param article
     */
    void insert(Article article);
}
