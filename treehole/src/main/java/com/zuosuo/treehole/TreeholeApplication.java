package com.zuosuo.treehole;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {"com.zuosuo.component", "com.zuosuo.wechat", "com.zuosuo.mybatis", "com.zuosuo.cache", "com.zuosuo.biudb", "com.zuosuo.auth", "com.zuosuo.treehole"})
@MapperScan(basePackages = {"com.zuosuo.biudb.mapper"})
@EntityScan(basePackages = {"com.zuosuo.biudb.entity"})
public class TreeholeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TreeholeApplication.class, args);
    }

}
