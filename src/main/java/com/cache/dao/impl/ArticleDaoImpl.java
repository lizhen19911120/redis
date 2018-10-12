package com.cache.dao.impl;

import com.cache.dao.ArticleDao;
import com.cache.domain.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        return 0;
    }

    @Override
    public int updArticle(Article article) {
        return 0;
    }

    @Override
    public int delArticle(String id) {
        return 0;
    }

    @Override
    public Article getArticleById(String id) {
//        Article article = jdbcTemplate.queryForObject("SELECT * FROM ssm_article WHERE id = ?",Article.class,id);
        String sql = "select * from ssm_article where id=?";

        list1 = jdbcTemplate.query(sql, new PreparedStatementSetter() {

            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, id);
            }

        }, new ResultSetExtractor<Article>() {

            public Article extractData(ResultSet rs) throws SQLException, DataAccessException {
                List list = new ArrayList();

                while (rs.next()) {
                    Article u = new Article();

                    u.setId(rs.getLong("id"));
                    u.setArticleTitle(rs.getString("article_title"));
                    u.setArticleCreateDate(rs.getTimestamp("article_create_date").toLocalDateTime());
                    u.setArticleContent(rs.getString("article_content"));
                    u.setAddName(rs.getString("add_name"));
                    u.setIsTop(rs.getInt("is_top"));
                    list.add(u);

                }

                return list;
            }
        });
}
