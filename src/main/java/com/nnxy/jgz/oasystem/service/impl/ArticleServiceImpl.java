package com.nnxy.jgz.oasystem.service.impl;

import com.nnxy.jgz.oasystem.entity.Article;
import com.nnxy.jgz.oasystem.mapper.ArticleMapper;
import com.nnxy.jgz.oasystem.service.ArticleService;
import com.nnxy.jgz.oasystem.utils.FileUtils;
import com.nnxy.jgz.oasystem.utils.ProjectConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 文章业务层
 * @author JGZ
 * CreateTime 2019/12/26 14:29
 * Email 1945282561@qq.com
 */
@EnableConfigurationProperties(ProjectConfig.class)
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ProjectConfig projectConfig;

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

    @Override
    public List<Article> articleListByArticleType(Integer articleType) {
        List<Article> articleList = articleMapper.articleListByArticleType(articleType);
        //生成显示顺序的列表排序
        return createNewList(articleList);
    }

    @Override
    public void insert(Article article, MultipartFile file) throws IOException {
        //设置id
        article.setArticleId(UUID.randomUUID().toString().replace("-",""));
        //设置插入时间
        article.setArticleCreateTime(System.currentTimeMillis());
        //设置评论数
        article.setArticleCommentCount(0);
        //设置浏览数
        article.setArticlePageView(0);
        //获取文件后缀名
        String fileLastWord = FileUtils.getFileLastWord(file.getOriginalFilename());
        //随机字符串
        String fileName = UUID.randomUUID().toString().replace("-", "");
        //获取毫秒值
        Long time = System.currentTimeMillis();
        //设置文件参数
        File f = new File(projectConfig.getArticleFileAddress() + fileName + time + "." + fileLastWord);
        article.setArticleFile(fileName + time + "." + fileLastWord);
        //插入数据库
        articleMapper.insert(article);
        //写入文件
        file.transferTo(f);
    }

    /**
     * 构建显示的列表
     * @param list
     * @return
     */
    private List<Article> createNewList(List<Article> list){
        List<Article> newList = new ArrayList<>();
        int len = list.size();
        for (int j = 0; j < len && j < 3 ; j++) {
            int max = 0;
            for (int i = 0; i < list.size(); i++) {
                if(list.get(i).getArticleCommentCount()>list.get(max).getArticleCommentCount()){
                    max = i;
                }
            }
            newList.add(list.get(max));
            list.remove(max);
        }
        newList.addAll(list);
        return newList;
    }
}
