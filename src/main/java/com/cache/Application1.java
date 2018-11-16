package com.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.util.List;

/**
 * Created by lizhen on 2018/11/12.
 */
@SpringBootApplication()
@ConfigurationProperties(prefix = "rbac-security")
/**
 * SpringBoot提供了对于配置属性的默认规定
 * 1、所有前缀必须是kebab格式（小写、连字符分隔）acme.myProject或acme.my_project是无效的，必须是acme.my-project
 * 2、属性名称可以使用kebab格式(my-name)或者camel格式(myName)或者snake格式(my_name)
 * 3、环境属性（来自OS的环境变量）必须使用常规的大写下划线格式，下划线只能使用于分隔关键词的各个部分。比如ACME_MYPROJECT_MYNAME
 */
public class Application1 {
    private String baseUrl = "http://localhost"; //client请求server的base地址

    private String endpoint = "/api/rbac-verify"; //client请求server的endpoint

    private String appName; //本服务名称默认使用spirng.application.name

    private String paramUrl = "url"; //访问参数名称

    private String paramMethod = "method";//访问参数名称

    private String paramAppName = "appName";//访问参数名称

    private List<String> ignoreUrl; //检验忽略的url集合
    private Environment environment;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getUaaVerifierUri() {
        return baseUrl+endpoint;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getParamUrl() {
        return paramUrl;
    }

    public void setParamUrl(String paramUrl) {
        this.paramUrl = paramUrl;
    }

    public String getParamMethod() {
        return paramMethod;
    }

    public void setParamMethod(String paramMethod) {
        this.paramMethod = paramMethod;
    }

    public String getParamAppName() {
        return paramAppName;
    }

    public void setParamAppName(String paramAppName) {
        this.paramAppName = paramAppName;
    }

    public List<String> getIgnoreUrl() {
        return ignoreUrl;
    }

    public void setIgnoreUrl(List<String> ignoreUrl) {
        this.ignoreUrl = ignoreUrl;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application1.class, args);
        Application1 application = context.getBean(Application1.class);
        System.out.println(application.getBaseUrl());
    }
}
