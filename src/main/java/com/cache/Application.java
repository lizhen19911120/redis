package com.cache;

import com.cache.common.SqlPropertySourceFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTemplate;

/**
 * Created by lizhen on 2018/10/11.
 */
@SpringBootApplication(scanBasePackages = "com.cache")
@PropertySources({@PropertySource(value = "classpath:database.sql", factory = SqlPropertySourceFactory.class),@PropertySource(value = "classpath:jdbc.properties",ignoreResourceNotFound = true)})
public class Application {

//    @Autowired
//    private JdbcTemplate jdbcTemplate;

    /**
     * 获取的@PropertySource中放入的文件内容键值对
     */
    @Value("${create_table_ssm_article}")
    private String createArticle;

    @Value("${insert_table_ssm_article}")
    private String insertArticle;

    @Value("${jdbc.driverClassName}")
    private String driverClassName;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Bean
    public DriverManagerDataSource driverManagerDataSource(){
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(driverClassName);
        driverManagerDataSource.setUrl(url);
        driverManagerDataSource.setUsername(username);
        driverManagerDataSource.setPassword(password);
        return driverManagerDataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DriverManagerDataSource dataSource){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }

//    @Bean
    private SessionFactory sessionFactory(){
        return null;
    }

//    @Bean
    public HibernateTemplate hibernateTemplate(SessionFactory sessionFactory){
        HibernateTemplate hibernateTemplate = new HibernateTemplate();
        hibernateTemplate.setSessionFactory(sessionFactory);
        return hibernateTemplate;
    }

//    @Bean
    public String initSqlBean(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute(createArticle);
        jdbcTemplate.batchUpdate(insertArticle);
        return "success";
    }


    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);
//        System.out.println(context.getBean(PropertySourcesPlaceholderConfigurer.class));
//        Application application = context.getBean(Application.class);
//        System.out.println(application.createArticle);
//        System.out.println(application.driverClassName);
    }
    
}
