package com.zuosuo.treehole.config;

import com.zuosuo.biudb.factory.BiuDbFactory;
import com.zuosuo.treehole.Handle.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private BiuDbFactory biuDbFactory;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(biuDbFactory)).addPathPatterns("/**");
    }
}
