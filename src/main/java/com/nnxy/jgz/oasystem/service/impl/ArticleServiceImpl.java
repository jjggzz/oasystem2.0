package com.nnxy.jgz.oasystem.service.impl;

import com.nnxy.jgz.oasystem.entity.Article;
import com.nnxy.jgz.oasystem.entity.Comment;
import com.nnxy.jgz.oasystem.entity.User;
import com.nnxy.jgz.oasystem.mapper.ArticleMapper;
import com.nnxy.jgz.oasystem.mapper.CommentMapper;
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

    @Autowired
    private CommentMapper commentMapper;

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
        //查询
        Article article = articleMapper.selectByArticleId(articleId);
        if(article!=null){
            //删除记录
            articleMapper.deleteById(articleId);
            if(article.getArticleFile()!=null){
                File file = new File(projectConfig.getArticleFileAddress()+article.getArticleFile());
                //删除文件
                if (file.exists()){
                    file.delete();
                }
            }
        }
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

    @Override
    public List<Article> articleListByUserId(String userId) {
        return articleMapper.articleListByUserId(userId);
    }

    @Override
    public Article select(String articleId) {
        //获取文章
        Article article = articleMapper.selectByArticleId(articleId);
        if(article!=null){
            //如果文章存在
           List<Comment> commentList = commentMapper.selectCommentByArticleId(article.getArticleId());
           article.setCommentList(commentList);
        }
        //设置文章文件路径
        article.setArticleFile(projectConfig.getServerAddress()+projectConfig.getProjectName()+projectConfig.getArticleFile()+article.getArticleFile());
        //设置发送者的头像
        article.getUser().setUserHeadPortrait(projectConfig.getServerAddress()+projectConfig.getProjectName()+
                projectConfig.getHeadPortraits()+article.getUser().getUserHeadPortrait());
        //设置评论人员的头像
        for (Comment comment:article.getCommentList()) {
            //设置一级评论者的头像
            comment.getUser().setUserHeadPortrait(projectConfig.getServerAddress()+projectConfig.getProjectName()+
                    projectConfig.getHeadPortraits()+comment.getUser().getUserHeadPortrait());
            //设置二级评论者的头像
            for (Comment c:comment.getCommentChildList()) {
                c.getUser().setUserHeadPortrait(projectConfig.getServerAddress()+projectConfig.getProjectName()+
                        projectConfig.getHeadPortraits()+c.getUser().getUserHeadPortrait());
            }
        }
        return article;
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
