package com.cache.service.impl;

import com.cache.dao.ArticleDao;
import com.cache.domain.Article;
import com.cache.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.JDBCType;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

    private static final Logger log = LoggerFactory.getLogger(ArticleService.class);

    @Resource
    private ArticleDao articleDao;


    @Override
    public List<Article> findArticle(Map<String, Object> map) {
        return null;
    }

//    @Override
//    public Long getTotalArticle(Map<String, Object> map) {
//        return articleDao.getTotalArticles(map);
//    }

    @Override
    public int addArticle(Article article) {
        if (article.getArticleTitle() == null || article.getArticleContent() == null || article.getArticleContent().length() > 50000) {
            return 0;
        }
        if (articleDao.insertArticle(article) > 0) {
            log.info("insert article success,save article to redis");
            return 1;
        }
        return 0;
    }

    @Override
    public int updateArticle(Article article) {
//        if (article.getArticleTitle() == null || article.getArticleContent() == null || getTotalArticle(null) > 90 || article.getArticleContent().length() > 50000) {
//            return 0;
//        }
//        if (articleDao.updArticle(article) > 0) {
//            log.info("update article success,delete article in redis and save again");
//            redisUtil.del(Constants.ARTICLE_CACHE_KEY + article.getId());
//            redisUtil.put(Constants.ARTICLE_CACHE_KEY + article.getId(), article);
//            return 1;
//        }
        return 0;
    }

    public Boolean testDelete(String id){
        return id.contains("4");
    }

    /**
     * 使用redis缓存管理器，mykeyGenerator作为键生成器，同时只有当参数id包含4时删除缓存
     * @param id
     * @return
     */
    @Override
    @CacheEvict(value = "myRedisConfig",keyGenerator = "mykeyGenerator",condition = "#id.contains('4')")
    public int deleteArticle(String id) {
        log.info("delete article from mysql");
        return articleDao.delArticle(id);
    }

    /**
     * 使用redis缓存管理器，mykeyGenerator作为键生成器，同时只有当方法返回的Article不为空才存入缓存
     * @param id
     * @return
     */
    @Override
    @Caching(cacheable = @Cacheable(value = "myRedisConfig",keyGenerator = "mykeyGenerator", cacheResolver = "cacheResolver", unless = "#result==null"))
    public Article findById(String id) {
        Article articleFromMysql = articleDao.getArticleById(id);
        log.info("get article from mysql");
        return articleFromMysql;
    }

    /**
     * 使用ConcurrentMap缓存管理器，mykeyGenerator作为键生成器，同时只有当方法返回的Article不为空才存入缓存
     * @param id
     * @return
     */
    @Override
    @Caching(cacheable = @Cacheable(value = "myConMapManager",keyGenerator = "mykeyGenerator", cacheResolver = "cacheResolver1", unless = "#result==null"))
    public Article findById2(String id) {
        Article articleFromMysql = articleDao.getArticleById(id);
        log.info("get article from mysql2");
        return articleFromMysql;
    }

}
