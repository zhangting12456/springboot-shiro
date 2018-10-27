package com.shiro.shirospringboot.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    public MyRealm myRealm(){
        MyRealm realm = new MyRealm();
        return realm;
    }
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(MyRealm realm,CookieRememberMeManager rememberMeManager){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
//        关联realm
        manager.setRealm(realm);
        //    关联rememberMe
        manager.setRememberMeManager(rememberMeManager);
        return manager;
    }

//    创建CookieRememberMeManager
    @Bean
    public CookieRememberMeManager cookieRememberMeManager(SimpleCookie cookie){
        CookieRememberMeManager manager = new CookieRememberMeManager();
        manager.setCookie(cookie);
        return manager;
    }
//    创建cookie
    @Bean
    public SimpleCookie simpleCookie(){
        SimpleCookie cookie = new SimpleCookie("rememberMe");
//        设置cookie的时间长度
        cookie.setMaxAge(120);
//        设置只读模型
        cookie.setHttpOnly(true);
        return cookie;
    }
//创建shiroFilterFactoryBean

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
//        关联SecurityManager
        bean.setSecurityManager(defaultWebSecurityManager);
        Map<String,String> filterMap = new HashMap<>();
//      认证过滤器
       /* filterMap.put("/product/toAdd","anon");*/
        filterMap.put("/user/login","anon");
//        授权过滤器
        filterMap.put("/product/toAdd","perms[product:add]");
        filterMap.put("/product/toUpdate","perms[product:update]");
        filterMap.put("/product/toList","perms[product:list]");
//        添加user过滤器
//        index的请求只要使用rememberMe功能，就可以访问
        filterMap.put("/index","user");
        filterMap.put("/**","authc");

//        添加过滤器
        bean.setFilterChainDefinitionMap(filterMap);
        bean.setLoginUrl("/toLogin");
        bean.setUnauthorizedUrl("/unAuth");
        return bean;
    }
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }


}
