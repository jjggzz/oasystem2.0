package com.nnxy.jgz.oasystem.controller;

import com.nnxy.jgz.oasystem.entity.Article;
import com.nnxy.jgz.oasystem.service.ArticleService;
import com.nnxy.jgz.oasystem.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


}
