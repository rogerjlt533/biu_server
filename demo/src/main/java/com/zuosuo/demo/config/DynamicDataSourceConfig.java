package com.zuosuo.demo.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@MapperScan(basePackages = {"com.zuosuo.biudb.mapper"}, sqlSessionFactoryRef = "SqlSessionFactory")
public class DynamicDataSourceConfig {

    @Bean(name = "DataSourceBiuMaster")
    // 默认数据源
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.biu.master")
    public DataSource getDateSourceOfficeMaster() {
        return DataSourceBuilder.create().build();
    }

//    @Bean(name = "DataSourceBiuRead1")
//    @ConfigurationProperties(prefix = "spring.datasource.biu.read1")
//    public DataSource getDateSourceOfficeRead1() {
//        return DataSourceBuilder.create().build();
//    }

    @Bean(name = "dynamicDataSource")
    public DynamicDataSource officeDataSource(@Qualifier("DataSourceBiuMaster") DataSource dataSourceBiuMaster) {
        // 这个地方是比较核心的 targetDataSource 集合是我们数据库和名字之间的映射
        Map<Object, Object> targetDataSource = new HashMap<>();
        targetDataSource.put(DataSourceOption.DataBaseItem.DATA_BIU, dataSourceBiuMaster);
//        targetDataSource.put(DataSourceOption.DataBaseItem.DATA_BIU_READ1, dataSourceBiuRead1);
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSource);
        dataSource.setDefaultTargetDataSource(dataSourceBiuMaster); // 设置默认对象
        return dataSource;
    }

    @Bean
    @ConfigurationProperties(prefix = "mybatis.configuration")
    public org.apache.ibatis.session.Configuration globalConfiguration() {
        return new org.apache.ibatis.session.Configuration();
    }

    @Bean(name = "SqlSessionFactory")
    public SqlSessionFactory SqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource, org.apache.ibatis.session.Configuration config) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dynamicDataSource);
        bean.setConfiguration(config);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/**/*.xml"));//设置我们的xml文件路径
        return bean.getObject();
    }
}
