package com.nnxy.jgz.oasystem.controller;

import com.nnxy.jgz.oasystem.entity.Article;
import com.nnxy.jgz.oasystem.entity.User;
import com.nnxy.jgz.oasystem.service.ArticleService;
import com.nnxy.jgz.oasystem.utils.ErrorEnum;
import com.nnxy.jgz.oasystem.utils.ResponseMessage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文章控制层
 * @author JGZ
 * CreateTime 2019/12/26 11:34
 * Email 1945282561@qq.com
 */
@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 获取文章列表
     * @return
     */
    @GetMapping("/article/articleList")
    public ResponseMessage articleList(){
        try {
           List<Article> articleList = articleService.articleList();
           ResponseMessage responseMessage = new ResponseMessage("0","获取文章列表成功");
           responseMessage.getData().put("articleList",articleList);
           return responseMessage;
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","获取失败");
        }
    }

    /**
     * 获取文章列表
     * @return
     */
    @GetMapping("/article/userArticle/{userId}")
    public ResponseMessage articleList(@PathVariable("userId")  String userId){
        try {
            List<Article> articleList = articleService.articleListByUserId(userId);
            ResponseMessage responseMessage = new ResponseMessage("0","获取文章列表成功");
            responseMessage.getData().put("articleList",articleList);
            return responseMessage;
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","获取失败");
        }
    }

    /**
     * 获取文章列表
     * @return
     */
    @GetMapping("/article/articleList/{articleType}")
    public ResponseMessage articleList(@PathVariable("articleType") Integer articleType){
        try {
            List<Article> articleList = articleService.articleListByArticleType(articleType);
            ResponseMessage responseMessage = new ResponseMessage("0","获取文章列表成功");
            responseMessage.getData().put("articleList",articleList);
            return responseMessage;
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","获取失败");
        }
    }


    /**
     * 修改文章
     * @param articleId
     * @param article
     * @return
     */
    @PutMapping("/article/{articleId}")
    public ResponseMessage alterArticle(@PathVariable("articleId") String articleId,
                                        @RequestBody Article article){
        try {
            article.setArticleId(articleId);
            articleService.update(article);
            return new ResponseMessage("0","修改成功");
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","修改失败");
        }
    }

    /**
     * 删除文章
     * @param articleId
     * @return
     */
    @DeleteMapping("/article/{articleId}")
    public ResponseMessage deleteArticle(@PathVariable("articleId") String articleId){
        try {
            articleService.delete(articleId);
            return new ResponseMessage("0","删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","删除失败");
        }
    }

    /**
     * 获取文章详情
     * @param articleId
     * @return
     */
    @GetMapping("/article/{articleId}")
    public ResponseMessage getArticle(@PathVariable("articleId") String articleId){
        try {
            Article article = articleService.select(articleId);
            ResponseMessage responseMessage = new ResponseMessage("0","获取成功");
            responseMessage.getData().put("article",article);
            return responseMessage;
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","获取文章详情失败");
        }
    }

    /**
     * 创建文章
     * @param article
     * @param file
     * @return
     */
    @PostMapping("/article")
    public ResponseMessage addArticle(Article article,MultipartFile file){
        try {
            Subject subject = SecurityUtils.getSubject();
            if(subject.isAuthenticated()){
                //如果用户登录
                User principal = (User) subject.getPrincipal();
                //设置发送用户
                article.setUser(principal);
                //写入数据
                articleService.insert(article,file);
                return new ResponseMessage("0","创建成功");
            }
            else{
                return new ResponseMessage(ErrorEnum.E_UNAUTHENTICATED);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseMessage("-1","创建失败");
        }
    }


}
