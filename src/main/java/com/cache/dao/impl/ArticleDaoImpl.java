package com.cache.dao.impl;

import com.cache.dao.ArticleDao;
import com.cache.domain.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by lizhen on 2018/10/12.
 */
@Component
public class ArticleDaoImpl implements ArticleDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Article> findArticles(Map<String, Object> map) {
        return null;
    }

    @Override
    public Long getTotalArticles(Map<String, Object> map) {
        return null;
    }

    @Override
    public int insertArticle(Article article) {

        String articleTitle = "\'"+article.getArticleTitle()+"\'";
        String articleCreateDate = "\'"+article.getArticleCreateDate()+"\'";
        String articleContent = "\'"+article.getArticleContent()+"\'";
        String isTop = "\'"+article.getIsTop()+"\'";
        String addName = "\'"+article.getAddName()+"\'";

        return jdbcTemplate.update("INSERT INTO ssm_article(article_title,article_create_date,article_content,is_top,add_name)" +
                " VALUES(" + articleTitle + "," + articleCreateDate + "," + articleContent + "," + isTop + "," + addName +
                ")");
    }

    @Override
    public int updArticle(Article article) {
        return 0;
    }

    @Override
    public int delArticle(String id) {
        return jdbcTemplate.update("DELETE FROM ssm_article WHERE id=?",ps -> ps.setString(1, id));
    }

    @Override
    public Article getArticleById(String id) {
        String sql = "select * from ssm_article where id=?";

        Article article = jdbcTemplate.query(sql, ps -> ps.setString(1, id), new ResultSetExtractor<Article>() {
            @Override
            public Article extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    Article u = new Article();
                    u.setId(rs.getLong("id"));
                    u.setArticleTitle(rs.getString("article_title"));
                    u.setArticleCreateDate(rs.getTimestamp("article_create_date").toLocalDateTime());
                    u.setArticleContent(rs.getString("article_content"));
                    u.setAddName(rs.getString("add_name"));
                    u.setIsTop(rs.getInt("is_top"));
                    return u;
                }
                return null;
            }
        });
        return article;
    }

}
